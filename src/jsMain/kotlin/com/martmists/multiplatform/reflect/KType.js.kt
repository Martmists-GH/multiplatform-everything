@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

package com.martmists.multiplatform.reflect

import kotlin.reflect.KType
import kotlin.reflect.js.internal.KTypeImpl

actual fun KType.withNullability(nullable: Boolean): KType {
    if (nullable == isMarkedNullable) return this
    return KTypeImpl(classifier!!, arguments, nullable)
}
