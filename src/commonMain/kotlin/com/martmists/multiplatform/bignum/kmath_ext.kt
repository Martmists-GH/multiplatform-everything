package com.martmists.multiplatform.bignum

fun max(a: BigNum, b: BigNum): BigNum {
    return if (a < b) b else a
}

fun min(a: BigNum, b: BigNum): BigNum {
    return if (a < b) a else b
}
