package com.martmists.multiplatform.graphql.ktor.ext

import com.martmists.multiplatform.graphql.SchemaRequestContext
import io.ktor.server.application.*

val SchemaRequestContext.call: ApplicationCall
    get() = context<ApplicationCall>()!!
