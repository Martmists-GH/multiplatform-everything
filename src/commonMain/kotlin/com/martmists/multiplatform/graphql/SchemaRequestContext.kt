package com.martmists.multiplatform.graphql

import com.martmists.multiplatform.graphql.parser.*

class SchemaRequestContext(
    private val variableDefinitions: VariablesDefinition,
    private val vars: Map<String, Any?>,
    private val frags: List<FragmentDefinition>,
) {
    private val variables = variableDefinitions.associate {
        it.variable.name to (vars[it.variable.name] ?: it.defaultValue!!.on(this))
    }

    internal fun expand(type: String, selection: SelectionSet): List<Field> {
        val fields = selection.filterIsInstance<Field>() + selection.filterIsInstance<FragmentSpread>().flatMap { expand(type, it) }
        return fields
    }

    internal fun expand(type: String, fragment: FragmentSpread): List<Field> {
        val set = frags.find { it.typeCond.name == type && it.name == fragment.name }?.selectionSet ?: return emptyList()
        val fields = set.filterIsInstance<Field>() + set.filterIsInstance<FragmentSpread>().flatMap { expand(type, it) }
        return fields
    }

    fun <T> variable(name: String): T {
        return variables[name] as T
    }

    fun withArguments(args: Arguments): SchemaRequestContext {
        val newVars = args.associate {
            it.name to it.value.on(this)
        }

        return SchemaRequestContext(variableDefinitions, newVars, frags)
    }
}
