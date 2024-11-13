package com.martmists.multiplatform.reflect

import kotlin.reflect.KType
import kotlin.reflect.full.withNullability

actual fun KType.withNullability(nullable: Boolean): KType {
    return this.withNullability(nullable)
}
