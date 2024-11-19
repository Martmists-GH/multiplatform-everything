package com.martmists.multiplatform.graphql.codegen

import java.io.File

class GraphQLCodeGenerator(outputDir: File, private val packageName: String) {
    private val outputDir = outputDir.resolve(packageName.replace('.', '/')).also {
        if (it.exists()) {
            it.deleteRecursively()
        }
        it.mkdirs()
    }

    init {
        emitQuery(emptyList(), emptyList())
        emitMutation(emptyList(), emptyList())
        emitSubscription(emptyList(), emptyList())
    }

    fun emitCore(omitSubscriptions: Boolean) {
        val file = outputDir.resolve("core.kt")

        val subscriptionImports = if (omitSubscriptions) "" else "import io.ktor.client.plugins.websocket.*\nimport io.ktor.websocket.*\nimport kotlinx.coroutines.*\nimport kotlinx.coroutines.flow.*"
        val subscriptionChunk = if (omitSubscriptions) "" else """suspend fun <T> subscription(scope: CoroutineScope? = null, block: @GraphDsl SubscriptionDSL.() -> Subscription<T>): Pair<Flow<T>, () -> Unit> {
        val query = SubscriptionDSL().block()
        return execute(scope, query)
    }
    
    @OptIn(ExperimentalUuidApi::class)
    suspend fun <T> execute(scope: CoroutineScope?, query: Subscription<T>): Pair<Flow<T>, () -> Unit> {
        val flow = MutableSharedFlow<T>()
        var mustClose = false
    
        (scope ?: GlobalScope).launch {
            client.webSocket(subscriptionUrl) {
                val id = Uuid.random().toHexString()
                outgoing.send(Frame.Text(Json.encodeToString(WebsocketTransportMessage(WebsocketTransportMessageType.CONNECTION_INIT))))
    
                while (!mustClose) {
                    val frame = incoming.receive()
                    if (frame is Frame.Text) {
                        val res = json.decodeFromString<WebsocketTransportMessage>(frame.readText())
                        when (res.type) {
                            WebsocketTransportMessageType.CONNECTION_ACK -> {
                                val payload = buildJsonObject {
                                    put("query", query.query())
                                    put("variables", buildJsonObject {
                                        query.variables.forEach { (name, variable) ->
                                            put(name, json.encodeToJsonElement(variable.serializer as SerializationStrategy<Any?>, variable.value))
                                        }
                                    })
                                }
                                outgoing.send(Frame.Text(Json.encodeToString(WebsocketTransportMessage(WebsocketTransportMessageType.SUBSCRIBE, id, payload))))
                            }
                            WebsocketTransportMessageType.NEXT -> {
                                flow.emit(query.constructor(res.payload!!.jsonObject))                                                    
                            }
                            WebsocketTransportMessageType.ERROR -> {
                                val errors = res.payload!!.jsonArray
                                throw GraphQLException(errors)
                            }
                            WebsocketTransportMessageType.COMPLETE -> {
                                mustClose = true
                            }
                            else -> {
                                // Do nothing
                            }
                        }
                    }
                }
            }
        }

        return flow to { mustClose = false }
    }"""

        file.writeText(
            """
@file:Suppress("UNCHECKED_CAST", "OPT_IN_USAGE")

package $packageName

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
$subscriptionImports
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlin.uuid.*

typealias ID = String

@Target(AnnotationTarget.TYPE)
@DslMarker
annotation class GraphDsl

@Serializable
data class WebsocketTransportMessage(
    val type: WebsocketTransportMessageType,
    val id: String? = null,
    val payload: JsonElement? = null,
)

enum class WebsocketTransportMessageType {
    @SerialName("connection_init")
    CONNECTION_INIT,
    @SerialName("connection_ack")
    CONNECTION_ACK,
    @SerialName("ping")
    PING,
    @SerialName("pong")
    PONG,
    @SerialName("subscribe")
    SUBSCRIBE,
    @SerialName("next")
    NEXT,
    @SerialName("error")
    ERROR,
    @SerialName("complete")
    COMPLETE,
}

class Variable<T>(val type: String, val value: T, val serializer: SerializationStrategy<T>) {
    companion object {
        inline fun <reified T> make(type: String, value: T): Variable<T> {
            return Variable(type, value, serializer())
        }
    }
}

typealias Variables = Map<String, Variable<*>>

class QueryBuilder {
    private val props = mutableListOf<String>()
    private val scopes = mutableListOf<String>()
    val variables = mutableMapOf<String, Variable<*>>()
    
    fun attr(name: String) {
        props += name
    }
    
    fun attr(name: String, variables: List<String>) {
        props += "${'$'}name(${'$'}{variables.joinToString(", ") { "${'$'}{it}: ${'$'}${'$'}{it}" }})"
    }
    
    fun scope(name: String, block: @GraphDsl QueryBuilder.() -> Unit) {
        val builder = QueryBuilder()
        builder.block()
        require(builder.scopes.isNotEmpty() || builder.props.isNotEmpty()) { "Must specify at least one field" }
        val (document, variables) = builder.build()
        scopes += "${'$'}name \n" + document.prependIndent("  ") + "\n"
        this.variables += variables
    }
    
    fun scope(name: String, mapping: Map<String, String>, block: @GraphDsl QueryBuilder.() -> Unit) {
        val builder = QueryBuilder()
        builder.block()
        require(builder.scopes.isNotEmpty() || builder.props.isNotEmpty()) { "Must specify at least one field" }
        val (document, variables) = builder.build()
        scopes += "${'$'}name(${'$'}{mapping.entries.joinToString(", ") { "${'$'}{it.key}: ${'$'}${'$'}{it.key}" }}) \n" + document.prependIndent("  ") + "\n"
        this.variables += variables
    }
    
    fun build(): Pair<String, Variables> {
        return "{\n" + props.joinToString("\n", "\n").prependIndent("  ") + scopes.joinToString("\n", "\n").prependIndent("  ") + "}" to variables
    }
}

class Query<T>(private val type: String, private val document: String, internal val variables: Variables, internal val constructor: (JsonObject?) -> T) {
    fun query(): String {
        if (variables.isEmpty())
            return "${'$'}type \n${'$'}{document.prependIndent("  ")}\n"
        return "${'$'}{type} (${'$'}{variables.entries.joinToString(", ") { "${'$'}${'$'}{it.key}: ${'$'}{it.value.type}" }}) \n${'$'}{document.prependIndent("  ")}\n"
    }
}

class Subscription<T>(private val type: String, private val document: String, internal val variables: Variables, internal val constructor: (JsonObject?) -> T) {
    fun query(): String {
        if (variables.isEmpty())
            return "${'$'}type \n${'$'}{document.prependIndent("  ")}\n"
        return "${'$'}{type} (${'$'}{variables.entries.joinToString(", ") { "${'$'}${'$'}{it.key}: ${'$'}{it.value.type}" }}) \n${'$'}{document.prependIndent("  ")}\n"
    }
}

class GraphQLException(val errors: JsonArray) : Exception()

class GraphQLClient(private val client: HttpClient, private val url: String, private val subscriptionUrl: String, private val configure: JsonBuilder.() -> Unit = {}) {
    private val json = Json {
        explicitNulls = false
        configure()
    }

    suspend inline fun <reified T> query(block: @GraphDsl QueryDSL.() -> Query<T>): T {
        val query = QueryDSL().block()
        return execute(query)
    }
    
    suspend fun <T> mutation(block: @GraphDsl MutationDSL.() -> Query<T>): T {
        val query = MutationDSL().block()
        return execute(query)
    }
    
    $subscriptionChunk
    
    suspend fun <T> execute(query: Query<T>): T {
        val response = client.post(url) {
            header("Accept", "application/json")
            header("Content-Type", "application/json")
            setBody(buildJsonObject {
                put("query", query.query())
                put("variables", buildJsonObject {
                    query.variables.forEach { (name, variable) ->
                        put(name, json.encodeToJsonElement(variable.serializer as SerializationStrategy<Any?>, variable.value))
                    }
                })
            })
        }
        
        if (response.status != HttpStatusCode.OK) {
            throw IllegalStateException("GraphQL request failed with status ${'$'}{response.status}")
        }

        val res = response.body<JsonObject>()
        
        val data = res["data"] as JsonObject?
        val errors = res["errors"] as JsonArray?
        if (errors != null) {
            throw GraphQLException(errors)
        }

        return query.constructor(data)
    }
}

val JsonElement?.orNull: JsonElement?
    get() = if (this == null || this is JsonNull) null else this
            """.trim()
        )
    }

    fun emitType(name: String, parent: String?, properties: List<PropertyDef>, methods: List<MethodDef>) {
        val file = outputDir.resolve("$name.kt")
        file.writeText(
            """
@file:Suppress("UNNECESSARY_NOT_NULL_ASSERTION", "UNNECESSARY_SAFE_CALL")
package $packageName

import kotlinx.serialization.*
import kotlinx.serialization.json.*

${generateType(name, parent, properties, methods)}
            """.trim()
        )
    }

    fun emitEnum(name: String, values: List<String>) {
        val file = outputDir.resolve("$name.kt")
        file.writeText(
            """
package $packageName

import kotlinx.serialization.*

@Serializable
enum class $name {
${values.joinToString(",\n").prependIndent("  ")}
}
            """.trim()
        )
    }

    fun emitQuery(properties: List<PropertyDef>, methods: List<MethodDef>) {
        val file = outputDir.resolve("QueryDSL.kt")
        file.writeText(
            """
@file:Suppress("UNNECESSARY_NOT_NULL_ASSERTION", "UNNECESSARY_SAFE_CALL")

package $packageName

import kotlinx.serialization.json.*
        
class QueryDSL {
${properties.joinToString("\n") { generatePropertyQuery("query", it) }.prependIndent("    ")}
${methods.joinToString("\n") { generateMethodQuery("query", it) }.prependIndent("    ")}
}
            """.trim()
        )
    }

    fun emitMutation(properties: List<PropertyDef>, methods: List<MethodDef>) {
        val file = outputDir.resolve("MutationDSL.kt")
        file.writeText(
            """
@file:Suppress("UNNECESSARY_NOT_NULL_ASSERTION", "UNNECESSARY_SAFE_CALL")

package $packageName

import kotlinx.serialization.json.*
        
class MutationDSL {
${properties.joinToString("\n") { generatePropertyQuery("mutation", it) }.prependIndent("    ")}
${methods.joinToString("\n") { generateMethodQuery("mutation", it) }.prependIndent("    ")}
}
            """.trim()
        )
    }

    fun emitSubscription(properties: List<PropertyDef>, methods: List<MethodDef>) {
        val file = outputDir.resolve("SubscriptionDSL.kt")
        file.writeText(
            """
@file:Suppress("UNNECESSARY_NOT_NULL_ASSERTION", "UNNECESSARY_SAFE_CALL")

package $packageName

import kotlinx.serialization.json.*
        
class SubscriptionDSL {
${properties.joinToString("\n") { generatePropertyQuery("subscription", it) }.prependIndent("    ")}
${methods.joinToString("\n") { generateMethodQuery("subscription", it) }.prependIndent("    ")}
}
            """.trim()
        )
    }

    fun generatePropertyQuery(type: String, prop: PropertyDef): String {
        val qType = if (type == "subscription") "Subscription" else "Query"

        if (prop.type.isScalar || prop.type.isEnum || (prop.type is ListType && (prop.type.elementType.isScalar || prop.type.elementType.isEnum))) {
            return """
val ${prop.name}: $qType<${remapType(prop.type)}>
    get() {
        val builder = QueryBuilder()
        builder.attr("${prop.name}")
        val (document, variables) = builder.build()
        return $qType("$type", document, variables, { it?.get("${prop.name}").let ${constructor(prop.type)} })
    }
            """.trim()
        } else {
            return """
fun ${prop.name}(block: @GraphDsl ${typeName(prop.type)}DSL.() -> Unit): $qType<${remapType(prop.type)}> {
    val builder = QueryBuilder()
    builder.scope("${prop.name}") {
        ${typeName(prop.type)}DSL(this).block()
    }
    val (document, variables) = builder.build()
    return $qType("$type", document, variables, { it?.get("${prop.name}").let ${constructor(prop.type)} })
}
        """.trim()
        }
    }

    fun generateMethodQuery(type: String, meth: MethodDef): String {
        val qType = if (type == "subscription") "Subscription" else "Query"

        val argsMapped =
            meth.args.joinToString(", ") { "${it.name}: ${argType(it.type)}" + if (it.type.isNullable) " = null" else "" }
        val argsList = meth.args.joinToString(
            ", ",
            "mapOf(",
            ")"
        ) { "\"${it.name}\" to Variable.make(\"${gqlType(it.type)}\", ${it.name})" }
        val argsVars = meth.args.joinToString(", ", "listOf(", ")") { "\"${it.name}\"" }
        val argsTypes = meth.args.joinToString(", ", "mapOf(", ")") { "\"${it.name}\" to \"${gqlType(it.type)}\"" }

        if (meth.returnType.isScalar) {
            return """
fun ${meth.name}($argsMapped): $qType<${remapType(meth.returnType)}> {
    val builder = QueryBuilder()
    builder.attr("${meth.name}", $argsVars)
    builder.variables.putAll($argsList)
    val (document, variables) = builder.build()
    return $qType("$type", document, variables, { it?.get("${meth.name}").let ${constructor(meth.returnType)} })
}
            """.trim()
        } else {
            return """
fun ${meth.name}($argsMapped, block: @GraphDsl ${typeName(meth.returnType)}DSL.() -> Unit): $qType<${remapType(meth.returnType)}> {
     val builder = QueryBuilder()
     builder.scope("${meth.name}", $argsTypes) {
         ${typeName(meth.returnType)}DSL(this).block()
     }
     builder.variables.putAll($argsList)
     val (document, variables) = builder.build()
     return $qType("$type", document, variables, { it?.get("${meth.name}").let ${constructor(meth.returnType)} })
}
            """.trim()
        }
    }

    fun generateType(name: String, parent: String?, properties: List<PropertyDef>, methods: List<MethodDef>): String {
        val props = properties.joinToString("\n", transform = ::generateProperty)
        val meths = methods.joinToString("\n", transform = ::generateMethod)

        val propsDSL = properties.joinToString("\n", transform = ::generatePropertyDSL)
        val methsDSL = methods.joinToString("\n", transform = ::generateMethodDSL)

        return """
class ${name}(private val json: JsonObject) {
${props.prependIndent("    ")}
${meths.prependIndent("    ")}
}

class ${name}DSL(private val builder: QueryBuilder) ${parent?.let { ": $it" } ?: ""} {
${propsDSL.prependIndent("    ")}
${methsDSL.prependIndent("    ")}
}

@Serializable
data class ${name}Model(
${
            properties.joinToString(",\n") { "val ${it.name}: ${argType(it.type)}" + if (it.type.isNullable) " = null" else "" }
                .prependIndent("    ")
        }
)
        """.trim()
    }

    fun generateProperty(prop: PropertyDef): String {
        return """
val ${prop.name}: ${remapType(prop.type)} by lazy {
${getElement(prop.name, prop.type).prependIndent("    ")}
}
        """.trim()
    }

    fun generatePropertyDSL(prop: PropertyDef): String {
        if (prop.type.isScalar || prop.type.isEnum || (prop.type is ListType && (prop.type.elementType.isScalar || prop.type.elementType.isEnum))) {
            return """
val ${prop.name}: Unit
    get() = builder.attr("${prop.name}")
            """.trim()
        }
        return """
fun ${prop.name}(block: @GraphDsl ${typeName(prop.type)}DSL.() -> Unit) {
    builder.scope("${prop.name}") {
        ${typeName(prop.type)}DSL(this).block()
    }
}
        """.trim()
    }

    fun generateMethod(meth: MethodDef): String {
        return """
val ${meth.name}: ${remapType(meth.returnType)} by lazy {
${getElement(meth.name, meth.returnType).prependIndent("    ")}
}
        """.trim()
    }

    fun generateMethodDSL(meth: MethodDef): String {
        val argsMapped =
            meth.args.joinToString(", ") { "${it.name}: ${argType(it.type)}" + if (it.type.isNullable) " = null" else "" }
        val argsList = meth.args.joinToString(
            ", ",
            "mapOf(",
            ")"
        ) { "\"${it.name}\" to Variable.make(\"${gqlType(it.type)}\", ${it.name})" }
        val argsTypes = meth.args.joinToString(", ", "mapOf(", ")") { "\"${it.name}\" to \"${gqlType(it.type)}\"" }
        val argsVars = meth.args.joinToString(", ", "listOf(", ")") { "\"${it.name}\"" }

        if (meth.returnType.isScalar || meth.returnType.isEnum || (meth.returnType is ListType && (meth.returnType.elementType.isScalar || meth.returnType.elementType.isEnum))) {
            return """
fun ${meth.name}(${argsMapped}): Unit {
    builder.attr("${meth.name}", $argsVars)
    builder.variables.putAll($argsList)
}
            """.trim()
        } else {
            return """
fun ${meth.name}(${argsMapped}, block: @GraphDsl ${typeName(meth.returnType)}DSL.() -> Unit) {
    builder.scope("${meth.name}", $argsTypes) {
        ${typeName(meth.returnType)}DSL(this).block()
    }
    builder.variables.putAll($argsList)
}
            """.trim()
        }
    }

    fun remapType(type: Type): String {
        return if (type is ListType) {
            "List<${remapType(type.elementType)}>"
        } else {
            type.name
        } + if (type.isNullable) "?" else ""
    }

    fun getElement(key: String, type: Type): String {
        val ne = if (type.isNullable) {
            ".orNull"
        } else {
            ""
        }
        val c = if (type.isNullable) {
            "?."
        } else {
            "!!."
        }

        val (prefix, on, suffix) = if (type is ListType) {
            val inner = if (type.elementType.isNullable) {
                "it${ne}."
            } else {
                "it?."
            }

            Triple("json[\"$key\"]${c}jsonArray${c}map { ", inner, " }")

        } else {
            if (type.isNullable) {
                Triple("", "json[\"$key\"].orNull${c}", "")
            } else {
                Triple("", "json[\"$key\"]${c}", "")
            }
        }

        return when (type.name) {
            "ID", "String" -> "${prefix}${on}jsonPrimitive${c}content${suffix}"
            "Int" -> "${prefix}${on}jsonPrimitive${c}int${suffix}"
            "Long" -> "${prefix}${on}jsonPrimitive${c}long${suffix}"
            "Float" -> "${prefix}${on}jsonPrimitive${c}float${suffix}"
            "Boolean" -> "${prefix}${on}jsonPrimitive${c}boolean${suffix}"
            else -> {
                if (type.isEnum) {
                    "${prefix}${on}jsonPrimitive${c}content${suffix}${c}let(${typeName(type)}::valueOf)${suffix}"
                } else {
                    "${prefix}${on}jsonObject${c}let(::${typeName(type)})${suffix}"
                }
            }
        }
    }

    fun typeName(type: Type): String {
        return if (type is ListType) {
            return typeName(type.elementType)
        } else {
            type.name
        }
    }

    fun gqlType(type: Type): String {
        return if (type is ListType) {
            "[${gqlType(type.elementType)}]"
        } else {
            type.name
        } + if (!type.isNullable) "!" else ""
    }

    fun argType(type: Type): String {
        return when {
            type.isModel() -> {
                "${type.name}Model"
            }

            type is ListType -> {
                "List<${argType(type.elementType)}>"
            }

            else -> {
                type.name
            }
        } + if (type.isNullable) "?" else ""
    }

    fun constructor(type: Type): String {
        if (type.isNullable) {
            val baseType = if (type is ListType) {
                ListType(type.elementType, false)
            } else {
                Type(type.name, type.isScalar, isNullable = false, isList = false, isEnum = type.isEnum)
            }
            return "{ it?.let(${constructor(baseType)}) }"
        }

        if (type is ListType) {
            return "{ it!!.jsonArray.map(${constructor(type.elementType)}) }"
        }

        if (type.isModel()) {
            return "{ ${type.name}(it!!.jsonObject) }"
        } else {
            return when (type.name) {
                "ID", "String" -> "{ it!!.jsonPrimitive.content }"
                "Int" -> "{ it!!.jsonPrimitive.int }"
                "Long" -> "{ it!!.jsonPrimitive.long }"
                "Float" -> "{ it!!.jsonPrimitive.float }"
                "Boolean" -> "{ it!!.jsonPrimitive.boolean }"
                else -> throw IllegalArgumentException("Unknown type ${type.name}")
            }
        }
    }
}
