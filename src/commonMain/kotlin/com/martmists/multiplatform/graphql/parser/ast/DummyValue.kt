package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.graphql.SchemaRequestContext
import com.martmists.multiplatform.parsing.core.Loc
import kotlin.reflect.KType

data class DummyValue(
    val value: Any?
): Value {
    override fun on(context: SchemaRequestContext, type: KType): Any? {
        return value
    }

    override val loc = Loc(-1, -1)
}
