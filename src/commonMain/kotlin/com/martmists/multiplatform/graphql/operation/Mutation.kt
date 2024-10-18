package com.martmists.multiplatform.graphql.operation

import com.martmists.multiplatform.graphql.Schema
import com.martmists.multiplatform.graphql.SchemaRequestContext
import com.martmists.multiplatform.graphql.parser.ast.*

class Mutation(context: SchemaRequestContext, select: List<Selection>) : SchemaOperation(context, select, "Mutation") {
    companion object {
        fun make(schema: Schema, def: OperationDefinition, vars: Map<String, Any?>, frags: List<FragmentDefinition>): Mutation {
            val ctx = SchemaRequestContext(schema, def.variableDefinitions, vars.mapValues(::DummyValue), frags)
            return Mutation(ctx, def.selectionSet)
        }
    }
}
