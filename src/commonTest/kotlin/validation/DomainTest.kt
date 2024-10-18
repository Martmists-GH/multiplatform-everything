package validation

import com.martmists.multiplatform.validation.Domain
import com.martmists.multiplatform.validation.UnsafeSkipValidation
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.fail

class DomainTest {
    @OptIn(UnsafeSkipValidation::class)
    @JsName("skipValidation")
    @Test
    fun `Skip Validation`() {
        Domain.fromValid("10")
    }

    @JsName("invalidDomain")
    @Test
    fun `Invalid Domain`() {
        assertFails {
            require(Domain("10").toString() == "10")
        }
    }

    @JsName("validDomain")
    @Test
    fun `Valid Domain`() {
        try {
            Domain("example-one.com").toString()
        } catch (e: Exception) {
            fail("example-one.com", e)
        }
    }
}
