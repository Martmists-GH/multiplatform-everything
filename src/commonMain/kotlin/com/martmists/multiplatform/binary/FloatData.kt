package com.martmists.multiplatform.binary

object FloatData : BinaryData<Float> {
    override val size: Int = 4
    override fun parseLE(array: ByteArray, offset: Int): Float {
        return IntData.parseLE(array, offset).toFloat()
    }

    override fun storeLE(value: Float, array: ByteArray, offset: Int) {
        IntData.storeLE(value.toRawBits(), array, offset)
    }
}
