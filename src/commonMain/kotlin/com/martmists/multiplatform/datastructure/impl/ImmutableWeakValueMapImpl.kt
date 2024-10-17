package com.martmists.multiplatform.datastructure.impl

import com.martmists.multiplatform.datastructure.WeakRef
import com.martmists.multiplatform.datastructure.WeakValueMap

internal class ImmutableWeakValueMapImpl<K, V : Any>(entries: Array<out Pair<K, V>>) : WeakValueMap<K, V> {
    private val backing = entries.associate { it.first to WeakRef(it.second) }.toMutableMap()  // Needs to be mutable for removing stale entries

    private fun eraseStale() {
        for ((k, v) in backing.entries) {
            if (v.get() == null) {
                backing.remove(k)
            }
        }
    }

    override val entries: Set<Map.Entry<K, V>>
        get() {
            eraseStale()
            return backing.entries.map { (k, v) -> Entry(k, v) }.toSet()
        }
    override val keys: Set<K>
        get() {
            eraseStale()
            return backing.keys
        }
    override val size: Int
        get() {
            eraseStale()
            return backing.size
        }
    override val values: Collection<V>
        get() {
            eraseStale()
            return backing.values.map { it.get()!! }.toSet()
        }

    override fun isEmpty(): Boolean {
        eraseStale()
        return backing.isEmpty()
    }

    override fun get(key: K): V? {
        eraseStale()
        return backing[key]?.get()
    }

    override fun containsValue(value: V): Boolean {
        eraseStale()
        return backing.values.any { it.get() == value }
    }

    override fun containsKey(key: K): Boolean {
        eraseStale()
        return backing.containsKey(key)
    }

    private class Entry<K, V : Any>(override val key: K, valueRef: WeakRef<V>) : Map.Entry<K, V> {
        // Keep available while Entry is referenced
        override val value = valueRef.get()!!
    }
}
