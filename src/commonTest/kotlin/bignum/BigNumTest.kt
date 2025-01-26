package bignum

import com.martmists.multiplatform.bignum.BigNum
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class BigNumTest {
    @JsName("toStringTest")
    @Test
    fun `toString valid`() {
        val num = Double.MAX_VALUE
        val res = BigNum(num).pow(BigNum(num))
        assertEquals("e5.541e310", res.toString())
    }

    @JsName("grahamTest")
    @Test
    fun `Grahams Number Series`() {
        val three = BigNum(3)
        val g1 = three.arrow(4, 3)
        val g2 = three.arrow(g1, three)

        assertEquals("e12#1#1#2", g1.toString())
        // Unable to handle larger numbers due to memory constraints
        assertEquals("Infinity", g2.toString())
    }
}
