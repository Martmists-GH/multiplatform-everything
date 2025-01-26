package com.martmists.multiplatform.bignum

import kotlin.jvm.JvmInline
import kotlin.math.*

@JvmInline
value class BigNum private constructor(
    private val data: DoubleArray,
) {
    init {
        require(data.size >= 2) { "data is too small!" }
    }

    private val sign: Int
        get() = data.first().toInt()

    fun copy(): BigNum = BigNum(data.copyOf())

    fun isNaN() = data[1].isNaN()
    fun isFinite() = data[1].isFinite()
    fun isInfinite() = data[1].isInfinite()
    fun isInt(): Boolean {
        if (sign == -1) {
            return abs().isInt()
        }
        if (this > MAX_SAFE_INTEGER) {
            return true
        }
        val num = toNumber()
        return floor(num) == num
    }

    @Suppress("RESERVED_MEMBER_INSIDE_VALUE_CLASS")
    override operator fun equals(other: Any?): Boolean {
        @Suppress("FORBIDDEN_IDENTITY_EQUALS")  // `this` can somehow become null
        if (other === null) return false
        if (other !is BigNum) return false

        if (isNaN() || other.isNaN()) return false

        return data.contentEquals(other.data)
    }

    fun equalsValue(other: Number) = equals(BigNum(other))

    operator fun compareTo(other: BigNum): Int {
        val selfNan = isNaN()
        val otherNan = other.isNaN()

        return when {
            selfNan && otherNan -> 0
            selfNan -> 1
            otherNan -> -1
            data[1] == Double.POSITIVE_INFINITY && other.data[1] != Double.POSITIVE_INFINITY -> sign
            data[1] != Double.POSITIVE_INFINITY && other.data[1] == Double.POSITIVE_INFINITY -> other.sign
            data[1] == 0.0 && other.data[1] == 0.0 -> 0
            sign == -other.sign -> sign
            else -> {
                sign * when {
                    data.size > other.data.size -> 1
                    data.size < other.data.size -> -1
                    else -> run {
                        for (i in data.lastIndex downTo 1) {
                            if (data[i] > other.data[i]) {
                                return@run 1
                            }
                            if (data[i] < other.data[i]) {
                                return@run -1
                            }
                        }
                        0
                    }
                }
            }
        }
    }

    operator fun compareTo(other: Number) = compareTo(BigNum(other))

    fun toNumber(): Double {
        if (sign == -1) {
            return -(abs().toNumber())
        }

        if (data.size >= 3 && ((data[2] >= 2) || (data[2] == 1.0 && data[1] > floor(log10(Double.MAX_VALUE))))) {
            return Double.POSITIVE_INFINITY
        }

        if (data.size >= 4 && (data[1] >= 3 || data[2] >= 1 || data[3] >= 1)) {
            return Double.POSITIVE_INFINITY
        }

        if (data.size >= 5 && (data[1] > 1 || data[2] >= 1 || data[3] >= 1)) {
            for (i in 4 until data.size) {
                if (data[i] > 0) {
                    return Double.POSITIVE_INFINITY
                }
            }
        }

        if (data.size == 3 && data[2] == 1.0) {
            return 10.0.pow(data[1])
        }

        return data[1]
    }

    operator fun unaryMinus(): BigNum {
        val newData = data.copyOf()
        newData[0] = -newData[0]
        return normalized(newData)
    }

    operator fun plus(other: Number) = plus(BigNum(other))
    operator fun plus(other: BigNum): BigNum {
        return when {
            isInfinite() && other.isInfinite() && sign == -other.sign -> BigNum.NaN
            sign == -1 -> {
                -(-this - other)
            }
            other.sign == -1 -> {
                this - other
            }
            this == BigNum.ZERO -> other
            other == BigNum.ZERO -> this
            (isNaN() && other.isNaN()) -> BigNum.NaN
            isInfinite() -> this
            other.isInfinite() -> other
            else -> {
                val (minRef, maxRef) = if (this < other) (this to other) else (other to this)
                val min = minRef.copy()
                val max = maxRef.copy()

                if (min.data.getOrNull(2) == 2.0 && min <= E_MAX_SAFE_INTEGER) {
                    min.data[2] = 1.0
                    min.data[1] = 10.0.pow(min.data[1])
                }

                if (max.data.getOrNull(2) == 2.0 && max <= E_MAX_SAFE_INTEGER) {
                    max.data[2] = 1.0
                    max.data[1] = 10.0.pow(max.data[1])
                }

                when {
                    max > E_MAX_SAFE_INTEGER || (max / min) > MAX_SAFE_INTEGER -> max
                    max.data.getOrElse(2) { 0.0 } == 0.0 -> BigNum(min.toNumber() + max.toNumber())
                    max.data.getOrNull(2) == 1.0 -> {
                        val a = if (min.data.getOrElse(2) { 0.0 } != 0.0) min.data[1] else log10(min.data[1])
                        normalized(doubleArrayOf(
                            1.0,
                            a + log10(10.0.pow(max.data[1] - a) + 1),
                            1.0
                        ))
                    }
                    else -> BigNum(-1)
                }
            }
        }
    }

    operator fun minus(other: Number) = minus(BigNum(other))
    operator fun minus(other: BigNum): BigNum {
        return when {
            isInfinite() && other.isInfinite() && sign == other.sign -> BigNum.NaN
            sign == -1 -> {
                return -(-this).plus(other)
            }
            other.sign == -1 -> this + -other
            this == other -> BigNum.ZERO
            other == BigNum.ZERO -> this
            this == BigNum.ZERO -> -other
            isNaN() || other.isNaN() -> BigNum.NaN
            isInfinite() -> this
            other.isInfinite() -> BigNum.NEGATIVE_INFINITY
            else -> {
                val (minRef, maxRef) = if (this < other) (this to other) else (other to this)
                val min = minRef.copy()
                val max = maxRef.copy()
                if (min.data.getOrNull(2) == 2.0 && min <= E_MAX_SAFE_INTEGER) {
                    min.data[2] = 1.0
                    min.data[1] = 10.0.pow(min.data[1])
                }
                if (max.data.getOrNull(2) == 2.0 && max <= E_MAX_SAFE_INTEGER) {
                    max.data[2] = 1.0
                    max.data[1] = 10.0.pow(max.data[1])
                }
                when {
                    max > E_MAX_SAFE_INTEGER || (max / min) > MAX_SAFE_INTEGER -> if (minRef == this) -max else max
                    max.data.getOrElse(2) { 0.0 } == 0.0 -> BigNum(toNumber() - other.toNumber())
                    max.data.getOrNull(2) == 1.0 -> {
                        val a = if (min.data.getOrElse(2) { 0.0 } != 0.0) min.data[1] else log10(min.data[1])
                        val sign = if (minRef == this) -1 else 1
                        sign * normalized(doubleArrayOf(
                            1.0,
                            a + log10(10.0.pow(max.data[1] - a) - 1),
                            1.0
                        ))
                    }
                    else -> BigNum(-1)
                }
            }
        }
    }

    operator fun times(other: Number) = div(BigNum(other))
    operator fun times(other: BigNum): BigNum {
        return when {
            isInfinite() && other.isInfinite() && sign == -other.sign -> BigNum.NaN
            sign == -other.sign -> -(abs().times(other.abs()))
            sign == -1 -> abs() * other.abs()
            isNaN() || other.isNaN() -> BigNum.NaN
            this == BigNum.ZERO || other == BigNum.ZERO -> BigNum.ZERO
            this == BigNum.ONE -> other
            other == BigNum.ONE -> this
            isInfinite() || other.isInfinite() -> BigNum.POSITIVE_INFINITY
            else -> {
                val max = max(this, other)
                if (max > EE_MAX_SAFE_INTEGER) {
                    max
                } else {
                    val n = toNumber() * other.toNumber()
                    if (n <= MAX_SAFE_INTEGER) BigNum(n) else BigNum.TEN.pow(log10() + other.log10())
                }
            }
        }
    }

    operator fun div(other: Number) = div(BigNum(other))
    operator fun div(other: BigNum): BigNum {
        return when {
            isInfinite() && other.isInfinite() && sign == -other.sign -> BigNum.NaN
            sign * other.sign == -1 -> -(abs() / other.abs())
            sign == -1 -> abs() / other.abs()
            isNaN() || other.isNaN() -> BigNum.NaN
            other == BigNum.ZERO -> BigNum.POSITIVE_INFINITY
            isInfinite() || other == BigNum.ONE -> this
            other.isInfinite() -> BigNum.ZERO
            else -> {
                val max = max(this, other)
                if (max > EE_MAX_SAFE_INTEGER) {
                    if (max == this) this else BigNum.ZERO
                } else {
                    val n = toNumber() / other.toNumber()
                    if (n <= MAX_SAFE_INTEGER) {
                        BigNum(n)
                    } else {
                        val pw = BigNum.TEN.pow(this.log10() - other.log10())
                        val fp = pw.floor()
                        if (pw - fp < BigNum(1e-9)) fp else pw
                    }
                }
            }
        }
    }

    operator fun rem(other: Number): BigNum = rem(BigNum(other))
    operator fun rem(other: BigNum): BigNum {
        return when {
            other == BigNum.ZERO -> BigNum.ZERO
            sign == -other.sign -> -(abs().rem(other.abs()))
            sign == -1 -> abs().rem(other.abs())
            else -> this - ((this / other).floor() * other)
        }
    }

    fun abs(): BigNum = if (sign == -1) -this else this
    fun floor(): BigNum {
        if (isInt()) {
            return this
        }
        return BigNum(floor(toNumber()))
    }
    fun ceil(): BigNum {
        if (isInt()) {
            return this
        }
        return BigNum(ceil(toNumber()))
    }
    fun round(): BigNum {
        if (isInt()) {
            return this
        }
        return BigNum(round(toNumber()))
    }

    fun pow(other: Number): BigNum = pow(BigNum(other))
    fun pow(other: BigNum): BigNum {
        return when {
            other == BigNum.ZERO -> BigNum.ONE
            other == BigNum.ONE -> this
            other.sign == -1 -> BigNum.ONE / pow(-other)
            sign == -1 && other.isInt() -> if (other.rem(2) == BigNum.ONE) abs().pow(other) else -abs().pow(other)
            sign == -1 -> abs().pow(other)
            this == BigNum.ONE || this == BigNum.ZERO -> this
            else -> {
                val max = max(this, other)
                when {
                    max > TETRATED_MAX_SAFE_INTEGER -> {
                        max
                    }
                    this == BigNum.TEN -> {
                        if (other > 0) {
                            val o = other.data.toMutableList()
                            o.getOrNull(2)?.let {
                                o[2] = it + 1
                            } ?: run {
                                o.add(1.0)
                            }
                            normalized(o.toDoubleArray())
                        } else {
                            BigNum(10.0.pow(other.toNumber()))
                        }
                    }
                    other < 1 -> {
                        root(1 / other)
                    }
                    else -> {
                        val n = toNumber().pow(other.toNumber())
                        if (n <= MAX_SAFE_INTEGER) {
                            BigNum(n)
                        } else {
                            BigNum.TEN.pow(log10() * other)
                        }
                    }
                }
            }
        }
    }

    fun logBase(base: Number) = logBase(BigNum(base))
    fun logBase(base: BigNum): BigNum = log10() / base.log10()

    fun log10(): BigNum {
        return when {
            sign == -1 -> BigNum.NaN
            this == BigNum.ZERO -> BigNum.NEGATIVE_INFINITY
            this <= MAX_SAFE_INTEGER -> BigNum(log10(toNumber()))
            this.isInfinite() -> this
            this > TETRATED_MAX_SAFE_INTEGER -> this
            else -> {
                val res = data.copyOf()
                res[2] = res[2] - 1
                normalized(res)
            }
        }
    }

    fun root(other: Number): BigNum = root(BigNum(other))
    fun root(other: BigNum): BigNum {
        return when {
            other == BigNum.ONE -> this
            other.sign == -1 -> 1 / root(-other)
            other < 1 -> pow(1 / other)
            sign == -1 -> BigNum.NaN  // TODO: Return roots for x^p where p = 2k+1?
            this == BigNum.ONE || this == BigNum.ZERO -> this
            else -> {
                val max = max(this, other)
                if (max > TETRATED_MAX_SAFE_INTEGER) {
                    if (max == this) this else BigNum.ZERO
                } else {
                    BigNum.TEN.pow(log10() / other)
                }
            }
        }
    }
    fun sqrt() = root(2)

    fun lambertw(): BigNum {
        return when {
            else -> TODO()
        }
    }

    fun tetrate(other: Number) = tetrate(BigNum(other))
    fun tetrate(other: BigNum): BigNum {
        return when {
            isNaN() || other.isNaN() || other <= -2 -> BigNum.NaN
            other.isInfinite() && other.sign == 1 -> {
                val tmp = -logBase(E)
                tmp.lambertw() / tmp
            }
            this == BigNum.ZERO -> when {
                other == BigNum.ZERO -> BigNum.NaN
                other.rem(2) == BigNum.ZERO -> BigNum.ZERO
                else -> BigNum.ONE
            }
            this == BigNum.ONE -> if (other == -BigNum.ONE) BigNum.NaN else BigNum.ONE
            other == -BigNum.ONE -> BigNum.ZERO
            other == BigNum.ZERO -> BigNum.ONE
            other == BigNum.ONE -> this
            other == BigNum(2) -> this.pow(this)
            else -> {
                val max = max(this, other)
                when {
                    max > ARROW3_MAX_SAFE_INTEGER -> max
                    max > TETRATED_MAX_SAFE_INTEGER || other > MAX_SAFE_INTEGER -> {
                        if (this < exp(1 / E)) {
                            val tmp = -logBase(E)
                            tmp.lambertw() / tmp
                        } else {
                            val tmp = (slog(10) + other).data.toMutableList()
                            tmp.getOrNull(3)?.let {
                                tmp[3] = it + 1
                            } ?: run {
                                while (tmp.size < 3) {
                                    tmp.add(0.0)
                                }
                                tmp.add(1.0)
                            }
                            normalized(tmp.toDoubleArray())
                        }
                    }
                    else -> {
                        var f = other.floor().toNumber().toInt()
                        var r = pow(other - f)
                        var l = BigNum.NaN
                        while (f != 0 && r < E_MAX_SAFE_INTEGER) {
                            if (f > 0) {
                                r = pow(r)
                                if (l == r) {
                                    f = 0
                                    break
                                }
                                l = r
                                f -= 1
                            } else {
                                r = r.logBase(this)
                                if (l == r) {
                                    f = 0
                                    break
                                }
                                l = r
                                f += 1
                            }
                        }
                        val data = r.data.toMutableList()
                        data.getOrNull(2)?.let {
                            data[2] = it + f
                        } ?: run {
                            data.add(f.toDouble())
                        }
                        normalized(data.toDoubleArray())
                    }
                }
            }
        }
    }

    fun slog(base: Number) = slog(BigNum(base))
    fun slog(base: BigNum): BigNum {
        return when {
            isNaN() || base.isNaN() || (this.isInfinite() && base.isInfinite()) -> BigNum.NaN
            isInfinite() -> BigNum.POSITIVE_INFINITY
            base.isInfinite() -> BigNum.ZERO
            sign == -1 -> -BigNum.ONE
            this < 1 -> BigNum.ZERO
            this == base -> BigNum.ONE
            else -> let {
                if (this < exp(1 / E)) {
                    val a = base.tetrate(BigNum.POSITIVE_INFINITY)
                    if (this == a) {
                        return@let BigNum.POSITIVE_INFINITY
                    } else if (this > a) {
                        return@let BigNum.NaN
                    }
                }
                val max = max(this, base)
                when {
                    max > ARROW3_MAX_SAFE_INTEGER -> if (max == this) this else BigNum.ZERO
                    max > TETRATED_MAX_SAFE_INTEGER -> {
                        if (max == this) {
                            val res = data.copyOf()
                            res[3] = res[3] - 1
                            normalized(res) - res[2]
                        } else BigNum.ZERO
                    }
                    else -> {
                        var r = 0.0
                        val t = data.getOrElse(2) { 0.0 } - base.data.getOrElse(2) { 0.0 }
                        var tmp = if (t > 3) {
                            val out = data.copyOf()
                            val l = t - 3
                            r += l
                            out[2] = out[2] - l
                            normalized(out)
                        } else copy()
                        for (i in 0 until 100) {
                            if (tmp < 0) {
                                tmp = base.pow(tmp)
                                r--
                            } else if (tmp <= 1) {
                                return@let BigNum(r + tmp.toNumber() - 1)
                            } else {
                                r++
                                tmp = tmp.logBase(base)
                            }
                        }
                        if (tmp > 10) {
                            return BigNum(r)
                        }
                        throw IllegalStateException()
                    }
                }
            }
        }
    }

    fun arrow(arrows: Number, other: Number) = arrow(BigNum(arrows), BigNum(other))
    fun arrow(arrows: BigNum, other: BigNum): BigNum {
        return when {
            isNaN() || other.isNaN() || !arrows.isInt() || arrows < 0 -> BigNum.NaN
            arrows == BigNum.ZERO -> this * other
            arrows == BigNum.ONE -> this.pow(other)
            arrows.equalsValue(2) -> this.tetrate(other)
            other.sign == -1 -> BigNum.NaN
            other == BigNum.ZERO -> BigNum.ONE
            other == BigNum.ONE -> this
            arrows > Int.MAX_VALUE - 2 -> BigNum.POSITIVE_INFINITY
            else -> let {
                if (other.equalsValue(2)) {
                    return@let arrow(arrows - 1, this)
                }
                val numArrows = arrows.toNumber().toInt()
                val max = max(this, other)

                val limit = BigNum(DoubleArray(numArrows + 2) {
                    when (it) {
                        0 -> 1.0
                        1 -> 12.857287541066
                        2, numArrows + 1 -> 1.0
                        else -> 0.0
                    }
                })
                val limit2 = BigNum(DoubleArray(numArrows + 1) {
                    when (it) {
                        0 -> 1.0
                        1 -> 12.857287541066
                        2, numArrows -> 1.0
                        else -> 0.0
                    }
                })

                if (max > limit || other > MAX_SAFE_INTEGER) {
                    val r = if (max > limit) {
                        val out = data.copyOf()
                        out[numArrows] = out[numArrows] - 1
                        normalized(out)
                    } else if (max > limit2) {
                        BigNum(data.getOrElse(numArrows) { 0.0 })
                    } else {
                        BigNum.ZERO
                    }
                    val out = (r + other).data.toMutableList()
                    out.getOrNull(numArrows+1)?.let {
                        out[numArrows] = it + 1
                    } ?: run {
                        while (out.size < numArrows + 2) {
                            out.add(0.0)
                        }
                        out.add(1.0)
                    }
                    normalized(out.toDoubleArray())
                } else {
                    var f = other.floor().toNumber().toInt()
                    var r = arrow(arrows - 1, other - f)
                    var i = 0
                    while (f != 0 && r < limit2 && i < 100) {
                        if (f > 0) {
                            r = arrow(arrows - 1, r)
                            f--
                        }
                        i++
                    }
                    if (i == 100) {
                        f = 0
                    }
                    val out = r.data.toMutableList()
                    out.getOrNull(numArrows)?.let {
                        out[numArrows] = it + f
                    } ?: run {
                        while (out.size < numArrows) {
                            out.add(0.0)
                        }
                        out.add(f.toDouble())
                    }

                    normalized(out.toDoubleArray())
                }
            }
        }
    }

    // Hyper-E notation
    override fun toString(): String {
        return toString(3)
    }

    fun toString(precision: Int): String {
        if (sign == -1) {
            return "-" + abs().toString()
        }

        if (isNaN()) {
            return "NaN"
        }

        if (isInfinite()) {
            return "Infinity"
        }

        val log = log10()
        val expPrecision = 10.0.pow(precision)

        return when {
            this < 100000000000 -> {
                // 123456789
                this.toNumber().toString()
            }

            log < 1000000 -> {
                // 1.234e56789
                var mantissa = 10.0.pow(data[1] - floor(data[1]))
                mantissa = round(mantissa * expPrecision) / expPrecision
                val exponent = floor(data[1])
                 "${mantissa}e${exponent.toLong()}"
            }

            log < BigNum.TEN.pow(1000000.0) -> {
                // e1.234e56789
                var mantissa = 10.0.pow(data[1] - floor(data[1]))
                mantissa = round(mantissa * expPrecision) / expPrecision
                val exponent = floor(data[1])
                "e${mantissa}e${exponent.toLong()}"
            }

            data.size == 3 && data[2] == 3.0 -> {
                // ee1.234e56789
                var mantissa = 10.0.pow(data[1] - floor(data[1]))
                mantissa = round(mantissa * expPrecision) / expPrecision
                val exponent = floor(data[1])
                "ee${mantissa}e${exponent.toLong()}"
            }

            data.size == 3 && data[2] <= 8 -> {
                // eeeeeeee56789
                val exponent = floor(data[1])
                return "${"e".repeat(data[2].toInt())}${exponent.toLong()}"
            }

            data.size < 9 -> {
                // e12#34#56#78
                val sb = StringBuilder()
                sb.append('e')
                val n = round(data[1] * expPrecision) / expPrecision
                if (n > 1e6) {
                    val exponent = floor(log10(n))
                    var mantissa = n / (10.0.pow(exponent))
                    mantissa = round(mantissa * expPrecision) / expPrecision
                    sb.append("(${exponent.toLong()}e$mantissa)")
                } else if (n < 1) {
                    sb.append("1")
                } else {
                    sb.append(n.toLong().toString())
                }

                for (i in 3 until data.size) {
                    sb.append("#${data[i].toLong()+1}")
                }

                sb.toString()
            }

            else -> {
                // e12#34##5678
                val sb = StringBuilder()
                sb.append('e')
                val n = round(data[1] * expPrecision) / expPrecision
                if (n > 1e6) {
                    val exponent = floor(log10(n))
                    var mantissa = n / (10.0.pow(exponent))
                    mantissa = round(mantissa * expPrecision) / expPrecision
                    sb.append("(${exponent.toLong()}e$mantissa)")
                } else if (n < 1) {
                    sb.append("1")
                } else {
                    sb.append(n.toString())
                }

                sb.append("#${data.last().toLong()}##${data.size - 2}")

                sb.toString()
            }
        }
    }

    companion object {
        val ZERO = BigNum(doubleArrayOf(1.0, 0.0))
        val ONE = BigNum(doubleArrayOf(1.0, 1.0))
        val TEN = BigNum(doubleArrayOf(1.0, 10.0))
        val NaN = BigNum(doubleArrayOf(1.0, Double.NaN))
        val POSITIVE_INFINITY = BigNum(doubleArrayOf(1.0, Double.POSITIVE_INFINITY))
        val NEGATIVE_INFINITY = BigNum(doubleArrayOf(-1.0, Double.POSITIVE_INFINITY))

        // -- CONFIG --
        private const val MAX_DISP_INTEGER = 1000000.0
        // X where X+1 is highest possible number
        private const val MAX_SAFE_INTEGER = Long.MAX_VALUE - 1
        // 10^MAX_SAFE_INTEGER
        private val E_MAX_SAFE_INTEGER = BigNum(doubleArrayOf(1.0, 15.954589770191, 2.0))
        // 10^10^MAX_SAFE_INTEGER
        private val EE_MAX_SAFE_INTEGER = BigNum(doubleArrayOf(1.0, 15.954589770191, 3.0))
        // 10^^MAX_SAFE_INTEGER
        private val TETRATED_MAX_SAFE_INTEGER = BigNum(doubleArrayOf(1.0, 10.0, 9007199254740981.0))
        // 10^^^MAX_SAFE_INTEGER
        private val ARROW3_MAX_SAFE_INTEGER = BigNum(doubleArrayOf(1.0, 10.0, 9.0, 89.0))

        private fun normalized(arr: DoubleArray): BigNum {
            val out = arr.toMutableList()

            if (out.size == 2 && out.last() == 0.0) {
                out[0] = 1.0
            }

            // Normalize indices
            for (i in out.lastIndex downTo 1) {
                val exp = out[i]

                if (exp.isNaN()) {
                    return BigNum.NaN
                }

                if (exp.isInfinite()) {
                    return if (exp == Double.NEGATIVE_INFINITY) BigNum.NEGATIVE_INFINITY else BigNum.POSITIVE_INFINITY
                }

                if (i != 1) {
                    out[i] = floor(exp)
                }
            }

            do {
                var loop = false

                // Remove trailing 0s
                while (out.size > 2 && out.last() == 0.0) {
                    out.removeLast()
                    loop = true
                }

                // Start setting exponents after max disp
                if (out[1] > MAX_DISP_INTEGER) {
                    out[1] = log10(out[1])
                    out.getOrNull(2)?.let {
                        out[2] = it + 1
                    } ?: run {
                        out.add(1.0)
                    }
                    loop = true
                }

                // Reduce exponent of stacks if root is low
                while (out[1] < log10(MAX_DISP_INTEGER) && out.getOrElse(2) { 0.0 } != 0.0) {
                    out[1] = 10.0.pow(out[1])
                    out[2] = out[2] - 1
                    loop = true
                }

                // Work around max numbers
                for (i in 1 until out.size) {
                    if (out[i] > MAX_SAFE_INTEGER) {
                        out.getOrNull(i + 1)?.let {
                            out[i + 1] = it + 1
                        } ?: run {
                            out.add(1.0)
                        }
                        out[1] = out[i] + 1
                        for (j in 2 .. i) {
                            out[j] = 0.0
                        }
                        loop = true
                    }
                }
            } while(loop)

            return BigNum(out.toDoubleArray())
        }

        operator fun invoke(value: Number): BigNum {
            val asDouble = value.toDouble()
            return normalized(doubleArrayOf(asDouble.sign, asDouble.absoluteValue))
        }
    }
}
