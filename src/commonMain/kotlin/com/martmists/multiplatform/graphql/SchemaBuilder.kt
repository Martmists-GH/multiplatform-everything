package com.martmists.multiplatform.graphql

import kotlin.reflect.KProperty1
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class SchemaBuilder {
    private val typeMap = mutableMapOf<KType, Schema.TypeDefinition<*>>()
    private val queries = mutableMapOf<String, Schema.OperationDefinition<*>>()
    private val mutations = mutableMapOf<String, Schema.OperationDefinition<*>>()
    private val requestedTypes = mutableSetOf<KType>()

    class BackedPropertyBuilder<T, R> internal constructor(private val name: String, private val type: KType, private val prop: KProperty1<T, R>) {
        private var rule: suspend T.(SchemaRequestContext) -> Boolean = { true }

        fun accessRule(rule: suspend T.(SchemaRequestContext) -> Boolean) {
            this.rule = rule
        }

        internal fun build(): Schema.PropertyDefinition<T, R> {
            return Schema.PropertyDefinition(name, type, rule) { prop.get(this) }
        }
    }

    class PropertyBuilder<T, R> internal constructor(private val name: String, private val type: KType) {
        private var rule: suspend T.(SchemaRequestContext) -> Boolean = { true }
        private var getter: (suspend T.(SchemaRequestContext) -> R)? = null

        fun accessRule(rule: suspend T.(SchemaRequestContext) -> Boolean) {
            this.rule = rule
        }

        fun getter(getter: suspend T.(SchemaRequestContext) -> R) {
            this.getter = getter
        }

        internal fun build(): Schema.PropertyDefinition<T, R> {
            require(getter != null) { "Property $name has no getter" }
            return Schema.PropertyDefinition(name, type, rule, getter!!)
        }
    }

    inner class TypeBuilder<T> internal constructor(private val name: String, private val type: KType) {
        private val properties = mutableMapOf<String, Schema.PropertyDefinition<T, *>>()

        inline fun <reified R> property(name: String, noinline builder: PropertyBuilder<T, R>.() -> Unit) = property(name, typeOf<R>(), builder)
        inline fun <reified R> property(prop: KProperty1<T, R>, noinline builder: BackedPropertyBuilder<T, R>.() -> Unit = {}) = property(prop.name, typeOf<R>(), prop, builder)
        fun <R> property(name: String, type: KType, builder: PropertyBuilder<T, R>.() -> Unit) {
            requestedTypes.add(type)
            val propBuilder = PropertyBuilder<T, R>(name, type)
            propBuilder.builder()
            properties[name] = propBuilder.build()
        }
        fun <R> property(name: String, type: KType, prop: KProperty1<T, R>, builder: BackedPropertyBuilder<T, R>.() -> Unit) {
            requestedTypes.add(type)
            val propBuilder = BackedPropertyBuilder(name, type, prop)
            propBuilder.builder()
            properties[name] = propBuilder.build()
        }

        internal fun build(): Schema.TypeDefinition<T> {
            return Schema.TypeDefinition(name, type, properties)
        }
    }

    class OperationBuilder<T> internal constructor(private val name: String, private val type: KType) {
        private var rule: suspend (SchemaRequestContext) -> Boolean = { true }
        private var executor: (suspend (SchemaRequestContext) -> T)? = null

        fun accessRule(rule: suspend (SchemaRequestContext) -> Boolean) {
            this.rule = rule
        }

        fun executor(executor: suspend (SchemaRequestContext) -> T) {
            this.executor = executor
        }

        internal fun build(): Schema.OperationDefinition<T> {
            require(executor != null) { "Operation $name has no executor" }
            return Schema.OperationDefinition(name, type, rule, executor!!)
        }
    }

    inline fun <reified T> type(noinline block: TypeBuilder<T>.() -> Unit) = type(typeOf<T>(), block)
    fun <T> type(type: KType, block: TypeBuilder<T>.() -> Unit) {
        val typeBuilder = TypeBuilder<T>(type.toString(), type)
        typeBuilder.block()
        typeMap[type] = typeBuilder.build()
    }

    inline fun <reified T> query(name: String, noinline block: OperationBuilder<T>.() -> Unit) = query(name, typeOf<T>(), block)
    fun <T> query(name: String, type: KType, block: OperationBuilder<T>.() -> Unit) {
        requestedTypes.add(type)
        val operationBuilder = OperationBuilder<T>(name, type)
        operationBuilder.block()
        queries[name] = operationBuilder.build()
    }

    internal fun build(): Schema {
        for (type in requestedTypes) {
            if (!typeMap.containsKey(type)) {
                if (type.classifier !in EXCLUDED_CLASSES) {
                    throw IllegalStateException("Type $type was returned, but not defined in the schema!")
                }
            }
        }

        return Schema(typeMap, queries, mutations)
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
            Array::class,
        )
    }
}
