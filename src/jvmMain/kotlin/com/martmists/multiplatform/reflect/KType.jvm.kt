@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

package com.martmists.multiplatform.reflect

import kotlin.reflect.KType
import kotlin.reflect.jvm.internal.KTypeImpl

actual fun KType.withNullability(nullable: Boolean): KType {
    return (this as KTypeImpl).makeNullableAsSpecified(nullable)
}
