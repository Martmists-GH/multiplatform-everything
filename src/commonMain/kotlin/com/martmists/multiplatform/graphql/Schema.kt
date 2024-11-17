package com.martmists.multiplatform.graphql

import kotlinx.coroutines.flow.Flow
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
        val interfaces: List<KType>
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
}
