package com.martmists.multiplatform.graphql.parser.ast

import com.martmists.multiplatform.graphql.SchemaRequestContext
import com.martmists.multiplatform.graphql.parser.Loc
import kotlin.reflect.KType

data class IntValue(val value: Long,
                    override val loc: Loc
) : Value {
    override fun on(context: SchemaRequestContext, type: KType): Long {
        return value
    }
}
