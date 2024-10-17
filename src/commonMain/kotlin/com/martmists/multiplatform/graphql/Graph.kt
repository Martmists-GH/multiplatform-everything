package com.martmists.multiplatform.graphql

import com.martmists.multiplatform.graphql.parser.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.*
import kotlin.reflect.KClass
import kotlin.reflect.KType

class Graph {
    private var didInit = false
    private lateinit var schema: Schema

    fun schema(builder: SchemaBuilder.() -> Unit) {
        if (didInit) {
            throw IllegalStateException("Schema already built!")
        }
        val schemaBuilder = SchemaBuilder()
        schemaBuilder.builder()
        schema = schemaBuilder.build()
        didInit = true
    }

    suspend fun execute(payload: JsonObject): JsonElement {
        val document = payload["query"]!!.jsonPrimitive.content
        val operation = payload["operationName"]?.jsonPrimitive?.content
        val variables = payload["variables"]?.jsonObject ?: emptyMap()
        return execute(document, operation, variables)
    }

    suspend fun execute(document: String, operation: String?, data: Map<String, Any?>): JsonElement {
        val lexer = GraphQLLexer(document)
        val prog = lexer.parseExecutableDocument()
        val fragments = prog.definitions.filterIsInstance<FragmentDefinition>()
        val ops = prog.definitions.filterIsInstance<OperationDefinition>()
        val selectedOp = ops.find { it.name == operation } ?: throw IllegalArgumentException("Unable to execute op: $operation")

        val o = when (selectedOp.type) {
            OperationType.QUERY -> execute(Query.make(selectedOp, data, fragments))
            else -> TODO(selectedOp.type.toString())
        }

        val out = buildJsonObject {
            if (operation == null) {
                put("data", o)
            } else {
                put("data", buildJsonObject {
                    put(operation, o)
                })
            }
        }

        return out
    }

    private suspend fun execute(query: Query): JsonElement {
        suspend fun map(obj: Any?, type: KType, def: Schema.TypeDefinition<*>?, fields: List<Field>): JsonElement {
            // def is only null if type is a primitive, string, list or array
            if (type.isMarkedNullable && obj == null) {
                return JsonNull
            }

            return when (type.classifier!!) {
                Int::class -> JsonPrimitive(obj as Int)
                Long::class -> JsonPrimitive(obj as Long)
                Float::class -> JsonPrimitive(obj as Float)
                Double::class -> JsonPrimitive(obj as Double)
                String::class -> JsonPrimitive(obj as String)
                Boolean::class -> JsonPrimitive(obj as Boolean)
                Array::class -> buildJsonArray {
                    val argType = type.arguments.first().type!!
                    val argDef = schema.typeMap[argType]
                    for (item in obj as Array<*>) {
                        add(map(item, argType, argDef, emptyList()))
                    }
                }
                List::class -> buildJsonArray {
                    val argType = type.arguments.first().type!!
                    val argDef = schema.typeMap[argType]
                    for (item in obj as List<*>) {
                        add(map(item, argType, argDef, emptyList()))
                    }
                }
                else -> {
                    buildJsonObject {
                        coroutineScope {
                            for (field in fields) {
                                launch {
                                    val prop = def!!.properties[field.name]!!
                                    val ctx = query.context.withArguments(field.arguments)
                                    val res = (prop.getter as suspend Any?.(SchemaRequestContext) -> Any?)(obj, ctx)
                                    val retDef = schema.typeMap[prop.ret]
                                    put(field.alias ?: field.name, map(res, prop.ret, retDef, emptyList()))
                                }
                            }
                        }
                    }
                }
            }
        }

        suspend fun fetch(field: Field): JsonElement {
            val queryDef = schema.queries[field.name]!!
            val ctx = query.context.withArguments(field.arguments)

            if (!queryDef.rule(query.context)) {
                throw IllegalStateException("Unable to access field!")
            }

            val res = queryDef.getter.invoke(ctx)
            val type = queryDef.ret
            val typeDef = schema.typeMap[type]

            return map(res, type, typeDef, query.context.expand(typeDef?.name ?: "<primitive>", field.selectionSet))
        }

        return buildJsonObject {
            coroutineScope {
                for (field in query.fields) {
                    launch {
                        put(field.name, fetch(field))
                    }
                }
            }
        }
    }
}
