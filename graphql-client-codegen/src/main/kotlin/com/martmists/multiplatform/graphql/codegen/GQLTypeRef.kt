package com.martmists.multiplatform.graphql.codegen

class GQLTypeRef(
    val name: String,
    val nullable: Boolean = false,
    val isList: Boolean = false,
    val ofType: GQLTypeRef? = null,
)
