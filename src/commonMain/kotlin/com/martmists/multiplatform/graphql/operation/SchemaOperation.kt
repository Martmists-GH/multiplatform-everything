package com.martmists.multiplatform.graphql.operation

import com.martmists.multiplatform.graphql.SchemaRequestContext
import com.martmists.multiplatform.graphql.parser.ast.Selection

abstract class SchemaOperation(val context: SchemaRequestContext, select: List<Selection>, val type: String) {
    val fields = context.expand(type, select)
}
