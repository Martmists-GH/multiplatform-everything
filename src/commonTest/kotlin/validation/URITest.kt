package validation

import com.martmists.multiplatform.validation.URI
import com.martmists.multiplatform.validation.UnsafeSkipValidation
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertFails


class URITest {
    @OptIn(UnsafeSkipValidation::class)
    @JsName("skipValidation")
    @Test
    fun `Skip Validation`() {
        URI.fromValid("invalid")
    }

    @JsName("invalidURI")
    @Test
    fun `Invalid URI`() {
        for (uri in arrayOf(
            // Bad IPv4
            "http://1.22.333.4",
            // Bad IPv6
            "http://[::1",
            // Space in urn
            "urn:isbn: 0-486-27557-4",
        )) {
            assertFails(uri) {
                URI(uri)
            }
        }
    }
}
