package com.martmists.multiplatform.graphql

import com.martmists.multiplatform.graphql.ext.gqlName
import com.martmists.multiplatform.reflect.withNullability
import kotlin.enums.EnumEntries
import kotlin.reflect.*

@GraphQLDSL
class SchemaBuilder {
    private val typeMap = mutableMapOf<KType, Schema.TypeDefinition<*>>()
    private val enumMap = mutableMapOf<KType, Schema.EnumDefinition<*>>()
    private val interfaceMap = mutableMapOf<KType, suspend Any?.() -> KType>()
    private val queries = mutableMapOf<String, Schema.OperationDefinition<*>>()
    private val mutations = mutableMapOf<String, Schema.OperationDefinition<*>>()
    private val requestedTypes = mutableSetOf<KType>()

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

    @GraphQLDSL
    class BackedPropertyBuilder<T, R> internal constructor(private val name: String, private val type: KType, private val prop: KProperty1<T, R>) : BaseBuilder() {
        private var rule: suspend T.(SchemaRequestContext) -> Boolean = { true }

        /**
         * Adds an access rule to this property. The rule will be called on the object instance, and if it returns false,
         * the property will not be included in the response.
         */
        fun accessRule(rule: suspend T.(ctx: SchemaRequestContext) -> Boolean) {
            this.rule = rule
        }

        internal fun build(): Schema.PropertyDefinition<T, R> {
            return Schema.PropertyDefinition(name, description, type, rule, emptyMap()) { prop.get(this) }
        }
    }

    @GraphQLDSL
    class PropertyBuilder<T, R> internal constructor(private val name: String, private val type: KType) : BuilderWithArgument() {
        private var rule: suspend T.(SchemaRequestContext) -> Boolean = { true }
        private var resolver: (suspend T.(SchemaRequestContext) -> R)? = null

        /**
         * Adds an access rule to this property. The rule will be called on the object instance, and if it returns false,
         * the property will not be included in the response.
         */
        fun accessRule(rule: suspend T.(ctx: SchemaRequestContext) -> Boolean) {
            this.rule = rule
        }

        /**
         * Sets the resolver for this property. The resolver will be called on the object instance, and its result will be
         * returned in the response.
         */
        fun resolver(getter: suspend T.(ctx: SchemaRequestContext) -> R) {
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
        inline fun <reified R> property(name: String, noinline builder: PropertyBuilder<T, R>.() -> Unit) = property(name, typeOf<R>(), builder)

        /**
         * Registers a bound property on this type.
         */
        inline fun <reified R> property(prop: KProperty1<T, R>, noinline builder: BackedPropertyBuilder<T, R>.() -> Unit = {}) = property(prop.name, typeOf<R>(), prop, builder)
        fun <R> property(name: String, type: KType, builder: PropertyBuilder<T, R>.() -> Unit) {
            (this@SchemaBuilder).requestedTypes.add(type.withNullability(false))
            val propBuilder = PropertyBuilder<T, R>(name, type)
            propBuilder.builder()
            properties[name] = propBuilder.build()
        }
        fun <R> property(name: String, type: KType, prop: KProperty1<T, R>, builder: BackedPropertyBuilder<T, R>.() -> Unit) {
            (this@SchemaBuilder).requestedTypes.add(type.withNullability(false))
            val propBuilder = BackedPropertyBuilder(name, type, prop)
            propBuilder.builder()
            properties[name] = propBuilder.build()
        }
    }

    @GraphQLDSL
    inner class TypeBuilder<T> internal constructor(private val type: KType): BaseTypeBuilder<T>() {
        private val interfaces = mutableListOf<KType>()


        inline fun <reified T> usesInterface() = usesInterface(typeOf<T>())
        fun usesInterface(type: KType) {
            interfaces += type
        }

        internal fun build(): Schema.TypeDefinition<T> {
            return Schema.TypeDefinition(type.withNullability(true).gqlName, description, type, properties, interfaces)
        }
    }

    @GraphQLDSL
    inner class InterfaceTypeBuilder<T> internal constructor(private val type: KType): BaseTypeBuilder<T>() {
        private var typeResolver: (suspend T.() -> KType)? = null

        fun resolver(typeResolver: suspend T.() -> KType) {
            this.typeResolver = typeResolver
        }

        internal fun build(): Pair<Schema.TypeDefinition<T>, (suspend T.() -> KType)> {
            require(typeResolver != null) { "Interface ${type.gqlName} has no getter" }
            return Schema.TypeDefinition(type.withNullability(true).gqlName, description, type, properties, emptyList()) to typeResolver!!
        }
    }

    @GraphQLDSL
    class OperationBuilder<T> internal constructor(private val name: String, private val type: KType): BuilderWithArgument() {
        private var rule: suspend (SchemaRequestContext) -> Boolean = { true }
        private var resolver: (suspend (SchemaRequestContext) -> T)? = null

        /**
         * Adds an access rule to this operation. If it returns false,
         * the operation will not be executed.
         */
        fun accessRule(rule: suspend (ctx: SchemaRequestContext) -> Boolean) {
            this.rule = rule
        }

        /**
         * Adds a resolver to this operation.
         */
        fun resolver(executor: suspend (ctx: SchemaRequestContext) -> T) {
            this.resolver = executor
        }

        internal fun build(): Schema.OperationDefinition<T> {
            require(resolver != null) { "Operation $name has no executor" }
            return Schema.OperationDefinition(name, description, type, rule, arguments, resolver!!)
        }
    }

    inline fun <reified T : Any> interfaceType(noinline block: InterfaceTypeBuilder<*>.() -> Unit) = interfaceType<T>(typeOf<T>(), block)
    fun <T : Any> interfaceType(type: KType, block: InterfaceTypeBuilder<T>.() -> Unit) {
        val builder = InterfaceTypeBuilder<T>(type)
        builder.block()
        val (def, resolver) = builder.build()
        typeMap[type] = def
        interfaceMap[type] = resolver as suspend Any?.() -> KType
    }

    /**
     * Registers a type to the type system. All fields you wish to expose must be exposed manually.
     */
    inline fun <reified T : Any> type(noinline block: TypeBuilder<T>.() -> Unit) = type(typeOf<T>(), block)
    fun <T : Any> type(type: KType, block: TypeBuilder<T>.() -> Unit) {
        val typeBuilder = TypeBuilder<T>(type)
        typeBuilder.block()
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
    inline fun <reified T> query(name: String, noinline block: OperationBuilder<T>.() -> Unit) = query(name, typeOf<T>(), block)
    fun <T> query(name: String, type: KType, block: OperationBuilder<T>.() -> Unit) {
        requestedTypes.add(type)
        val operationBuilder = OperationBuilder<T>(name, type)
        operationBuilder.block()
        queries[name] = operationBuilder.build()
    }

    /**
     * Defines a mutation operation to the schema.
     */
    inline fun <reified T> mutation(name: String, noinline block: OperationBuilder<T>.() -> Unit) = mutation(name, typeOf<T>(), block)
    fun <T> mutation(name: String, type: KType, block: OperationBuilder<T>.() -> Unit) {
        requestedTypes.add(type)
        val operationBuilder = OperationBuilder<T>(name, type)
        operationBuilder.block()
        mutations[name] = operationBuilder.build()
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
                        val types = listOf(
                            // Primitives
                            typeOf<Int>(),
                            typeOf<Long>(),
                            typeOf<String>(),
                            typeOf<Boolean>(),
                            typeOf<Float>(),
                            typeOf<Double>(),

                            // Operations
                            typeOf<Query>(),
                            typeOf<Mutation>(),
                            typeOf<Subscription>(),
                        ) + (this@SchemaBuilder).typeMap.keys

                        types.map(::__Type)
                    }
                }

                property("queryType") {
                    resolver {
                        __Type(typeOf<Query>())
                    }
                }

                property("mutationType") {
                    resolver {
                        __Type(typeOf<Mutation>())
                    }
                }

                property("subscriptionType") {
                    resolver {
                        __Type(typeOf<Subscription>())
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
                        when {
                            !type.isMarkedNullable -> __TypeKind.NON_NULL
                            type.classifier == List::class -> __TypeKind.LIST
                            (this@SchemaBuilder).enumMap.containsKey(type) -> __TypeKind.ENUM
                            (this@SchemaBuilder).typeMap.containsKey(type) || type == typeOf<Query>() || type == typeOf<Mutation>() || type == typeOf<Subscription>() -> __TypeKind.OBJECT
                            else -> __TypeKind.SCALAR
                        }
                    }
                }

                property("name") {
                    resolver {
                        type.withNullability(true).gqlName.takeIf { type.classifier != List::class && type.isMarkedNullable }
                    }
                }

                property<String?>("description") {
                    resolver {
                        (this@SchemaBuilder).typeMap[type.withNullability(false)]?.description
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
                            typeOf<Query>() -> {
                                (this@SchemaBuilder).queries.map { (k, v) -> __Field(v) }
                            }
                            typeOf<Mutation>() -> {
                                (this@SchemaBuilder).mutations.map { (k, v) -> __Field(v) }
                            }
                            typeOf<Subscription>() -> {
                                emptyList()
                            }
                            else -> {
                                (this@SchemaBuilder).typeMap[type.withNullability(false)]?.properties?.values?.map(::__Field)
                            }
                        }
                    }
                }

                property<List<__Type>?>("interfaces") {
                    resolver {
                        when (type) {
                            typeOf<Query>() -> {
                                emptyList()
                            }
                            typeOf<Mutation>() -> {
                                emptyList()
                            }
                            typeOf<Subscription>() -> {
                                emptyList()
                            }
                            else -> {
                                (this@SchemaBuilder).typeMap[type.withNullability(false)]?.interfaces?.map(::__Type)
                            }
                        }
                    }
                }

                property<List<__Type>?>("possibleTypes") {
                    resolver {
                        null
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
                        if (type.classifier == List::class) {
                            __Type(type.arguments[0].type!!)
                        } else {
                            type.takeIf { !it.isMarkedNullable }?.let { __Type(it.withNullability(true)) }
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

            enum(__TypeKind.entries)

            query("__schema") {
                resolver {
                    __Schema
                }
            }

            query("__type") {
                val type = argument<String>("name")
                resolver { ctx ->
                    val def = (this@SchemaBuilder).typeMap.values.firstOrNull { it.name == ctx.type() }
                    def?.type?.let(::__Type)
                }
            }
        }

        for (type in requestedTypes) {
            val t = type.withNullability(false)

            if (!typeMap.containsKey(t) && !enumMap.containsKey(t)) {
                if ((t.classifier as KClass<*>?) !in EXCLUDED_CLASSES) {
                    throw IllegalStateException("Type $t was returned, but not defined in the schema!")
                }
            }
        }

        return Schema(typeMap, enumMap, interfaceMap, queries, mutations)
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
