package com.martmists.multiplatform.datastructure

import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.ref.WeakReference

@OptIn(ExperimentalNativeApi::class)
actual class WeakRef<T : Any> actual constructor(value: T) {
    private val ref = WeakReference(value)

    actual fun get(): T? = ref.get()
}
