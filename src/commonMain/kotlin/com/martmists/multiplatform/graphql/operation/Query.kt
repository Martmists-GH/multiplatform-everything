package com.martmists.multiplatform.graphql.operation

import com.martmists.multiplatform.graphql.Schema
import com.martmists.multiplatform.graphql.SchemaRequestContext
import com.martmists.multiplatform.graphql.parser.ast.*
import kotlin.reflect.KType

class Query(context: SchemaRequestContext, select: List<Selection>) : SchemaOperation(context, select, "Query") {
    companion object {
        fun make(schema: Schema, def: OperationDefinition, vars: Map<String, Any?>, frags: List<FragmentDefinition>, contexts: Map<KType, Any?>): Query {
            val ctx = SchemaRequestContext(schema, def.variableDefinitions, vars.mapValues { (_, v) -> DummyValue(v) }, frags, contexts)
            return Query(ctx, def.selectionSet)
        }
    }
}
