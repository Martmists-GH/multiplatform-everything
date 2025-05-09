package com.martmists.multiplatform.datastructure

import kotlin.reflect.KProperty

/**
 * Weak Reference as Delegate
 */
class WeakDelegate<T : Any>() {
    private var ref: WeakRef<T>? = null

    constructor(value: T) : this() {
        ref = WeakRef<T>(value)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? = ref?.get()
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        ref = WeakRef(value)
    }
}
