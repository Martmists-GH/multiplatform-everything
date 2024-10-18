package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.graphql.SchemaRequestContext
import com.martmists.multiplatform.graphql.parser.Loc
import kotlin.reflect.KType

data class BooleanValue(val value: Boolean, override val loc: Loc) : Value {
    override fun on(context: SchemaRequestContext, type: KType): Boolean {
        return value
    }
}
