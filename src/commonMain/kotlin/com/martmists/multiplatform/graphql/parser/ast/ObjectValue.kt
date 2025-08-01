package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.graphql.SchemaRequestContext
import com.martmists.multiplatform.parsing.core.Loc
import kotlin.reflect.KType

data class ObjectValue(val value: Map<String, Value>,
                       override val loc: Loc
) : Value {
    override fun on(context: SchemaRequestContext, type: KType): Map<String, Any?> {
        return value.mapValues { it.value.on(context, type.arguments[1].type!!) }
    }
}
