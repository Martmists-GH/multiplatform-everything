package com.martmists.multiplatform.binary

/**
 * The base interface for all binary data types.
 *
 * These types help simplify dealing with packed binary data.
 */
interface BinaryData<T> {
    /**
     * The number of bytes this type takes up in a binary representation.
     */
    val size: Int

    /**
     * Read the data from the given array at the given offset.
     *
     * @param array The byte array to read from.
     * @param offset The offset in the array to start reading at.
     * @param bigEndian Whether to read the data in big endian or little endian order.
     * @return The parsed data.
     */
    fun parse(array: ByteArray, offset: Int, bigEndian: Boolean = false): T = if (bigEndian) parseBE(array, offset) else parseLE(array, offset)

    /**
     * Read the data from the given array at the given offset in little endian order.
     */
    fun parseLE(array: ByteArray, offset: Int = 0): T

    /**
     * Read the data from the given array at the given offset in big endian order.
     */
    fun parseBE(array: ByteArray, offset: Int = 0): T {
        return parseLE(array.sliceArray(offset until offset + size).reversedArray(), 0)
    }

    /**
     * Write the data to the given array at the given offset.
     *
     * @param value The data to write.
     * @param array The byte array to write to.
     * @param offset The offset in the array to start writing at.
     * @param bigEndian Whether to write the data in big endian or little endian order.
     */
    fun store(value: T, array: ByteArray, offset: Int, bigEndian: Boolean = false) = if (bigEndian) storeBE(value, array, offset) else storeLE(value, array, offset)

    /**
     * Write the data to the given array at the given offset in little endian order.
     */
    fun storeLE(value: T, array: ByteArray, offset: Int = 0)

    /**
     * Write the data to the given array at the given offset in big endian order.
     */
    fun storeBE(value: T, array: ByteArray, offset: Int = 0) {
        val tmp = ByteArray(size)
        storeLE(value, tmp, 0)
        tmp.reverse()
        tmp.copyInto(array, offset)
    }
}
