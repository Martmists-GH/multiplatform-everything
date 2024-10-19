@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

package com.martmists.multiplatform.reflect

import kotlin.reflect.KType
import kotlin.native.internal.KTypeImpl

actual fun KType.withNullability(nullable: Boolean): KType {
    if (nullable == isMarkedNullable) return this
    return KTypeImpl<Any?>(classifier, arguments, nullable)
}
