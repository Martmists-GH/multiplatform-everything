package com.martmists.multiplatform.graphql

import kotlin.enums.EnumEntries
import kotlin.reflect.KType

class Schema internal constructor(
    val typeMap: Map<KType, TypeDefinition<*>>,
    val enumMap: Map<KType, EnumDefinition<*>>,
    val queries: Map<String, OperationDefinition<*>>,
    val mutations: Map<String, OperationDefinition<*>>,
) {
    data class PropertyDefinition<T, R>(
        val name: String,
        val ret: KType,
        val rule: suspend T.(SchemaRequestContext) -> Boolean,
        val arguments: Map<String, KType>,
        val resolver: suspend T.(SchemaRequestContext) -> R,
    )

    data class TypeDefinition<T>(
        val name: String,
        val type: KType,
        val properties: Map<String, PropertyDefinition<T, *>>
    )

    data class EnumDefinition<T : Enum<T>>(
        val name: String,
        val type: KType,
        val entries: EnumEntries<T>,
    )

    data class OperationDefinition<T>(
        val name: String,
        val ret: KType,
        val rule: suspend (SchemaRequestContext) -> Boolean,
        val arguments: Map<String, KType>,
        val resolver: suspend (SchemaRequestContext) -> T
    )
}
