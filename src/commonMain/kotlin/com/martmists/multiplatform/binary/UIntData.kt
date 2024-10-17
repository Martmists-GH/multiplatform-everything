package com.martmists.multiplatform.binary

object UIntData : BinaryData<UInt> {
    override val size: Int = 4
    override fun parseLE(array: ByteArray, offset: Int): UInt {
        return IntData.parseLE(array, offset).toUInt()
    }

    override fun storeLE(value: UInt, array: ByteArray, offset: Int) {
        IntData.storeLE(value.toInt(), array, offset)
    }
}
