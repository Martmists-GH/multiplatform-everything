package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.graphql.SchemaRequestContext
import com.martmists.multiplatform.parsing.core.Loc
import kotlin.reflect.KType

data class FloatValue(val value: Float,
                      override val loc: Loc
) : Value {
    override fun on(context: SchemaRequestContext, type: KType): Float {
        return value
    }
}
