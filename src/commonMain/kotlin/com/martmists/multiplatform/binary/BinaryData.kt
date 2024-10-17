package com.martmists.multiplatform.binary

// TODO: support big-endian?
interface BinaryData<T> {
    val size: Int
    fun parse(array: ByteArray, offset: Int, bigEndian: Boolean = false): T = if (bigEndian) parseBE(array, offset) else parseLE(array, offset)
    fun parseLE(array: ByteArray, offset: Int = 0): T
    fun parseBE(array: ByteArray, offset: Int = 0): T {
        return parseLE(array.sliceArray(offset until offset + size).reversedArray(), 0)
    }

    fun store(value: T, array: ByteArray, offset: Int, bigEndian: Boolean = false) = if (bigEndian) storeBE(value, array, offset) else storeLE(value, array, offset)
    fun storeLE(value: T, array: ByteArray, offset: Int = 0): Unit
    fun storeBE(value: T, array: ByteArray, offset: Int = 0): Unit {
        val tmp = ByteArray(size)
        storeLE(value, tmp, 0)
        tmp.reverse()
        tmp.copyInto(array, offset)
    }
}
