package com.martmists.multiplatform.graphql.ktor

import com.martmists.multiplatform.graphql.GraphQL
import com.martmists.multiplatform.graphql.GraphQLDSL
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class GraphQLFeature(val configuration: Configuration) {
    class Configuration : GraphQL() {
        internal val contexts = mutableMapOf<KType, (ApplicationCall) -> Any?>()

        /**
         * The endpoint graphql will listen on for queries and mutations.
         */
        var endpoint: String = "/graphql"

        /**
         * The endpoint graphql will listen on for subscriptions.
         */
        var subscriptionEndpoint: String = "/subscription"

        /**
         * The HTML to display on the graphql endpoint.
         */
        var playgroundHtml: String? = null

        inline fun <reified T> context(noinline block: @GraphQLDSL ApplicationCall.() -> T?) = context(typeOf<T>(), block)
        fun <T> context(type: KType, block: @GraphQLDSL ApplicationCall.() -> T?) {
            contexts[type] = block
        }
    }

    companion object Feature : Plugin<Application, Configuration, GraphQLFeature> {
        override val key = AttributeKey<GraphQLFeature>("Multiplatform-Everything/GraphQL")

        override fun install(pipeline: Application, configure: Configuration.() -> Unit): GraphQLFeature {
            val config = Configuration()
            config.configure()

            val routing: Routing.() -> Unit = {
                route(config.endpoint) {
                    config.playgroundHtml?.let {
                        get {
                            call.respondText(it, ContentType.Text.Html)
                        }
                    }

                    post {
                        val payload = call.receive<JsonObject>()
                        val contexts = config.contexts.mapValues { (_, v) -> v(call) }.toMutableMap()
                        contexts[typeOf<ApplicationCall>()] = call

                        val result = config.execute(payload, contexts)

                        call.respondText(result.first().toString(), ContentType.Application.Json)
                    }
                }

                if (config.schema.subscriptions.isNotEmpty()) {
                    require(config.endpoint != config.subscriptionEndpoint)

                    route(config.subscriptionEndpoint) {
                        webSocket {
                            val contexts = config.contexts.mapValues { (_, v) -> v(call) }.toMutableMap()
                            contexts[typeOf<ApplicationCall>()] = call

                            val frame = incoming.receive()
                            if (frame is Frame.Text) {
                                val json = Json.decodeFromString<JsonObject>(frame.readText())
                                val result = config.execute(json, contexts)
                                result.collect { item ->
                                    outgoing.send(Frame.Text(item.toString()))
                                }
                            }
                        }
                    }
                }
            }

            pipeline.pluginOrNull(RoutingRoot)?.apply(routing) ?: pipeline.install(RoutingRoot, routing)

            return GraphQLFeature(config)
        }
    }
}
