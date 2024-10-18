package com.martmists.multiplatform.graphql.ext

import kotlin.reflect.KClass
import kotlin.reflect.KType

/**
 * Returns the GraphQL type name for this [KType].
 */
val KType.gqlName: String
    get() {
        val name = (this.classifier as KClass<*>).simpleName ?: "<ERR: UNKNOWN TYPE>"
        return if (isMarkedNullable) name else "$name!"
    }
