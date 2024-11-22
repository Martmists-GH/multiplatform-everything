package com.martmists.multiplatform.graphql.codegen

class GQLMethod(
    val name: String,
    val returnType: GQLTypeRef,
    val arguments: Map<String, GQLTypeRef>,
)
