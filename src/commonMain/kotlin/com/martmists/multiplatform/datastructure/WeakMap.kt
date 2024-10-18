package com.martmists.multiplatform.datastructure

import com.martmists.multiplatform.datastructure.impl.WeakMapImpl
import com.martmists.multiplatform.datastructure.impl.WeakValueMapImpl

/**
 * A Map that holds weak references to its keys.
 * If a key is collected by the garbage collector, it will be removed from the map.
 *
 * @see [WeakValueMap] for a map that holds weak references to its values.
 *
 * Note: Modifying `keys`, `values`, or `entries` has no effect on this implementation.
 */
interface WeakMap<K : Any, V> : MutableMap<K, V>

/**
 * A Map that holds weak references to its values.
 * If a value is collected by the garbage collector, it will be removed from the map.
 *
 * @see [WeakMap] for a map that holds weak references to its keys.
 *
 * Note: Modifying `keys`, `values`, or `entries` has no effect on this implementation.
 */
interface WeakValueMap<K, V : Any> : MutableMap<K, V>

/**
 * Creates a [WeakMap] from the given pairs.
 */
fun <K : Any, V> weakMapOf(vararg items: Pair<K, V>): WeakMap<K, V> = WeakMapImpl(items)

/**
 * Creates a [WeakValueMap] from the given pairs.
 */
fun <K, V : Any> weakValueMapOf(vararg items: Pair<K, V>): WeakValueMap<K, V> = WeakValueMapImpl(items)

/**
 * Creates a [WeakMap] from the given map.
 */
fun <K : Any, V> Map<K, V>.toWeakMap(): WeakMap<K, V> = WeakMapImpl(entries.map { (k, v) -> k to v }.toTypedArray())

/**
 * Creates a [WeakValueMap] from the given map.
 */
fun <K, V : Any> Map<K, V>.toWeakValueMap(): WeakValueMap<K, V> = WeakValueMapImpl(entries.map { (k, v) -> k to v }.toTypedArray())

/**
 * Creates a [WeakMap] from the given iterable.
 */
fun <K : Any, V> Iterable<Pair<K, V>>.toWeakMap(): WeakMap<K, V> = WeakMapImpl(this.toList().toTypedArray())

/**
 * Creates a [WeakValueMap] from the given iterable.
 */
fun <K, V : Any> Iterable<Pair<K, V>>.toWeakValueMap(): WeakValueMap<K, V> = WeakValueMapImpl(this.toList().toTypedArray())
