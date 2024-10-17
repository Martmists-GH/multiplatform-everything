package com.martmists.multiplatform.datastructure

expect class WeakRef<T : Any>(value: T) {
    fun get(): T?
}
