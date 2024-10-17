package com.martmists.multiplatform.graphql

import com.martmists.multiplatform.graphql.parser.*

class Query(val context: SchemaRequestContext, select: SelectionSet) {
    val fields = select.filterIsInstance<Field>() + select.filterIsInstance<FragmentSpread>().flatMap { context.expand("Query", it) }

    companion object {
        fun make(def: OperationDefinition, vars: Map<String, Any?>, frags: List<FragmentDefinition>): Query {
            val ctx = SchemaRequestContext(def.variableDefinitions, vars, frags)
            return Query(ctx, def.selectionSet)
        }
    }
}
