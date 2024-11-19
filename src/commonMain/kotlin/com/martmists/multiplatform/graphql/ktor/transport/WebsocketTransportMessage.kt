package com.martmists.multiplatform.graphql.ktor.transport

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class WebsocketTransportMessage(
    val type: WebsocketTransportMessageType,
    val id: String? = null,
    val payload: JsonElement? = null,
)
