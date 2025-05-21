package com.martmists.multiplatform.graphql

import com.martmists.multiplatform.graphql.ext.gqlName
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer
import kotlin.enums.EnumEntries
import kotlin.reflect.KType

class Schema internal constructor(
    val typeMap: Map<KType, TypeDefinition<*>>,
    val enumMap: Map<KType, EnumDefinition<*>>,
    val interfaceMap: Map<KType, suspend Any?.() -> KType>,
    val queries: Map<String, OperationDefinition<*>>,
    val mutations: Map<String, OperationDefinition<*>>,
    val subscriptions: Map<String, SubscriptionDefinition<*>>,
) {
    data class PropertyDefinition<T, R>(
        val name: String,
        val description: String?,
        val ret: KType,
        val rule: suspend T.(SchemaRequestContext) -> Boolean,
        val arguments: Map<String, KType>,
        val resolver: suspend T.(SchemaRequestContext) -> R,
    )

    data class TypeDefinition<T>(
        val name: String,
        val description: String?,
        val type: KType,
        val properties: Map<String, PropertyDefinition<T, *>>,
        val interfaces: List<KType>,
        val scalarSerializer: KSerializer<T>?,
    )

    data class EnumDefinition<T : Enum<T>>(
        val name: String,
        val type: KType,
        val entries: EnumEntries<T>
    )

    data class OperationDefinition<T>(
        val name: String,
        val description: String?,
        val ret: KType,
        val rule: suspend (SchemaRequestContext) -> Boolean,
        val arguments: Map<String, KType>,
        val resolver: suspend (SchemaRequestContext) -> T
    )

    data class SubscriptionDefinition<T>(
        val name: String,
        val description: String?,
        val ret: KType,
        val rule: suspend (SchemaRequestContext) -> Boolean,
        val arguments: Map<String, KType>,
        val resolver: suspend (SchemaRequestContext) -> Flow<T>
    )

    fun graphqls(): String {
        val sb = StringBuilder()
        for ((_, enum) in enumMap) {
            if (enum.name.startsWith("__")) continue

            sb.append("enum ${enum.name.removeSuffix("!")} {\n")
            for (entry in enum.entries) {
                sb.append("    ${entry.name}\n")
            }
            sb.append("}\n\n")
        }

        fun typeName(type: KType): String {
            if (type.classifier == List::class) {
                var t = "[" + typeName(type.arguments[0].type!!) + "]"
                if (!type.isMarkedNullable) {
                    t = "$t!"
                }
                return t
            }
            return type.gqlName
        }

        for ((ref, type) in typeMap) {
            if (type.name.startsWith("__")) continue

            if (type.scalarSerializer != null) {
                if (type.name !in arrayOf("String", "Int", "Float", "Boolean")) {
                    sb.append("scalar ${type.name}\n")
                }
                continue
            }

            val kind = if (ref in interfaceMap) "interface" else "type"

            sb.append("$kind ${type.name.removeSuffix("!")}")
            if (type.interfaces.isNotEmpty()) {
                sb.append(" implements ")
                sb.append(type.interfaces.joinToString(", ") { typeName(it).removeSuffix("!") })
            }
            sb.append(" {\n")
            for ((name, prop) in type.properties) {
                if (name.startsWith("__")) continue

                sb.append("    $name")
                if (prop.arguments.isNotEmpty()) {
                    sb.append('(')
                    sb.append(prop.arguments.entries.joinToString(", ") { (k, v) -> "$k: ${typeName(v)}" })
                    sb.append(')')
                }
                sb.append(": ${typeName(prop.ret)}\n")
            }
            sb.append("}\n\n")
        }

        sb.append("type Query {\n")
        for ((name, query) in queries) {
            if (name.startsWith("__")) continue

            sb.append("    $name")
            if (query.arguments.isNotEmpty()) {
                sb.append('(')
                sb.append(query.arguments.entries.joinToString(", ") { (k, v) -> "$k: ${typeName(v)}" })
                sb.append(')')
            }
            sb.append(": ${typeName(query.ret)}\n")
        }
        sb.append("}\n\n")

        if (mutations.isNotEmpty()) {
            sb.append("type Mutation {\n")
            for ((name, mutation) in mutations) {
                if (name.startsWith("__")) continue

                sb.append("    $name")
                if (mutation.arguments.isNotEmpty()) {
                    sb.append('(')
                    sb.append(mutation.arguments.entries.joinToString(", ") { (k, v) -> "$k: ${typeName(v)}" })
                    sb.append(')')
                }
                sb.append(": ${typeName(mutation.ret)}\n")
            }
            sb.append("}\n\n")
        }

        if (subscriptions.isNotEmpty()) {
            sb.append("type Subscription {\n")
            for ((name, subscription) in subscriptions) {
                if (name.startsWith("__")) continue

                sb.append("    $name")
                if (subscription.arguments.isNotEmpty()) {
                    sb.append('(')
                    sb.append(subscription.arguments.entries.joinToString(", ") { (k, v) -> "$k: ${typeName(v)}" })
                    sb.append(')')
                }
                sb.append(": ${typeName(subscription.ret)}\n")
            }
            sb.append("}\n\n")
        }

        return sb.toString()
    }
}
