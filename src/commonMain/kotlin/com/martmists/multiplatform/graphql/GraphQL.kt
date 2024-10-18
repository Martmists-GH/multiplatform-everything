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
class GraphQL {
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
    suspend fun execute(payload: JsonObject): JsonElement {
        val document = payload["query"]!!.jsonPrimitive.content
        val operation = payload["operationName"]?.jsonPrimitive?.content
        val variables = payload["variables"]?.jsonObject ?: emptyMap()
        return execute(document, operation, variables)
    }

    /**
     * Executes a GraphQL operation.
     *
     * @param document The GraphQL document.
     * @param operation The name of the operation from the document to execute.
     * @param data The variables to pass into the operation.
     * @return The JSON output. On success, this will have a `data` key. On failure, this will have an `errors` key.
     */
    suspend fun execute(document: String, operation: String?, data: Map<String, Any?>): JsonElement {
        val lexer = GraphQLLexer(document)
        val prog = lexer.parseExecutableDocument()
        val fragments = prog.definitions.filterIsInstance<FragmentDefinition>()
        val ops = prog.definitions.filterIsInstance<OperationDefinition>()
        val selectedOp = ops.find { it.name == operation } ?: throw IllegalArgumentException("Unable to execute op: $operation")

        try {
            val o = when (selectedOp.type) {
                OperationType.QUERY -> execute(
                    Query.make(schema, selectedOp, data, fragments),
                    schema.queries
                )

                OperationType.MUTATION -> execute(
                    Mutation.make(schema, selectedOp, data, fragments),
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
                                put(field.name, execute(operation, defMap[field.name] ?: throw GraphQLException("Cannoty query field \"${field.name}\" on type \"${operation.type}\".", field.loc), field))
                            } catch (e: GraphQLException) {
                                errors += e.errors
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

    private suspend fun execute(operation: SchemaOperation, def: Schema.OperationDefinition<*>, field: Field): JsonElement {
        val ctx = operation.context.withArguments(field.arguments)

        if (!def.rule(operation.context)) {
            throw IllegalStateException("Unable to access field!")
        }

        for ((argName, argType) in def.arguments) {
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
        val res = def.resolver.invoke(ctx)
        val type = def.ret
        val typeDef = schema.typeMap[type]
        val enumDef = schema.enumMap[type]

        return mapJson(operation, res, type, typeDef, enumDef, operation.context.expand(typeDef?.name ?: "<ERR: INTERNAL>", field.selectionSet))
    }

    private suspend fun mapJson(operation: SchemaOperation, obj: Any?, type: KType, def: Schema.TypeDefinition<*>?, enum: Schema.EnumDefinition<*>?, fields: List<Field>): JsonElement {
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
                when (kClass.qualifiedName) {
                    Array::class.qualifiedName -> buildJsonArray {
                        val argType = type.arguments.first().type!!
                        val argDef = schema.typeMap[argType]
                        val enumDef = schema.enumMap[argType]
                        for (item in obj as Array<*>) {
                            add(mapJson(operation, item, argType, argDef, enumDef, emptyList()))
                        }
                    }
                    List::class.qualifiedName -> buildJsonArray {
                        val argType = type.arguments.first().type!!
                        val argDef = schema.typeMap[argType]
                        val enumDef = schema.enumMap[argType]
                        for (item in obj as List<*>) {
                            add(mapJson(operation, item, argType, argDef, enumDef, emptyList()))
                        }
                    }
                    else -> buildJsonObject {
                        val errors = mutableListOf<GraphQLException.GraphQLError>()
                        coroutineScope {
                            for (field in fields) {
                                launch {
                                    wrapper {
                                        try {
                                            val prop = def!!.properties[field.name]!!
                                            val ctx = operation.context.withArguments(field.arguments)
                                            for ((argName, argType) in prop.arguments) {
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
                                            val res = (prop.resolver as suspend Any?.(SchemaRequestContext) -> Any?)(obj, ctx)
                                            val retDef = schema.typeMap[prop.ret]
                                            val enumDef = schema.enumMap[prop.ret]
                                            put(
                                                field.alias ?: field.name,
                                                mapJson(operation, res, prop.ret, retDef, enumDef, emptyList())
                                            )
                                        } catch (e: GraphQLException) {
                                            errors += e.errors
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
