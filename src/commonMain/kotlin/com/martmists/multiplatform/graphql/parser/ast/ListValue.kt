package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.graphql.SchemaRequestContext
import com.martmists.multiplatform.parsing.core.Loc
import kotlin.reflect.KType

data class ListValue(val value: List<Value>,
                     override val loc: Loc
) : Value {
    override fun on(context: SchemaRequestContext, type: KType): List<Any?> {
        return value.map { it.on(context, type.arguments[0].type!!) }
    }
}
