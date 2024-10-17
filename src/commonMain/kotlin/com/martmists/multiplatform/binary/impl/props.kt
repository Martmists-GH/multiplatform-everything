package com.martmists.multiplatform.binary.impl

import com.martmists.multiplatform.binary.BinaryData
import com.martmists.multiplatform.binary.StructData
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal class Prop<T>(private val owner: StructData, private val type: BinaryData<T>) : PropertyDelegateProvider<Any?, ReadWriteProperty<Any?, T>> {
    override fun provideDelegate(thisRef: Any?, property: KProperty<*>): BoundProp<T> {
        val offset = owner.currOffset
        owner.currOffset += type.size
        return BoundProp(owner.data, type, offset)
    }
}

internal class StructProp<T : StructData>(private val owner: StructData, private val constructor: (ByteArray, Int) -> T) : PropertyDelegateProvider<Any?, ReadWriteProperty<Any?, T>> {
    override fun provideDelegate(thisRef: Any?, property: KProperty<*>): BoundStructProp<T> {
        val inst = constructor(owner.data, owner.currOffset)
        owner.currOffset += inst.size
        return BoundStructProp(owner.data, inst, 0)
    }
}

internal class BoundProp<T>(private val data: ByteArray, private val inst: BinaryData<T>, private val offset: Int) : ReadWriteProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return inst.parseLE(data, offset)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        inst.storeLE(value, data, offset)
    }
}


internal class BoundStructProp<T : StructData>(private val data: ByteArray, private val inst: T, private val offset: Int) : ReadWriteProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return inst
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        throw UnsupportedOperationException()
    }
}
