package com.martmists.multiplatform.datastructure

/**
 * Weak reference to an object.
 *
 * In Kotlin/JVM, this is a [java.lang.ref.WeakReference].
 * In Kotlin/JS, this is a [window.WeakReference]
 * In Kotlin/Native, this is a [kotlin.native.ref.WeakReference]
 *
 * @param value the object to hold a weak reference to
 */
expect class WeakRef<T : Any>(value: T) {
    /**
     * Get the object this weak reference points to.
     *
     * @return the object or null if it has been garbage collected
     */
    fun get(): T?
}
