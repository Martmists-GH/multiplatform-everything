package com.martmists.multiplatform.graphql.ktor

import com.martmists.multiplatform.graphql.GraphQL
import com.martmists.multiplatform.graphql.GraphQLDSL
import com.martmists.multiplatform.graphql.ktor.transport.WebsocketTransportMessage
import com.martmists.multiplatform.graphql.ktor.transport.WebsocketTransportMessageType
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.util.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.channels.ClosedSendChannelException
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlin.reflect.KType
import kotlin.reflect.typeOf
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.uuid.Uuid

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
         * The schema endpoint where the graphqls file will be served.
         * Set to null to disable.
         */
        var schemaEndpoint: String? = "/graphqls"

        /**
         * The timeout for websocket connections to send an initialization message.
         */
        var subscriptionTimeout: Duration = 1.seconds

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
                        call.respond(result.first())
                    }
                }

                if (config.schema.subscriptions.isNotEmpty()) {
                    require(config.endpoint != config.subscriptionEndpoint)

                    route(config.subscriptionEndpoint) {
                        webSocket {
                            runCatching {
                                var exit = false

                                val payload = withTimeoutOrNull(config.subscriptionTimeout) {
                                    while (true) {
                                        val frame = incoming.receive()
                                        if (frame is Frame.Text) {
                                            val json = Json.decodeFromString<WebsocketTransportMessage>(frame.readText())
                                            if (json.type != WebsocketTransportMessageType.CONNECTION_INIT) {
                                                close(CloseReason(4400, "No connection init message"))
                                                exit = true
                                            } else {
                                                return@withTimeoutOrNull json
                                            }
                                        }
                                    }
                                }

                                if (exit) return@webSocket
                                if (payload == null) {
                                    close(CloseReason(4408, "Connection initialisation timeout"))
                                    return@webSocket
                                }

                                outgoing.send(Frame.Text(Json.encodeToString(WebsocketTransportMessage(WebsocketTransportMessageType.CONNECTION_ACK))))

                                val contexts = config.contexts.mapValues { (_, v) -> v(call) }.toMutableMap()
                                contexts[typeOf<ApplicationCall>()] = call
                                val listeners = mutableMapOf<String, Job>()

                                coroutineScope {
                                    for (frame in incoming) {
                                        if (frame is Frame.Text) {
                                            val json = Json.decodeFromString<WebsocketTransportMessage>(frame.readText())
                                            when (json.type) {
                                                WebsocketTransportMessageType.PING -> {
                                                    outgoing.send(Frame.Text(Json.encodeToString(WebsocketTransportMessage(WebsocketTransportMessageType.PONG))))
                                                }

                                                WebsocketTransportMessageType.PONG -> {}
                                                WebsocketTransportMessageType.SUBSCRIBE -> {
                                                    if (json.id == null || json.payload == null) {
                                                        close(CloseReason(4400, "id or payload missing"))
                                                        break
                                                    }

                                                    if (json.id in listeners) {
                                                        close(CloseReason(4409, "Subscribe for ${json.id} already exists"))
                                                        break
                                                    }

                                                    val flow = config.execute(json.payload.jsonObject, contexts)
                                                    listeners[json.id] = launch {
                                                        flow.collect {
                                                            val obj = it.jsonObject
                                                            if ("errors" in obj) {
                                                                outgoing.send(Frame.Text(Json.encodeToString(WebsocketTransportMessage(WebsocketTransportMessageType.ERROR, json.id, obj["errors"]))))
                                                                listeners.remove(json.id)?.cancel()
                                                            } else {
                                                                outgoing.send(Frame.Text(Json.encodeToString(WebsocketTransportMessage(WebsocketTransportMessageType.NEXT, json.id, it))))
                                                            }
                                                        }
                                                    }
                                                }

                                                WebsocketTransportMessageType.COMPLETE -> {
                                                    if (json.id == null) {
                                                        close(CloseReason(4400, "id missing"))
                                                        return@coroutineScope
                                                    }

                                                    listeners.remove(json.id)?.cancel()
                                                }

                                                else -> {
                                                    close(CloseReason(4400, "Illegal message type"))
                                                    break
                                                }
                                            }
                                        }
                                    }

                                    for (key in listeners.keys.toList()) {
                                        listeners.remove(key)?.cancel()
                                    }
                                }
                            }.onFailure {
                                if (it !is ClosedReceiveChannelException && it !is ClosedSendChannelException) {
                                    // Exception during execution, rethrow
                                    throw it
                                }
                            }
                        }
                    }
                }

                config.schemaEndpoint?.let {
                    get(it) {
                        call.respondText(config.schema.graphqls(), ContentType.Text.Plain)
                    }
                }
            }

            pipeline.pluginOrNull(RoutingRoot)?.apply(routing) ?: pipeline.install(RoutingRoot, routing)

            return GraphQLFeature(config)
        }
    }
}

