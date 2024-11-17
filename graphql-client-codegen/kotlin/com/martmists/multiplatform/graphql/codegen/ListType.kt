package com.martmists.multiplatform.graphql.codegen

class ListType(
    val elementType: Type,
    isNullable: Boolean,
) : Type(elementType.name, false, isNullable, true, false)
