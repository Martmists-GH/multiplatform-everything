package com.martmists.multiplatform.graphql

import com.martmists.multiplatform.graphql.ext.gqlName
import com.martmists.multiplatform.graphql.operation.Mutation
import com.martmists.multiplatform.graphql.operation.Query
import com.martmists.multiplatform.graphql.operation.SchemaOperation
import com.martmists.multiplatform.graphql.parser.*
import com.martmists.multiplatform.graphql.parser.ast.Field
import com.martmists.multiplatform.graphql.parser.ast.FragmentDefinition
import com.martmists.multiplatform.graphql.parser.ast.OperationDefinition
import com.martmists.multiplatform.graphql.parser.ast.OperationType
import com.martmists.multiplatform.reflect.withNullability
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.*
import kotlin.reflect.KClass
import kotlin.reflect.KType

/**
 * A GraphQL Service.
 *
 * This is the entry point for all GraphQL operations.
 */
@GraphQLDSL
open class GraphQL {
    private var didInit = false
    private lateinit var schema: Schema

    /**
     * A wrapper function for each property accessed.
     *
     * If you want everything to happen on a specific context or want to wrap it (for example, with Exposed's newSuspendedTransaction), you can set this property to do so.
     */
    var wrapper: suspend (content: suspend () -> Unit) -> Unit = { it() }

    /**
     * Builds the GraphQL Schema.
     *
     * @throws IllegalStateException If the schema has already been built, or a type is returned from a field that was not registered.
     */
    fun schema(builder: SchemaBuilder.() -> Unit) {
        if (didInit) {
            throw IllegalStateException("Schema already built!")
        }
        val schemaBuilder = SchemaBuilder()
        schemaBuilder.builder()
        schema = schemaBuilder.build()
        didInit = true
    }

    /**
     * Executes a GraphQL operation.
     * This function takes a [JsonObject] to easily fit into any web application.
     */
    suspend fun execute(payload: JsonObject, contexts: Map<KType, Any?> = emptyMap()): JsonElement {
        val document = payload["query"]!!.jsonPrimitive.content
        val operation = payload["operationName"]?.jsonPrimitive?.content
        val variables = payload["variables"]?.jsonObject ?: emptyMap()
        return execute(document, operation, variables, contexts)
    }

    /**
     * Executes a GraphQL operation.
     *
     * @param document The GraphQL document.
     * @param operation The name of the operation from the document to execute.
     * @param data The variables to pass into the operation.
     * @return The JSON output. On success, this will have a `data` key. On failure, this will have an `errors` key.
     */
    suspend fun execute(document: String, operation: String? = null, data: Map<String, Any?> = emptyMap(), contexts: Map<KType, Any?> = emptyMap()): JsonElement {
        val lexer = GraphQLLexer(document)
        val prog = lexer.parseExecutableDocument()
        val fragments = prog.definitions.filterIsInstance<FragmentDefinition>()
        val ops = prog.definitions.filterIsInstance<OperationDefinition>()
        val selectedOp = ops.find { it.name == operation } ?: throw IllegalArgumentException("Unable to execute op: $operation")

        try {
            val o = when (selectedOp.type) {
                OperationType.QUERY -> execute(
                    Query.make(schema, selectedOp, data, fragments, contexts),
                    schema.queries
                )

                OperationType.MUTATION -> execute(
                    Mutation.make(schema, selectedOp, data, fragments, contexts),
                    schema.mutations
                )

                else -> TODO()
            }

            return buildJsonObject {
                if (operation == null) {
                    put("data", o)
                } else {
                    put("data", buildJsonObject {
                        put(operation, o)
                    })
                }
            }
        } catch (e: GraphQLException) {
            return buildJsonObject {
                val errors = buildJsonArray {
                    for (err in e.errors) {
                        add(buildJsonObject {
                            put("message", err.message)
                            if (err.path.isNotEmpty()) {
                                val path = buildJsonArray {
                                    for (p in err.path) {
                                        if (p is String) {
                                            add(p)
                                        } else if (p is Int) {
                                            add(p)
                                        }
                                    }
                                }
                                put("path", path)
                            }
                            if (err.loc != null) {
                                val locs = buildJsonArray {
                                    add(buildJsonObject {
                                        put("line", err.loc.line)
                                        put("column", err.loc.col)
                                    })
                                }
                                put("locations", locs)
                            }
                        })
                    }
                }
                put("errors", errors)
            }
        }
    }

    private suspend fun execute(operation: SchemaOperation, defMap: Map<String, Schema.OperationDefinition<*>>): JsonElement {
        return buildJsonObject {
            val errors = mutableListOf<GraphQLException.GraphQLError>()
            coroutineScope {
                for (field in operation.fields) {
                    launch {
                        wrapper {
                            try {
                                put(field.alias ?: field.name, execute(operation, defMap[field.name] ?: throw GraphQLException("Cannoty query field \"${field.name}\" on type \"${operation.type}\".", field.loc), field))
                            } catch (e: GraphQLException) {
                                errors += e.errors.map { it.withParent(field.alias ?: field.name) }
                            }
                        }
                    }
                }
            }
            if (errors.isNotEmpty()) {
                throw GraphQLException(errors)
            }
        }
    }

    private fun parseVariables(ctx: SchemaRequestContext, field: Field, args: Map<String, KType>) {
        for ((argName, argType) in args) {
            if (ctx.vars[argName] == null && !argType.isMarkedNullable) {
                throw GraphQLException("Field \"${field.name}\" argument \"$argName\" of type \"${argType.gqlName}\" is required but not provided.", field.loc)
            }
            if (ctx.vars[argName] != null) {
                val argVar = ctx.vars[argName]!!
                val argValue = argVar.on(ctx, argType)
                if (argValue == null && !argType.isMarkedNullable) {
                    throw GraphQLException("Expected type ${argType.gqlName}, found null.", argVar.loc)
                }
                if (argValue != null) {
                    if (!(argType.classifier!! as KClass<*>).isInstance(argValue)) {
                        throw GraphQLException("Expected type ${argType.gqlName}, found $argValue", argVar.loc)
                    }
                }
                ctx.variables[argName] = argValue
            }
        }
    }

    private suspend fun execute(operation: SchemaOperation, def: Schema.OperationDefinition<*>, field: Field): JsonElement {
        val ctx = operation.context.withArguments(field.arguments)

        if (!def.rule(operation.context)) {
            throw IllegalStateException("Unable to access field!")
        }

        parseVariables(ctx, field, def.arguments)
        val res = def.resolver.invoke(ctx)
        val type = schema.interfaceMap[def.ret.withNullability(false)]?.invoke(res) ?: def.ret

        return mapJson(operation, res, type, operation.context.expand(type, field.selectionSet))
    }

    private suspend fun mapJson(operation: SchemaOperation, obj: Any?, type: KType, fields: List<Field>): JsonElement {
        val def = schema.typeMap[type.withNullability(false)]
        val enum = schema.enumMap[type.withNullability(false)]

        // def is only null if type is a primitive, string, list or array
        if (type.isMarkedNullable && obj == null) {
            return JsonNull
        }

        if (enum != null) {
            return JsonPrimitive((obj as Enum<*>).name)
        }

        return when (type.classifier!!) {
            Int::class -> JsonPrimitive(obj as Int)
            Long::class -> JsonPrimitive(obj as Long)
            Float::class -> JsonPrimitive(obj as Float)
            Double::class -> JsonPrimitive(obj as Double)
            String::class -> JsonPrimitive(obj as String)
            Boolean::class -> JsonPrimitive(obj as Boolean)
            else -> {
                val kClass = type.classifier as KClass<*>
                when (kClass) {
                    List::class -> buildJsonArray {
                        val argType = type.arguments.first().type!!
                        val errors = mutableListOf<GraphQLException.GraphQLError>()
                        for ((i, item) in (obj as List<*>).withIndex()) {
                            try {
                                add(mapJson(operation, item, argType, fields))
                            } catch (e: GraphQLException) {
                                errors += e.errors.map { it.withParent(i) }
                            }
                        }
                        if (errors.isNotEmpty()) {
                            throw GraphQLException(errors)
                        }
                    }
                    else -> buildJsonObject {
                        val errors = mutableListOf<GraphQLException.GraphQLError>()
                        coroutineScope {
                            for (field in fields) {
                                launch {
                                    wrapper {
                                        try {
                                            val prop = def!!.properties[field.name] ?: throw GraphQLException("Unknown field: ${field.name}", field.loc)
                                            val ctx = operation.context.withArguments(field.arguments)
                                            parseVariables(ctx, field, prop.arguments)
                                            val res = (prop.resolver as suspend Any?.(SchemaRequestContext) -> Any?)(obj, ctx)
                                            val retType = schema.interfaceMap[prop.ret.withNullability(false)]?.invoke(res) ?: prop.ret
                                            put(
                                                field.alias ?: field.name,
                                                mapJson(operation, res, retType, ctx.expand(retType, field.selectionSet))
                                            )
                                        } catch (e: GraphQLException) {
                                            errors += e.errors.map { it.withParent(field.alias ?: field.name) }
                                        }
                                    }
                                }
                            }
                        }
                        if (errors.isNotEmpty()) {
                            throw GraphQLException(errors)
                        }
                    }
                }
            }
        }
    }
}
