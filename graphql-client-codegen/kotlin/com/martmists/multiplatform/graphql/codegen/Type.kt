package com.martmists.multiplatform.graphql.codegen

open class Type(
    val name: String,
    val isScalar: Boolean,
    val isNullable: Boolean,
    val isList: Boolean,
    val isEnum: Boolean,
) {
    fun isModel() = !isScalar && !isList && !isEnum

    companion object {
        val scalars = setOf(
            "String",
            "Int",
            "Long",
            "Short",
            "Float",
            "Boolean",
            "ID",
        )
    }
}
