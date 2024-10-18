package com.martmists.multiplatform.datastructure.impl

import com.martmists.multiplatform.datastructure.WeakMap
import com.martmists.multiplatform.datastructure.WeakRef

internal class WeakMapImpl<K : Any, V>(entries: Array<out Pair<K, V>>) : WeakMap<K, V> {
    private val backing = entries.associate { WeakRef(it.first) to it.second }.toMutableMap()

    private fun eraseStale() {
        for (k in backing.keys) {
            if (k.get() == null) {
                backing.remove(k)
            }
        }
    }

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() {
            eraseStale()
            return backing.entries.map { (k, v) -> Entry(this, k, v) }.toMutableSet()
        }
    override val keys: MutableSet<K>
        get() {
            eraseStale()
            return backing.keys.map { it.get()!! }.toMutableSet()
        }
    override val size: Int
        get() {
            eraseStale()
            return backing.size
        }
    override val values: MutableCollection<V>
        get() {
            eraseStale()
            return backing.values
        }

    override fun isEmpty(): Boolean {
        eraseStale()
        return backing.isEmpty()
    }

    override fun clear() {
        backing.clear()
    }

    override fun remove(key: K): V? {
        eraseStale()
        val ref = backing.keys.firstOrNull { it.get() == key } ?: return null
        return backing.remove(ref)
    }

    override fun putAll(from: Map<out K, V>) {
        backing.putAll(from.map { (k, v) -> WeakRef(k) to v })
    }

    override fun put(key: K, value: V): V? {
        eraseStale()
        val ref = backing.keys.firstOrNull { it.get() == key } ?: WeakRef(key)
        return backing.put(ref, value)
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

    private class Entry<K : Any, V>(private val map: WeakMapImpl<K, V>,
                                    private val keyRef: WeakRef<K>,
                                    override val value: V) : MutableMap.MutableEntry<K, V> {
        // Keep available while Entry is referenced
        override val key = keyRef.get()!!

        override fun setValue(newValue: V): V {
            map.backing[keyRef] = newValue
            return value
        }
    }
}
