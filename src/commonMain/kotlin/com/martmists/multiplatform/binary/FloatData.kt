package com.martmists.multiplatform.binary

/**
 * The binary data type for a float.
 */
object FloatData : BinaryData<Float> {
    override val size: Int = 4
    override fun parseLE(array: ByteArray, offset: Int): Float {
        return IntData.parseLE(array, offset).toFloat()
    }

    override fun storeLE(value: Float, array: ByteArray, offset: Int) {
        IntData.storeLE(value.toRawBits(), array, offset)
    }
}
