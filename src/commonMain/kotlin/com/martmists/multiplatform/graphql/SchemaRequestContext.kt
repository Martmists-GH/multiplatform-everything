package com.martmists.multiplatform.graphql

import com.martmists.multiplatform.graphql.ext.gqlName
import com.martmists.multiplatform.graphql.parser.ast.*
import com.martmists.multiplatform.reflect.withNullability
import kotlin.reflect.KType

class SchemaRequestContext(
    internal val schema: Schema,
    private val variableDefinitions: List<VariableDefinition>,
    internal val vars: Map<String, Value>,
    private val frags: List<FragmentDefinition>,
) {
    internal val variables = mutableMapOf<String, Any?>()

    internal fun expand(type: KType, selection: List<Selection>): List<Field> {
        var t = type
        while (t.classifier == List::class) {
            t = t.arguments[0].type!!
        }
        return expand(t.withNullability(true).gqlName, selection)
    }

    internal fun expand(type: String, selection: List<Selection>): List<Field> {
        val fields = selection.filterIsInstance<Field>() + selection.filterIsInstance<FragmentSpread>().flatMap { expand(type, it) }
        return fields
    }

    private fun expand(type: String, fragment: FragmentSpread): List<Field> {
        val set = frags.find { it.typeCond.name == type && it.name == fragment.name }?.selectionSet ?: return emptyList()
        val fields = set.filterIsInstance<Field>() + set.filterIsInstance<FragmentSpread>().flatMap { expand(type, it) }
        return fields
    }

    internal fun <T> variable(name: String): T {
        return variables[name] as T
    }

    internal fun withArguments(args: List<Argument>): SchemaRequestContext {
        return SchemaRequestContext(schema, variableDefinitions, args.associate { it.name to it.value }, frags)
    }
}
