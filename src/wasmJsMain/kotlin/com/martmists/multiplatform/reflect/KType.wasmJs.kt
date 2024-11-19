@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

package com.martmists.multiplatform.reflect

import kotlin.reflect.KType
import kotlin.wasm.internal.KTypeImpl

actual fun KType.withNullability(nullable: Boolean): KType {
    return KTypeImpl(classifier, arguments, nullable)
}
