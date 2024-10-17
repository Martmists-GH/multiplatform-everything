package com.martmists.multiplatform.datastructure

external interface JsWeakRef<T: Any> {
    fun deref(): T?
}

actual class WeakRef<T : Any> actual constructor(value: T) {
    @Suppress("UNCHECKED_CAST_TO_EXTERNAL_INTERFACE")
    private val ref = js("new WeakRef(value)") as JsWeakRef<T>

    actual fun get(): T? = ref.deref()
}
