package com.martmists.multiplatform.binary

/**
 * The binary data type for an unsigned short.
 */
object UShortData : BinaryData<UShort> {
    override val size: Int = 2
    override fun parseLE(array: ByteArray, offset: Int): UShort {
        return ShortData.parseLE(array, offset).toUShort()
    }

    override fun storeLE(value: UShort, array: ByteArray, offset: Int) {
        ShortData.storeLE(value.toShort(), array, offset)
    }
}
