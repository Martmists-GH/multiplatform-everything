package com.martmists.multiplatform.graphql.operation

import com.martmists.multiplatform.graphql.Schema
import com.martmists.multiplatform.graphql.SchemaRequestContext
import com.martmists.multiplatform.graphql.parser.ast.*
import kotlin.reflect.KType

class Subscription(context: SchemaRequestContext, select: List<Selection>) : SchemaOperation(context, select, "Subscription") {
    companion object {
        fun make(schema: Schema, def: OperationDefinition, vars: Map<String, Any?>, frags: List<FragmentDefinition>, contexts: Map<KType, Any?>): Subscription {
            val ctx = SchemaRequestContext(schema, def.variableDefinitions, vars.mapValues(::DummyValue), frags, contexts)
            return Subscription(ctx, def.selectionSet)
        }
    }
}
