package com.martmists.multiplatform.binary

object ULongData : BinaryData<ULong> {
    override val size: Int = 8
    override fun parseLE(array: ByteArray, offset: Int): ULong {
        return LongData.parseLE(array, offset).toULong()
    }

    override fun storeLE(value: ULong, array: ByteArray, offset: Int) {
        return LongData.storeLE(value.toLong(), array, offset)
    }
}
