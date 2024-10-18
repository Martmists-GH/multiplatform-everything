package com.martmists.multiplatform.binary

/**
 * The binary data type for a short.
 */
object ShortData : BinaryData<Short> {
    override val size: Int = 2
    override fun parseLE(array: ByteArray, offset: Int): Short {
        val b1 = array[offset]
        val b2 = array[offset + 1]
        return (b1.toInt() or ((b2.toInt() and 0xFF) shl 8)).toShort()
    }

    override fun storeLE(value: Short, array: ByteArray, offset: Int) {
        array[offset] = value.toByte()
        array[offset + 1] = ((value.toInt() shr 8) and 0xFF).toByte()
    }
}
