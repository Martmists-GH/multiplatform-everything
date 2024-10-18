package com.martmists.multiplatform.binary

/**
 * The binary data type for a double.
 */
object DoubleData : BinaryData<Double> {
    override val size: Int = 8
    override fun parseLE(array: ByteArray, offset: Int): Double {
        return LongData.parseLE(array, offset).toDouble()
    }

    override fun storeLE(value: Double, array: ByteArray, offset: Int) {
        LongData.storeLE(value.toRawBits(), array, offset)
    }
}
