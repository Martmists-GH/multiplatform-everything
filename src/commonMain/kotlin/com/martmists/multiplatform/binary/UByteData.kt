package com.martmists.multiplatform.binary

object UByteData : BinaryData<UByte> {
    override val size: Int = 1
    override fun parseLE(array: ByteArray, offset: Int): UByte {
        return array[offset].toUByte()
    }

    override fun storeLE(value: UByte, array: ByteArray, offset: Int) {
        array[offset] = value.toByte()
    }
}
