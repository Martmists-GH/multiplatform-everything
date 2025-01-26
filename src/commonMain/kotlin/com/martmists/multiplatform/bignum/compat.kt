package com.martmists.multiplatform.bignum

operator fun Number.plus(other: BigNum): BigNum = other.plus(this)
operator fun Number.minus(other: BigNum): BigNum = (-other).plus(this)
operator fun Number.times(other: BigNum): BigNum = other.times(this)
operator fun Number.div(other: BigNum): BigNum = other.times(1 / this.toDouble())
operator fun Number.rem(other: BigNum): BigNum = BigNum(this).rem(other)
fun Number.pow(other: BigNum): BigNum = BigNum(this).pow(other)
