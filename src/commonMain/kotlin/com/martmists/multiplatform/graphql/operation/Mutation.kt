package com.martmists.multiplatform.graphql.operation

import com.martmists.multiplatform.graphql.Schema
import com.martmists.multiplatform.graphql.SchemaRequestContext
import com.martmists.multiplatform.graphql.parser.ast.*
import kotlin.reflect.KType

class Mutation(context: SchemaRequestContext, select: List<Selection>) : SchemaOperation(context, select, "Mutation") {
    companion object {
        fun make(schema: Schema, def: OperationDefinition, vars: Map<String, Any?>, frags: List<FragmentDefinition>, contexts: Map<KType, Any?>): Mutation {
            val ctx = SchemaRequestContext(schema, def.variableDefinitions, vars.mapValues(::DummyValue), frags, contexts)
            return Mutation(ctx, def.selectionSet)
        }
    }
}
