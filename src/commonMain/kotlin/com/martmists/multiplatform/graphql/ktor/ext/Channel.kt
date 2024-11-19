package com.martmists.multiplatform.graphql.ktor.ext

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel

suspend fun <E> channelOf(element: E): ReceiveChannel<E> {
    val channel = Channel<E>()
    channel.send(element)
    return channel
}
