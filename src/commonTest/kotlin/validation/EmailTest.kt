package validation

import com.martmists.multiplatform.validation.Email
import com.martmists.multiplatform.validation.UnsafeSkipValidation
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.fail


class EmailTest {
    @OptIn(UnsafeSkipValidation::class)
    @JsName("skipValidation")
    @Test
    fun `Skip Validation`() {
        Email.fromValid("invalid")
    }

    @JsName("invalidEmail")
    @Test
    fun `Invalid Email`() {
        for (email in arrayOf(
            "plainaddress",
            "#@%^%#$@#$@#.com",
            "@example.com",
            "email.example.com",
            "email@example@example.com",
            ".email@example.com",
            "email.@example.com",
            "email..email@example.com",
            "あいうえお@example.com",
            "email@-example.com",
            "email@111.222.333.44444",
            "email@example..com",
            "Abc..123@example.com",
            // Valid RFC 6531, invalid RFC 5322
            "much.”more\\ unusual”@example.com",
            "very.unusual.”@”.unusual.com@example.com",
            "very.”(),:;<>[]”.VERY.”very@\\\\ \"very”.unusual@strange.example.com",
        )) {
            assertFails(email) {
                Email(email)
            }
        }
    }

    @JsName("validEmail")
    @Test
    fun `Valid Email`() {
        for (email in arrayOf(
            "email@example.com",
            "firstname.lastname@example.com",
            "email@subdomain.example.com",
            "firstname+lastname@example.com",
            "email@123.123.123.123",
            "email@[123.123.123.123]",
            "\"email\"@example.com",
            "1234567890@example.com",
            "email@example-one.com",
            "_______@example.com",
            "email@example.name",
            "email@example.museum",
            "email@example.co.jp",
            "firstname-lastname@example.com",
            // Invalid 5321, valid 5322
            "Joe Smith <email@example.com>",
            "email@example.com (Joe Smith)",
            "sample@[12:f::192.142.15.2]"
        )) {
            try {
                Email(email)
            } catch (e: Throwable) {
                fail(email, e)
            }
        }
    }
}
