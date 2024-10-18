package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.graphql.SchemaRequestContext
import com.martmists.multiplatform.validation.lexer.Loc
import kotlin.reflect.KType

data class StringValue(val value: String,
                       override val loc: Loc
) : Value {
    override fun on(context: SchemaRequestContext, type: KType): String {
        return value
    }
}
