package com.martmists.multiplatform.datastructure

import com.martmists.multiplatform.datastructure.impl.ImmutableWeakMapImpl
import com.martmists.multiplatform.datastructure.impl.ImmutableWeakValueMapImpl
import com.martmists.multiplatform.datastructure.impl.MutableWeakMapImpl
import com.martmists.multiplatform.datastructure.impl.MutableWeakValueMapImpl

interface WeakMap<K : Any, V> : Map<K, V>
interface WeakValueMap<K, V : Any> : Map<K, V>
// NOTE: Modifying keys/values/entries on MutableWeak[Value]Map is not supported.
interface MutableWeakMap<K : Any, V> : WeakMap<K, V>, MutableMap<K, V>
interface MutableWeakValueMap<K, V : Any> : WeakValueMap<K, V> , MutableMap<K, V>

// TODO: Consider removing immutable weak maps since it doesn't make much sense as entries get removed regardless.
fun <K : Any, V> weakMapOf(vararg items: Pair<K, V>): WeakMap<K, V> = ImmutableWeakMapImpl(items)
fun <K, V : Any> weakValueMapOf(vararg items: Pair<K, V>): WeakValueMap<K, V> = ImmutableWeakValueMapImpl(items)
fun <K : Any, V> mutableWeakMapOf(vararg items: Pair<K, V>): MutableWeakMap<K, V> = MutableWeakMapImpl(items)
fun <K, V : Any> mutableWeakValueMapOf(vararg items: Pair<K, V>): MutableWeakValueMap<K, V> = MutableWeakValueMapImpl(items)

fun <K : Any, V> Map<K, V>.toWeakMap(): WeakMap<K, V> {
    if (this is ImmutableWeakMapImpl) {
        return this
    }
    return ImmutableWeakMapImpl(this.entries.map { (k, v) -> k to v }.toTypedArray())
}

fun <K : Any, V> Map<K, V>.toMutableWeakMap(): MutableWeakMap<K, V> = MutableWeakMapImpl(entries.map { (k, v) -> k to v }.toTypedArray())

fun <K, V : Any> Map<K, V>.toWeakValueMap(): WeakValueMap<K, V> {
    if (this is ImmutableWeakValueMapImpl) {
        return this
    }
    return ImmutableWeakValueMapImpl(this.entries.map { (k, v) -> k to v }.toTypedArray())
}

fun <K, V : Any> Map<K, V>.toMutableWeakValueMap(): MutableWeakValueMap<K, V> = MutableWeakValueMapImpl(entries.map { (k, v) -> k to v }.toTypedArray())
