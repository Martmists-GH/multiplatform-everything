package com.martmists.multiplatform.datastructure.impl

import com.martmists.multiplatform.datastructure.WeakValueMap
import com.martmists.multiplatform.datastructure.WeakRef

internal class WeakValueMapImpl<K, V : Any>(entries: Array<out Pair<K, V>>) : WeakValueMap<K, V> {
    private val backing = entries.associate { it.first to WeakRef(it.second) }.toMutableMap()

    private fun eraseStale() {
        for ((k, v) in backing.entries) {
            if (v.get() == null) {
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
            return backing.keys
        }
    override val size: Int
        get() {
            eraseStale()
            return backing.size
        }
    override val values: MutableCollection<V>
        get() {
            eraseStale()
            return backing.values.map { it.get()!! }.toMutableSet()
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
        return backing.remove(key)?.get()
    }

    override fun putAll(from: Map<out K, V>) {
        backing.putAll(from.map { (k, v) -> k to WeakRef(v) })
    }

    override fun put(key: K, value: V): V? {
        eraseStale()
        return backing.put(key, WeakRef(value))?.get()
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

    private class Entry<K, V : Any>(private val map: WeakValueMapImpl<K, V>,
                                    override val key: K,
                                    valueRef: WeakRef<V>) : MutableMap.MutableEntry<K, V> {
        // Keep available while Entry is referenced
        override val value = valueRef.get()!!

        override fun setValue(newValue: V): V {
            map.backing[key] = WeakRef(newValue)
            return value
        }
    }
}
