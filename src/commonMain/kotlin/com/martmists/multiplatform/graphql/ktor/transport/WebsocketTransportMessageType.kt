package com.martmists.multiplatform.graphql.ktor.transport

import kotlinx.serialization.SerialName

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
