package com.martmists.multiplatform.binary

import com.martmists.multiplatform.binary.impl.Prop
import com.martmists.multiplatform.binary.impl.StructProp
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadWriteProperty


open class StructData(internal val data: ByteArray, offset: Int = 0) : BinaryData<Unit> {
    internal var currOffset: Int = offset
    protected val byte: PropertyDelegateProvider<Any?, ReadWriteProperty<Any?, Byte>> = Prop(this, ByteData)
    protected val ubyte: PropertyDelegateProvider<Any?, ReadWriteProperty<Any?, UByte>> = Prop(this, UByteData)
    protected val short: PropertyDelegateProvider<Any?, ReadWriteProperty<Any?, Short>> = Prop(this, ShortData)
    protected val ushort: PropertyDelegateProvider<Any?, ReadWriteProperty<Any?, UShort>> = Prop(this, UShortData)
    protected val int: PropertyDelegateProvider<Any?, ReadWriteProperty<Any?, Int>> = Prop(this, IntData)
    protected val uint: PropertyDelegateProvider<Any?, ReadWriteProperty<Any?, UInt>> = Prop(this, UIntData)
    protected val long: PropertyDelegateProvider<Any?, ReadWriteProperty<Any?, Long>> = Prop(this, LongData)
    protected val ulong: PropertyDelegateProvider<Any?, ReadWriteProperty<Any?, ULong>> = Prop(this, ULongData)
    protected val float: PropertyDelegateProvider<Any?, ReadWriteProperty<Any?, Float>> = Prop(this, FloatData)
    protected val double: PropertyDelegateProvider<Any?, ReadWriteProperty<Any?, Double>> = Prop(this, DoubleData)
    protected fun <T : StructData> struct(constructor: (ByteArray, Int) -> T): PropertyDelegateProvider<Any?, ReadWriteProperty<Any?, T>> = StructProp(this, constructor)

    override val size = currOffset - offset

    init {
        require(offset >= 0 && currOffset <= data.size) { "Struct does not fit in data" }
    }

    override fun storeLE(value: Unit, array: ByteArray, offset: Int) = throw UnsupportedOperationException()
    override fun parseLE(array: ByteArray, offset: Int): Unit = throw UnsupportedOperationException()
}
