package com.martmists.multiplatform.datastructure

@JsName("WeakRef")
private external class WasmWeakRef<T: JsAny> {
    constructor(target: T)
    fun deref(): T?
}


actual class WeakRef<T : Any> constructor(value: JsReference<T>) {
    private val impl = WasmWeakRef(value)

    actual constructor(value: T) : this(value.toJsReference())

    actual fun get(): T? {
        return impl.deref()?.get()
    }
}
