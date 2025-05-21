package com.martmists.multiplatform.graphql

import com.martmists.multiplatform.graphql.ext.gqlName
import com.martmists.multiplatform.reflect.withNullability
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import kotlin.enums.EnumEntries
import kotlin.reflect.*

class SchemaBuilder {
    private val typeMap = mutableMapOf<KType, Schema.TypeDefinition<*>>()
    private val enumMap = mutableMapOf<KType, Schema.EnumDefinition<*>>()
    private val interfaceMap = mutableMapOf<KType, suspend Any?.() -> KType>()
    private val queries = mutableMapOf<String, Schema.OperationDefinition<*>>()
    private val mutations = mutableMapOf<String, Schema.OperationDefinition<*>>()
    private val subscriptions = mutableMapOf<String, Schema.SubscriptionDefinition<*>>()
    private val requestedTypes = mutableSetOf<KType>()

    init {
        scalar<String>()
        scalar<Long>()
        scalar<Int>()
        scalar<Float>()
        scalar<Double>()
        scalar<Boolean>()
    }

    abstract class BaseBuilder internal constructor() {
        /**
         * A description for the property. This will be included in the schema.
         */
        var description: String? = null
    }

    abstract class BuilderWithArgument internal constructor() : BaseBuilder() {
        protected val arguments = mutableMapOf<String, KType>()
        /**
         * Adds an argument to this property.
         */
        inline fun <reified A> argument(name: String) = argument<A>(name, typeOf<A>())
        fun <A> argument(name: String, type: KType): SchemaRequestContext.() -> A {
            arguments[name] = type
            return { this.variable(name) }
        }
    }

    class BackedPropertyBuilder<T, R> internal constructor(private val name: String, private val type: KType, private val prop: KProperty1<T, R>) : BaseBuilder() {
        private var rule: suspend T.(SchemaRequestContext) -> Boolean = { true }

        /**
         * Adds an access rule to this property. The rule will be called on the object instance, and if it returns false,
         * the property will not be included in the response.
         */
        fun accessRule(rule: @GraphQLDSL suspend T.(ctx: SchemaRequestContext) -> Boolean) {
            this.rule = rule
        }

        internal fun build(): Schema.PropertyDefinition<T, R> {
            return Schema.PropertyDefinition(name, description, type, rule, emptyMap()) { prop.get(this) }
        }
    }

    class PropertyBuilder<T, R> internal constructor(private val name: String, private val type: KType) : BuilderWithArgument() {
        private var rule: suspend T.(SchemaRequestContext) -> Boolean = { true }
        private var resolver: (suspend T.(SchemaRequestContext) -> R)? = null

        /**
         * Adds an access rule to this property. The rule will be called on the object instance, and if it returns false,
         * the property will not be included in the response.
         */
        fun accessRule(rule: @GraphQLDSL suspend T.(ctx: SchemaRequestContext) -> Boolean) {
            this.rule = rule
        }

        /**
         * Sets the resolver for this property. The resolver will be called on the object instance, and its result will be
         * returned in the response.
         */
        fun resolver(getter: @GraphQLDSL suspend T.(ctx: SchemaRequestContext) -> R) {
            this.resolver = getter
        }

        internal fun build(): Schema.PropertyDefinition<T, R> {
            require(resolver != null) { "Property $name has no getter" }
            return Schema.PropertyDefinition(name, description, type, rule, arguments, resolver!!)
        }
    }

    abstract inner class BaseTypeBuilder<T> internal constructor() : BaseBuilder() {
        protected val properties = mutableMapOf<String, Schema.PropertyDefinition<T, *>>()

        /**
         * Registers a property on this type. You must set the `resolver` function for this property.
         */
        inline fun <reified R> property(name: String, noinline builder: @GraphQLDSL PropertyBuilder<T, R>.() -> Unit) = property(name, typeOf<R>(), builder)

        /**
         * Registers a bound property on this type.
         */
        inline fun <reified R> property(prop: KProperty1<T, R>, noinline builder: @GraphQLDSL BackedPropertyBuilder<T, R>.() -> Unit = {}) = property(prop.name, typeOf<R>(), prop, builder)
        fun <R> property(name: String, type: KType, builder: PropertyBuilder<T, R>.() -> Unit) {
            val propBuilder = PropertyBuilder<T, R>(name, type)
            propBuilder.builder()
            properties[name] = propBuilder.build()
        }
        fun <R> property(name: String, type: KType, prop: KProperty1<T, R>, builder: @GraphQLDSL BackedPropertyBuilder<T, R>.() -> Unit) {
            val propBuilder = BackedPropertyBuilder(name, type, prop)
            propBuilder.builder()
            properties[name] = propBuilder.build()
        }
    }

    inner class TypeBuilder<T> internal constructor(private val type: KType): BaseTypeBuilder<T>() {
        private val interfaces = mutableListOf<KType>()
        internal var serializer: KSerializer<T>? = null

        /**
         * Registers this type as inheriting the given interface type.
         */
        inline fun <reified T> usesInterface() = usesInterface(typeOf<T>())
        fun usesInterface(type: KType) {
            interfaces += type
        }

        internal fun build(): Schema.TypeDefinition<T> {
            property("__typename") {
                resolver {
                    type.withNullability(true).gqlName
                }
            }
            requestedTypes.addAll(properties.values.map { it.ret.withNullability(false) })
            return Schema.TypeDefinition(type.withNullability(true).gqlName, description, type, properties, interfaces, serializer)
        }
    }

    inner class InterfaceTypeBuilder<T> internal constructor(private val type: KType): BaseTypeBuilder<T>() {
        private var typeResolver: (suspend T.() -> KType)? = null

        /**
         * Resolves the value to its GraphQL type.
         */
        fun resolver(typeResolver: @GraphQLDSL suspend T.() -> KType) {
            this.typeResolver = typeResolver
        }

        internal fun build(): Pair<Schema.TypeDefinition<T>, (suspend T.() -> KType)> {
            require(typeResolver != null) { "Interface ${type.gqlName} has no resolver" }
            property("__typename") {
                resolver {
                    typeResolver!!.invoke(this).withNullability(true).gqlName
                }
            }
            requestedTypes.addAll(properties.values.map { it.ret.withNullability(false) })
            return Schema.TypeDefinition(type.withNullability(true).gqlName, description, type, properties, emptyList(), null) to typeResolver!!
        }
    }

    class OperationBuilder<T> internal constructor(private val name: String, private val type: KType): BuilderWithArgument() {
        private var rule: suspend (SchemaRequestContext) -> Boolean = { true }
        private var resolver: (suspend (SchemaRequestContext) -> T)? = null

        /**
         * Adds an access rule to this operation. If it returns false,
         * the operation will not be executed.
         */
        fun accessRule(rule: @GraphQLDSL suspend (ctx: SchemaRequestContext) -> Boolean) {
            this.rule = rule
        }

        /**
         * Adds a resolver to this operation.
         */
        fun resolver(resolver: @GraphQLDSL suspend (ctx: SchemaRequestContext) -> T) {
            this.resolver = resolver
        }

        internal fun build(): Schema.OperationDefinition<T> {
            require(resolver != null) { "Operation $name has no resolver" }
            return Schema.OperationDefinition(name, description, type, rule, arguments, resolver!!)
        }
    }

    class SubscriptionBuilder<T> internal constructor(private val name: String, private val type: KType): BuilderWithArgument() {
        private var rule: suspend (SchemaRequestContext) -> Boolean = { true }
        private var resolver: (suspend (SchemaRequestContext) -> Flow<T>)? = null

        /**
         * Adds an access rule to this operation. If it returns false,
         * the operation will not be executed.
         */
        fun accessRule(rule: @GraphQLDSL suspend (ctx: SchemaRequestContext) -> Boolean) {
            this.rule = rule
        }

        /**
         * Adds a resolver to this operation.
         */
        fun resolver(resolver: @GraphQLDSL suspend (ctx: SchemaRequestContext) -> Flow<T>) {
            this.resolver = resolver
        }

        internal fun build(): Schema.SubscriptionDefinition<T> {
            require(resolver != null) { "Operation $name has no resolver" }
            return Schema.SubscriptionDefinition(name, description, type, rule, arguments, resolver!!)
        }
    }

    inline fun <reified T : Any> interfaceType(noinline block: @GraphQLDSL InterfaceTypeBuilder<T>.() -> Unit) = interfaceType<T>(typeOf<T>(), block)
    fun <T : Any> interfaceType(type: KType, block: @GraphQLDSL InterfaceTypeBuilder<T>.() -> Unit) {
        val builder = InterfaceTypeBuilder<T>(type)
        builder.block()
        val (def, resolver) = builder.build()
        typeMap[type] = def
        @Suppress("UNCHECKED_CAST")
        interfaceMap[type] = resolver as suspend Any?.() -> KType
    }

    /**
     * Registers a type to the type system. All fields you wish to expose must be exposed manually.
     */
    inline fun <reified T : Any> type(noinline block: @GraphQLDSL TypeBuilder<T>.() -> Unit) = type(typeOf<T>(), block)
    fun <T : Any> type(type: KType, block: @GraphQLDSL TypeBuilder<T>.() -> Unit) {
        val typeBuilder = TypeBuilder<T>(type)
        typeBuilder.block()
        typeMap[type] = typeBuilder.build()
    }

    /**
     * Registers a custom scalar.
     */
    inline fun <reified T : Any> scalar(serializer: KSerializer<T> = serializer<T>()) = scalar(typeOf<T>(), serializer)
    fun <T : Any> scalar(type: KType, serializer: KSerializer<T>) {
        val typeBuilder = TypeBuilder<T>(type)
        typeBuilder.serializer = serializer
        typeMap[type] = typeBuilder.build()
    }

    /**
     * Registers an enum to the type system. This will only expose the name of the enum value.
     */
    inline fun <reified T : Enum<T>> enum(entries: EnumEntries<T>) = enum(entries, typeOf<T>())
    fun <T : Enum<T>> enum(entries: EnumEntries<T>, type: KType) {
        enumMap[type] = Schema.EnumDefinition(type.gqlName, type, entries)
    }

    /**
     * Defines a query operation to the schema.
     */
    inline fun <reified T> query(name: String, noinline block: @GraphQLDSL OperationBuilder<T>.() -> Unit) = query(name, typeOf<T>(), block)
    fun <T> query(name: String, type: KType, block: @GraphQLDSL OperationBuilder<T>.() -> Unit) {
        requestedTypes.add(type)
        val operationBuilder = OperationBuilder<T>(name, type)
        operationBuilder.block()
        queries[name] = operationBuilder.build()
    }

    /**
     * Defines a mutation operation to the schema.
     */
    inline fun <reified T> mutation(name: String, noinline block: @GraphQLDSL OperationBuilder<T>.() -> Unit) = mutation(name, typeOf<T>(), block)
    fun <T> mutation(name: String, type: KType, block: @GraphQLDSL OperationBuilder<T>.() -> Unit) {
        requestedTypes.add(type)
        val operationBuilder = OperationBuilder<T>(name, type)
        operationBuilder.block()
        mutations[name] = operationBuilder.build()
    }

    /**
     * Defines a subscription operation to the schema.
     */
    inline fun <reified T> subscription(name: String, noinline block: @GraphQLDSL SubscriptionBuilder<T>.() -> Unit) = subscription(name, typeOf<T>(), block)
    fun <T> subscription(name: String, type: KType, block: @GraphQLDSL SubscriptionBuilder<T>.() -> Unit) {
        requestedTypes.add(type)
        val operationBuilder = SubscriptionBuilder<T>(name, type)
        operationBuilder.block()
        subscriptions[name] = operationBuilder.build()
    }

    /**
     * Whether schema introspection is enabled
     */
    var introspection = true

    private object __Schema
    private class __Type(val type: KType)
    private enum class __TypeKind {
        SCALAR,
        OBJECT,
        INTERFACE,
        UNION,
        ENUM,
        INPUT_OBJECT,
        LIST,
        NON_NULL,
    }
    private class __Field(val name: String, val description: String?, val args: Map<String, KType>, val type: KType) {
        constructor(def: Schema.PropertyDefinition<*, *>) : this(def.name, def.description, def.arguments, def.ret)
        constructor(def: Schema.OperationDefinition<*>) : this(def.name, def.description, def.arguments, def.ret)
        constructor(def: Schema.SubscriptionDefinition<*>) : this(def.name, def.description, def.arguments, def.ret)
    }
    private class __EnumValue(val value: Enum<*>)
    private class __Directive
    private class __InputValue(val name: String, val type: KType)
    private object Query
    private object Mutation
    private object Subscription

    internal fun build(): Schema {
        if (introspection) {
            type<__Schema> {
                property<String?>("description") {
                    resolver {
                        null
                    }
                }

                property("types") {
                    resolver {
                        val types = listOfNotNull(
                            // Primitives
                            typeOf<Int?>(),
                            typeOf<Long?>(),
                            typeOf<String?>(),
                            typeOf<Boolean?>(),
                            typeOf<Float?>(),
                            typeOf<Double?>(),

                            // Operations
                            typeOf<Query?>(),
                            typeOf<Mutation?>().takeIf { mutations.isNotEmpty() },
                            typeOf<Subscription?>().takeIf { subscriptions.isNotEmpty() },
                        ) +
                                typeMap.filterValues { !it.name.startsWith("__") }.keys.map { it.withNullability(true) } +
                                enumMap.filterValues { !it.name.startsWith("__") }.keys.map { it.withNullability(true) }

                        types.map(::__Type)
                    }
                }

                property("queryType") {
                    resolver {
                        __Type(typeOf<Query?>())
                    }
                }

                property("mutationType") {
                    resolver {
                        __Type(typeOf<Mutation?>()).takeIf { mutations.isNotEmpty() }
                    }
                }

                property("subscriptionType") {
                    resolver {
                        __Type(typeOf<Subscription?>()).takeIf { subscriptions.isNotEmpty() }
                    }
                }

                property<List<__Directive>>("directives") {
                    resolver {
                        emptyList()
                    }
                }
            }

            type<__Type> {
                property("kind") {
                    resolver {
                        val nonNull = type.withNullability(false)
                        when {
                            !type.isMarkedNullable -> __TypeKind.NON_NULL
                            type.classifier == List::class -> __TypeKind.LIST
                            enumMap.containsKey(nonNull) -> __TypeKind.ENUM
                            interfaceMap.containsKey(nonNull) -> __TypeKind.INTERFACE
                            typeMap.containsKey(nonNull) && typeMap[nonNull]!!.scalarSerializer != null -> __TypeKind.SCALAR
                            typeMap.containsKey(nonNull) || type == typeOf<Query?>() || type == typeOf<Mutation?>() || type == typeOf<Subscription?>() -> __TypeKind.OBJECT
                            else -> __TypeKind.SCALAR
                        }
                    }
                }

                property("name") {
                    resolver {
                        when (this.type) {
                            typeOf<Query?>() -> "Query"
                            typeOf<Mutation?>() -> "Mutation"
                            typeOf<Subscription?>() -> "Subscription"
                            else -> type.withNullability(true).gqlName.takeIf { type.classifier != List::class && type.isMarkedNullable }
                        }
                    }
                }

                property<String?>("description") {
                    resolver {
                        typeMap[type.withNullability(false)]?.description
                    }
                }

                property<String?>("specifiedBy") {
                    resolver {
                        null
                    }
                }

                property<List<__Field>?>("fields") {
                    val includeDeprecated = argument<Boolean?>("includeDeprecated")

                    resolver { ctx ->
                        val deprecated = ctx.includeDeprecated() ?: false

                        when (type) {
                            typeOf<Query?>() -> {
                                queries.filterValues { !it.name.startsWith("__") }.map { (k, v) -> __Field(v) }
                            }
                            typeOf<Mutation?>() -> {
                                mutations.filterValues { !it.name.startsWith("__") }.map { (k, v) -> __Field(v) }
                            }
                            typeOf<Subscription?>() -> {
                                subscriptions.filterValues { !it.name.startsWith("__") }.map { (k, v) -> __Field(v) }
                            }
                            else -> {
                                typeMap[type.withNullability(false)]?.properties?.values?.filter { !it.name.startsWith("__") }?.map(::__Field)
                            }
                        }
                    }
                }

                property<List<__Type>?>("interfaces") {
                    resolver {
                        when (type) {
                            typeOf<Query?>(), typeOf<Mutation?>(), typeOf<Subscription?>() -> {
                                emptyList()
                            }
                            else -> {
                                typeMap[type.withNullability(false)]?.interfaces?.map { __Type(it.withNullability(true)) }
                            }
                        }
                    }
                }

                property<List<__Type>?>("possibleTypes") {
                    resolver {
                        val notNull = type.withNullability(false)
                        if (notNull in interfaceMap.keys) {
                            typeMap.filterValues { notNull in it.interfaces }.map { (k, _) -> __Type(k.withNullability(true)) }
                        } else {
                            null
                        }
                    }
                }

                property<List<__EnumValue>?>("enumValues") {
                    val includeDeprecated = argument<Boolean?>("includeDeprecated")

                    resolver { ctx ->
                        val deprecated = ctx.includeDeprecated() ?: false
                        (this@SchemaBuilder).enumMap[type.withNullability(false)]?.entries?.map(::__EnumValue)
                    }
                }

                property<List<__InputValue>?>("inputFields") {
                    val includeDeprecated = argument<Boolean?>("includeDeprecated")

                    resolver { ctx ->
                        val deprecated = ctx.includeDeprecated() ?: false
                        null
                    }
                }

                property("ofType") {
                    resolver {
                        when {
                            !type.isMarkedNullable -> __Type(type.withNullability(true))
                            type.classifier == List::class -> __Type(type.arguments[0].type!!)
                            else -> null
                        }
                    }
                }
            }

            type<__Field> {
                property(__Field::name)
                property(__Field::description)
                property<List<__InputValue>>("args") {
                    resolver {
                        args.map { (n, t) -> __InputValue(n, t) }
                    }
                }
                property("type") {
                    resolver {
                        __Type(type)
                    }
                }
                property("isDeprecated") {
                    resolver {
                        false
                    }
                }
                property<String?>("deprecationReason") {
                    resolver {
                        null
                    }
                }
            }

            type<__InputValue> {
                property(__InputValue::name)
                property<String?>("description") {
                    resolver {
                        null
                    }
                }
                property("type") {
                    resolver {
                        __Type(type)
                    }
                }
                property<String?>("defaultValue") {
                    resolver {
                        null
                    }
                }
                property("isDeprecated") {
                    resolver {
                        false
                    }
                }
                property<String?>("deprecationReason") {
                    resolver {
                        null
                    }
                }
            }

            type<__EnumValue> {
                property("name") {
                    resolver {
                        value.name
                    }
                }
                property<String?>("description") {
                    resolver {
                        null
                    }
                }
                property("isDeprecated") {
                    resolver {
                        false
                    }
                }
                property<String?>("deprecationReason") {
                    resolver {
                        null
                    }
                }
            }

            type<__Directive> {

            }

            enum(__TypeKind.entries)

            query("__schema") {
                resolver {
                    __Schema
                }
            }

            query("__type") {
                val type = argument<String>("name")
                resolver { ctx ->
                    val def = typeMap.values.firstOrNull { it.name == ctx.type() }
                    def?.type?.let(::__Type)
                }
            }
        }

        fun ensureType(type: KType) {
            if (!typeMap.containsKey(type) && !enumMap.containsKey(type)) {
                if ((type.classifier as KClass<*>?) !in EXCLUDED_CLASSES) {
                    throw IllegalStateException("Type $type was returned, but not defined in the schema!")
                } else if (type.classifier == List::class) {
                    ensureType(type.arguments[0].type!!.withNullability(false))
                }
            }
        }

        for (type in requestedTypes) {
            ensureType(type.withNullability(false))
        }

        return Schema(typeMap, enumMap, interfaceMap, queries, mutations, subscriptions)
    }

    companion object {
        private val EXCLUDED_CLASSES = arrayOf(
            String::class,
            Int::class,
            Long::class,
            Float::class,
            Double::class,
            Boolean::class,
            List::class,
        )
    }
}
