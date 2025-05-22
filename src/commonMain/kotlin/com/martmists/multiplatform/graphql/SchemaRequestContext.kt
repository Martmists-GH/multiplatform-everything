package com.martmists.multiplatform.graphql

import com.martmists.multiplatform.graphql.ext.gqlName
import com.martmists.multiplatform.graphql.parser.ast.*
import com.martmists.multiplatform.reflect.withNullability
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class SchemaRequestContext(
    internal val schema: Schema,
    private val variableDefinitions: List<VariableDefinition>,
    internal val vars: Map<String, Value>,
    private val frags: List<FragmentDefinition>,
    private val contexts: Map<KType, Any?>
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
        val fields = selection.filterIsInstance<Field>() +
                selection.filterIsInstance<FragmentSpread>().flatMap { expand(type, it) } +
                selection.filterIsInstance<InlineFragment>().flatMap { expand(type, it) }
        return fields
    }

    private fun expand(type: String, fragment: FragmentSpread): List<Field> {
        val set = frags.find { it.typeCond.name == type && it.name == fragment.name }?.selectionSet ?: return emptyList()
        return expand(type, set)
    }

    private fun expand(type: String, fragment: InlineFragment): List<Field> {
        if (fragment.typeCond?.name != type) return emptyList()
        return expand(type, fragment.selectionSet)
    }

    internal fun <T> variable(name: String): T {
        @Suppress("UNCHECKED_CAST")
        return variables[name] as T
    }

    inline fun <reified T> context() = context<T>(typeOf<T>())
    fun <T> context(type: KType): T? {
        @Suppress("UNCHECKED_CAST")
        return contexts[type] as T?
    }

    fun expand(arg: Value): Value {
        return if (arg is Variable) {
            vars[arg.name] ?: arg
        } else arg
    }

    internal fun withArguments(args: List<Argument>): SchemaRequestContext {
        return SchemaRequestContext(schema, variableDefinitions, vars + args.associate { it.name to expand(it.value) }, frags, contexts)
    }
}
