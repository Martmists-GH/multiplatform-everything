package com.martmists.multiplatform.graphql.ktor

import com.martmists.multiplatform.graphql.GraphQL
import com.martmists.multiplatform.graphql.GraphQLDSL
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import kotlinx.serialization.json.JsonObject
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class GraphQLFeature(val configuration: Configuration) {
    class Configuration : GraphQL() {
        internal val contexts = mutableMapOf<KType, (RoutingCall) -> Any?>()
        var endpoint: String = "/graphql"
        var playgroundHtml: String? = null

        inline fun <reified T> context(noinline block: @GraphQLDSL RoutingCall.() -> T?) = context(typeOf<T>(), block)
        fun <T> context(type: KType, block: @GraphQLDSL RoutingCall.() -> T?) {
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

                        call.respondText(result.toString(), ContentType.Application.Json)
                    }
                }
            }

            pipeline.pluginOrNull(RoutingRoot)?.apply(routing) ?: pipeline.install(RoutingRoot, routing)

            return GraphQLFeature(config)
        }
    }
}
