package com.martmists.multiplatform.graphql

import kotlinx.serialization.json.JsonElement
import kotlin.reflect.KType

class Schema internal constructor(
    internal val typeMap: Map<KType, TypeDefinition<*>>,
    internal val queries: Map<String, OperationDefinition<*>>,
    private val mutations: Map<String, OperationDefinition<*>>,
) {
    data class PropertyDefinition<T, R>(
        val name: String,
        val ret: KType,
        val rule: suspend T.(SchemaRequestContext) -> Boolean,
        val getter: suspend T.(SchemaRequestContext) -> R,
    )

    data class TypeDefinition<T>(
        val name: String,
        val type: KType,
        val properties: Map<String, PropertyDefinition<T, *>>
    )

    data class OperationDefinition<T>(
        val name: String,
        val ret: KType,
        val rule: suspend (SchemaRequestContext) -> Boolean,
        val getter: suspend (SchemaRequestContext) -> T
    )
}
