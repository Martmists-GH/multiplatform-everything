package com.martmists.multiplatform.binary

/**
 * The binary data type for an unsigned byte.
 */
object UByteData : BinaryData<UByte> {
    override val size: Int = 1
    override fun parseLE(array: ByteArray, offset: Int): UByte {
        return array[offset].toUByte()
    }

    override fun storeLE(value: UByte, array: ByteArray, offset: Int) {
        array[offset] = value.toByte()
    }
}
