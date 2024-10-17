package com.martmists.multiplatform.datastructure.impl

import com.martmists.multiplatform.datastructure.WeakMap
import com.martmists.multiplatform.datastructure.WeakRef

internal class ImmutableWeakMapImpl<K : Any, V>(entries: Array<out Pair<K, V>>) : WeakMap<K, V> {
    private val backing = entries.associate { WeakRef(it.first) to it.second }.toMutableMap()  // Needs to be mutable for removing stale entries

    private fun eraseStale() {
        for (k in backing.keys) {
            if (k.get() == null) {
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
            return backing.keys.map { it.get()!! }.toSet()
        }
    override val size: Int
        get() {
            eraseStale()
            return backing.size
        }
    override val values: Collection<V>
        get() {
            eraseStale()
            return backing.values
        }

    override fun isEmpty(): Boolean {
        eraseStale()
        return backing.isEmpty()
    }

    override fun get(key: K): V? {
        eraseStale()
        val ref = backing.keys.firstOrNull { it.get() == key } ?: return null
        return backing[ref]
    }

    override fun containsValue(value: V): Boolean {
        eraseStale()
        return backing.containsValue(value)
    }

    override fun containsKey(key: K): Boolean {
        eraseStale()
        return backing.keys.any { it.get() == key }
    }

    private class Entry<K : Any, V>(keyRef: WeakRef<K>, override val value: V) : Map.Entry<K, V> {
        // Keep available while Entry is referenced
        override val key = keyRef.get()!!
    }
}
