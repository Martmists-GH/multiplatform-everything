package com.martmists.multiplatform.binary

/**
 * The binary data type for an int.
 */
object IntData : BinaryData<Int> {
    override val size: Int = 4
    override fun parseLE(array: ByteArray, offset: Int): Int {
        val b0 = array[offset]
        val b1 = array[offset + 1]
        val b2 = array[offset + 2]
        val b3 = array[offset + 3]
        return b0.toInt() or (b1.toInt() shl 8) or (b2.toInt() shl 16) or (b3.toInt() shl 24)
    }

    override fun storeLE(value: Int, array: ByteArray, offset: Int) {
        array[offset] = value.toByte()
        array[offset + 1] = (value shr 8).toByte()
        array[offset + 2] = (value shr 16).toByte()
        array[offset + 3] = (value shr 24).toByte()
    }
}
