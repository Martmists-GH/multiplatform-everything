package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.graphql.SchemaRequestContext
import com.martmists.multiplatform.reflect.withNullability
import com.martmists.multiplatform.parsing.core.Loc
import kotlin.reflect.KType

data class EnumValue(val value: String,
                     override val loc: Loc
) : Value {
    override fun on(context: SchemaRequestContext, type: KType): Any {
        return context.schema.enumMap[type.withNullability(false)]!!.entries.first { it.name == value }
    }
}
