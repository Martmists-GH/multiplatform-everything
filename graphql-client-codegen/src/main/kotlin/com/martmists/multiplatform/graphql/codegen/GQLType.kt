package com.martmists.multiplatform.graphql.codegen

class GQLType(
    val kind: TypeKind,
    val name: String,
    val properties: Map<String, GQLTypeRef> = emptyMap(),
    val methods: Map<String, GQLMethod> = emptyMap(),
    val interfaces: List<GQLTypeRef> = emptyList(),
    val enumValues: List<String> = emptyList(),
    val ofType: GQLType? = null
)
