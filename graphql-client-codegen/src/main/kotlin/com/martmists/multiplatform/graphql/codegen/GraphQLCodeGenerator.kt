package com.martmists.multiplatform.graphql.codegen

import org.intellij.lang.annotations.Language
import java.io.File

class GraphQLCodeGenerator(private val outputDir: File, private val packageName: String, private val registry: GraphQLTypeRegistry) {
    private val files = mutableMapOf<String, File>()

    private fun emit(file: String, @Language("kotlin") code: String) = files.getOrPut(file) { outputDir.resolve(file) }.writeText(code.trim() + "\n")

    fun emitCode() {
        outputDir.mkdirs()

        emitCommon()

        for (type in registry.types.values) {
            if (type.name !in arrayOf("Query", "Mutation", "Subscription")) {
                emitType(type)
            }
        }

        emitQuery()
        if ("Mutation" in registry.types) {
            emitMutation()
        }
        if ("Subscription" in registry.types) {
            emitSubscription()
        }
    }

    private fun emitQuery() {
        val queryType = registry.types["Query"]!!

        emit("QueryDSL.kt", """
package $packageName

import kotlinx.serialization.json.*

class QueryDSL {
${queryType.properties.entries.joinToString("\n") { (k, v) -> emitQueryDSL(k, v, "query", "Query").trim().prependIndent("    ") }}
${queryType.methods.entries.joinToString("\n") { (k, v) -> emitQueryDSL(k, v, "query", "Query").trim().prependIndent("    ") }}
}""")
    }

    private fun emitMutation() {
        val mutationType = registry.types["Mutation"]!!

        emit("MutationDSL.kt", """
package $packageName

import kotlinx.serialization.json.*

class MutationDSL {
${mutationType.properties.entries.joinToString("\n") { (k, v) -> emitQueryDSL(k, v, "mutation", "Query").trim().prependIndent("    ") }}
${mutationType.methods.entries.joinToString("\n") { (k, v) -> emitQueryDSL(k, v, "mutation", "Query").trim().prependIndent("    ") }}
}""")
    }

    private fun emitSubscription() {
        val subscriptionType = registry.types["Subscription"]!!

        emit("SubscriptionDSL.kt", """
package $packageName

import kotlinx.serialization.json.*

class SubscriptionDSL {
${subscriptionType.properties.entries.joinToString("\n") { (k, v) -> emitQueryDSL(k, v, "subscription", "Subscription").trim().prependIndent("    ") }}
${subscriptionType.methods.entries.joinToString("\n") { (k, v) -> emitQueryDSL(k, v, "subscription", "Subscription").trim().prependIndent("    ") }}
}""")
    }

    private fun emitQueryDSL(name: String, type: GQLTypeRef, queryKind: String, objectKind: String): String {
        val inType = registry.resolveInner(registry.resolve(type))
        return when (inType.kind) {
            TypeKind.SCALAR, TypeKind.ENUM -> {
                """
val $name: $objectKind<${typeString(type)}>
    get() {
        val builder = QueryBuilder()
        builder.attr("$name")
        val (document, variables) = builder.build()
        return $objectKind("$queryKind", document, variables, { ${emitGetter("it?.get(\"$name\")", type)} })
    }"""
            }
            else -> {
                """
fun $name(block: @GraphQLDSL ${inType.name}DSL.() -> Unit): $objectKind<${typeString(type)}> {
    val builder = QueryBuilder()
    builder.scope("$name") {
        ${inType.name}DSL(this).block()
    }
    val (document, variables) = builder.build()
    return $objectKind("$queryKind", document, variables, { ${emitGetter("it?.get(\"$name\")", type)} })
}"""
            }
        }
    }

    private fun emitQueryDSL(name: String, method: GQLMethod, queryKind: String, objectKind: String): String {
        val type = method.returnType
        val inType = registry.resolveInner(registry.resolve(type))
        return when (inType.kind) {
            TypeKind.SCALAR, TypeKind.ENUM -> {
                """
fun $name(${method.arguments.entries.joinToString(", ") { (k, v) -> "$k: ${argTypeString(v)}" }}): $objectKind<${typeString(type)}> {
    val builder = QueryBuilder()
    builder.attr("$name", listOf(${method.arguments.keys.joinToString(", ") { "\"$it\"" }}))
    builder.variables.putAll(mapOf(${method.arguments.entries.joinToString(", ") { (k, v) -> "\"$k\" to Variable.make(\"${gqlTypeString(v)}\", $k)" }}))
    val (document, variables) = builder.build()
    return $objectKind("$queryKind", document, variables, { ${emitGetter("it?.get(\"$name\")", type)} })
}"""
            }
            else -> {
                """
fun $name(${method.arguments.entries.joinToString(", ") { (k, v) -> "$k: ${argTypeString(v)}" }}, block: @GraphQLDSL ${inType.name}DSL.() -> Unit): $objectKind<${typeString(type)}> {
    val builder = QueryBuilder()
    builder.scope("$name") {
        ${inType.name}DSL(this).block()
    }
    builder.variables.putAll(mapOf(${method.arguments.entries.joinToString(", ") { (k, v) -> "\"$k\" to Variable.make(\"${gqlTypeString(v)}\", $k)" }}))
    val (document, variables) = builder.build()
    return $objectKind("$queryKind", document, variables, { ${emitGetter("it?.get(\"$name\")", type)} })
}"""
            }
        }
    }

    private fun emitObject(type: GQLType) {
        emit("${type.name}.kt", """
@file:Suppress("UNNECESSARY_NOT_NULL_ASSERTION", "UNNECESSARY_SAFE_CALL")

package $packageName

import kotlinx.serialization.json.*
import kotlinx.serialization.*

class ${type.name}(private val json: JsonObject)${if (type.interfaces.isEmpty()) "" else ": ${type.interfaces.joinToString(", ") { it.name }}"} {
${type.properties.entries.joinToString("\n") { (k, v) -> emitProperty(k, v, type.interfaces.any { registry.resolve(it).properties.containsKey(k) }).prependIndent("    ") }}
${type.methods.entries.joinToString("\n") { (k, v) -> emitProperty(k, v.returnType, type.interfaces.any { registry.resolve(it).methods.containsKey(k) }).prependIndent("    ") }}
${if (type.interfaces.isEmpty()) "" else """
    companion object {
        operator fun invoke(block: ${type.name}DSL.() -> Unit): ${type.name}DSL {
            return ${type.name}DSL(QueryBuilder()).also { it.block() }
        }
    }
"""}
}

class ${type.name}DSL(private val builder: QueryBuilder)${if (type.interfaces.isEmpty()) "" else ": ${type.interfaces.joinToString(", ") { "${it.name}FragmentBase" }}"} {
${type.properties.entries.joinToString("\n") { (k, v) -> emitPropertyDSL(k, v).prependIndent("    ") }}
${type.methods.entries.joinToString("\n") { (k, v) -> emitPropertyDSL(k, v).prependIndent("    ") }}
${if (type.interfaces.isEmpty()) "" else """
    @Deprecated("Reserved for internal use", level = DeprecationLevel.ERROR)
    @Suppress("FunctionName")
    override fun `__internal-builder`(): QueryBuilder = builder
"""}
}

@Serializable
class ${type.name}Model(${(type.properties + type.methods.mapValues { (k, v) -> v.returnType }).entries.joinToString(", ") { (k, v) -> "val $k: ${argTypeString(v)}${if (v.nullable) " = null" else ""}" }})""")
    }

    private fun emitEnum(type: GQLType) {
        emit("${type.name}.kt", """
package $packageName

enum class ${type.name} {
    ${type.enumValues.joinToString(",\n    ")}
}""")
    }

    private fun emitInterface(type: GQLType) {
        emit("${type.name}.kt", """
package $packageName

import kotlinx.serialization.*

sealed interface ${type.name} {
    ${type.properties.entries.joinToString("\n    ") { (k, v) -> "val $k: ${typeString(v)}" }}
    ${type.methods.entries.joinToString("\n    ") { (k, v) -> "val $k: ${typeString(v.returnType)}" }}
}

sealed interface ${type.name}FragmentBase {
    @Deprecated("Reserved for internal use", level = DeprecationLevel.ERROR)
    @Suppress("FunctionName")
    fun `__internal-builder`(): QueryBuilder
}

class ${type.name}DSL(private val builder: QueryBuilder) {
    init {
        builder.attr("__typename")
    }

${type.properties.entries.joinToString("\n    ") { (k, v) -> emitPropertyDSL(k, v) }}
${type.methods.entries.joinToString("\n    ") { (k, v) -> emitPropertyDSL(k, v) }}
    val fragment: Fragment
        get() = Fragment()
    
    infix fun <T: ${type.name}FragmentBase> Fragment.on(query: T) {
        @Suppress("DEPRECATION_ERROR")
        val nestedBuilder = query.`__internal-builder`()
        builder.fragment(query::class.simpleName!!, nestedBuilder)
    }
}

@Serializable
sealed interface ${type.name}Model
""")
    }

    private fun emitProperty(name: String, type: GQLTypeRef, isOverride: Boolean): String {
        return """
${if (isOverride) "override " else ""}val $name: ${typeString(type)}
    get() = ${emitGetter("json[\"$name\"]", type)}
        """.trim()
    }

    private fun emitGetter(name: String, type: GQLTypeRef): String {
        return when {
            type.nullable -> "$name.orNull?.let { ${emitGetter("it", type.ofType!!)} }"
            type.isList -> "$name!!.jsonArray.map { ${emitGetter("it", type.ofType!!)} }"
            else -> {
                val resolved = registry.resolve(type)
                when (resolved.kind) {
                    TypeKind.SCALAR -> {
                        val start = "$name!!.jsonPrimitive"
                        when (val it = resolved.name) {
                            "ID", "String" -> "$start.content"
                            "Int" -> "$start.int"
                            "Long" -> "$start.long"
                            "Float" -> "$start.float"
                            "Double" -> "$start.double"
                            "Boolean" -> "$start.boolean"
                            else -> "$packageName.parse$it($start)"
                        }
                    }
                    TypeKind.ENUM -> {
                        "${resolved.name}.valueOf($name!!.jsonPrimitive.content)"
                    }
                    TypeKind.INTERFACE -> {
                        """
    when ($name!!.jsonObject["__typename"]!!.jsonPrimitive.content) {
        ${registry.implementors(type.name).joinToString("\n        ") { "\"${it.name}\" -> ${it.name}($name!!.jsonObject)" }}
        else -> throw IllegalStateException("Unknown typename ${'$'}{$name!!.jsonObject["__typename"]}")
    }"""
                    }
                    else -> "${resolved.name}($name!!.jsonObject)"
                }
            }
        }
    }

    private fun emitPropertyDSL(name: String, type: GQLTypeRef): String {
        val dslType = registry.resolveInner(registry.resolve(type))

        return when (dslType.kind) {
            TypeKind.SCALAR, TypeKind.ENUM -> {
                """
val $name: Unit
    get() = builder.attr("$name")"""
            }
            else -> {
                """
fun $name(block: @GraphQLDSL ${dslType.name}DSL.() -> Unit) {
    builder.scope("$name") {
        ${dslType.name}DSL(this).block()
    }
}"""
            }
        }
    }

    private fun emitPropertyDSL(name: String, method: GQLMethod): String {
        val type = method.returnType
        val dslType = registry.resolveInner(registry.resolve(type))

        return when (dslType.kind) {
            TypeKind.SCALAR, TypeKind.ENUM -> {
                """
fun $name(${method.arguments.entries.joinToString(", ") { (k, v) -> "$k: ${argTypeString(v)}" }}): Unit {
    builder.attr("$name", listOf(${method.arguments.keys.joinToString(", ") { "\"$it\"" }}))
    builder.variables.putAll(mapOf(${method.arguments.entries.joinToString(", ") { (k, v) -> "\"$k\" to Variable.make(\"${gqlTypeString(v)}\", $k)" }}))
}"""
            }
            else -> {
                """
fun $name(${method.arguments.entries.joinToString(", ") { (k, v) -> "$k: ${argTypeString(v)}" }}, block: @GraphQLDSL ${dslType.name}DSL.() -> Unit) {
    builder.scope("$name", listOf(${method.arguments.keys.joinToString(", ") { "\"$it\"" }})) {
        ${dslType.name}DSL(this).block()
    }
    builder.variables.putAll(mapOf(${method.arguments.entries.joinToString(", ") { (k, v) -> "\"$k\" to Variable.make(\"${gqlTypeString(v)}\", $k)" }}))
}"""
            }
        }
    }

    private fun typeString(type: GQLTypeRef): String {
        return when {
            type.nullable -> "${typeString(type.ofType!!)}?"
            type.isList -> "List<${typeString(type.ofType!!)}>"
            else -> type.name
        }
    }

    private fun gqlTypeString(type: GQLTypeRef): String {
        val base = when {
            type.isList -> "List<${gqlTypeString(type.ofType!!)}>"
            type.nullable -> gqlTypeString(type.ofType!!).removeSuffix("!")
            else -> type.name
        }
        return if (type.nullable) base else "$base!"
    }

    private fun argTypeString(type: GQLTypeRef): String {
        return when {
            type.nullable -> "${argTypeString(type.ofType!!)}?"
            type.isList -> "List<${argTypeString(type.ofType!!)}>"
            else -> when (registry.resolve(type).kind) {
                TypeKind.OBJECT, TypeKind.INTERFACE -> "${type.name}Model"
                else -> type.name
            }
        }
    }

    private fun emitCommon() {
        emit("core.kt", """
package $packageName

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
${if ("Subscription" !in registry.types) "" else """
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
"""}
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlin.uuid.*
            
typealias ID = String

@Target(AnnotationTarget.TYPE)
@DslMarker
annotation class GraphQLDSL

class Variable<T>(val type: String, val value: T, val serializer: SerializationStrategy<T>) {
    companion object {
        inline fun <reified T> make(type: String, value: T): Variable<T> {
            return Variable(type, value, serializer())
        }
    }
}

typealias Variables = Map<String, Variable<*>>

class Fragment

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
    
    fun scope(name: String, block: @GraphQLDSL QueryBuilder.() -> Unit) {
        val builder = QueryBuilder()
        builder.block()
        require(builder.scopes.isNotEmpty() || builder.props.isNotEmpty()) { "Must specify at least one field" }
        val (document, variables) = builder.build()
        scopes += "${'$'}name \n" + document.prependIndent("  ") + "\n"
        this.variables += variables
    }
    
    fun scope(name: String, mapping: Map<String, String>, block: @GraphQLDSL QueryBuilder.() -> Unit) {
        val builder = QueryBuilder()
        builder.block()
        require(builder.scopes.isNotEmpty() || builder.props.isNotEmpty()) { "Must specify at least one field" }
        val (document, variables) = builder.build()
        scopes += "${'$'}name(${'$'}{mapping.entries.joinToString(", ") { "${'$'}{it.key}: ${'$'}${'$'}{it.key}" }}) \n" + document.prependIndent("  ") + "\n"
        this.variables += variables
    }

    fun fragment(dslType: String, nested: QueryBuilder) {
        val realType = dslType.substring(0, dslType.length - 3)
        val (document, variables) = nested.build()
        scopes += "... on ${'$'}realType ${'$'}document".prependIndent("    ") + "\n"
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

${if ("Subscription" !in registry.types) "" else """
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
"""}

class GraphQLClient(private val client: HttpClient, private val url: String, private val subscriptionUrl: String, private val configure: JsonBuilder.() -> Unit = {}) {
    private val json = Json {
        explicitNulls = false
        configure()
    }

    suspend inline fun <reified T> query(block: @GraphQLDSL QueryDSL.() -> Query<T>): T {
        val query = QueryDSL().block()
        return execute(query)
    }
    
${if ("Mutation" !in registry.types) "" else """
    fun <T> mutation(block: @GraphQLDSL MutationDSL.() -> Query<T>): T {
        val query = MutationDSL().block()
        return execute(query)
    }
"""}
    
${if ("Subscription" !in registry.types) "" else """
    suspend fun <T> subscription(scope: CoroutineScope? = null, block: @GraphQLDSL SubscriptionDSL.() -> Subscription<T>): Pair<Flow<T>, () -> Unit> {
        val query = SubscriptionDSL().block()
        return execute(scope, query)
    }
    
    @Suppress("UNCHECKED_CAST")
    @OptIn(ExperimentalUuidApi::class, DelicateCoroutinesApi::class)
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
    }
"""}
    
    @Suppress("UNCHECKED_CAST")
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
    get() = if (this == null || this === JsonNull) null else this
        """
        )
    }

    private fun emitType(type: GQLType) {
        when (type.kind) {
            TypeKind.OBJECT -> emitObject(type)
            TypeKind.ENUM -> emitEnum(type)
            TypeKind.INTERFACE -> emitInterface(type)
            else -> {}
        }
    }
}
