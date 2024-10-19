package com.martmists.multiplatform.reflect

import kotlin.reflect.KType

expect fun KType.withNullability(nullable: Boolean): KType
