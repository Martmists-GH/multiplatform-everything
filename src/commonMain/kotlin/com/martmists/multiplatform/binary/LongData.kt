package com.martmists.multiplatform.binary

/**
 * The binary data type for a long.
 */
object LongData : BinaryData<Long> {
    override val size: Int = 8
    override fun parseLE(array: ByteArray, offset: Int): Long {
        val b0 = array[offset]
        val b1 = array[offset + 1]
        val b2 = array[offset + 2]
        val b3 = array[offset + 3]
        val b4 = array[offset + 4]
        val b5 = array[offset + 5]
        val b6 = array[offset + 6]
        val b7 = array[offset + 7]
        return b0.toLong() or ((b1.toLong() and 0xff) shl 8) or ((b2.toLong() and 0xff) shl 16) or ((b3.toLong() and 0xff) shl 24) or
                ((b4.toLong() and 0xff) shl 32) or ((b5.toLong() and 0xff) shl 40) or ((b6.toLong() and 0xff) shl 48) or ((b7.toLong() and 0xff) shl 56)
    }

    override fun storeLE(value: Long, array: ByteArray, offset: Int) {
        array[offset] = value.toByte()
        array[offset + 1] = (value shr 8).toByte()
        array[offset + 2] = (value shr 16).toByte()
        array[offset + 3] = (value shr 24).toByte()
        array[offset + 4] = (value shr 32).toByte()
        array[offset + 5] = (value shr 40).toByte()
        array[offset + 6] = (value shr 48).toByte()
        array[offset + 7] = (value shr 56).toByte()
    }
}
