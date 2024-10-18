package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.graphql.SchemaRequestContext
import kotlin.reflect.KType

sealed interface Value : AstNode {
    fun on(context: SchemaRequestContext, type: KType): Any?
}
