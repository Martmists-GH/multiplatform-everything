package com.martmists.multiplatform.validation

import com.martmists.multiplatform.validation.lexer.EmailLexer
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

/**
 * Represents an RFC 5322 email address.
 * This does NOT support UTF-8 as specified in RFC 6531.
 *
 * Implementation details:
 * - Supports obsolete syntax.
 * - Supports (nested) comments.
 * - Supports quoted strings.
 * - Validates IPv4 and IPv6 addresses.
 * - Validates domain names according to RFC 1035, see [Domain].
 * - The `validateAddressOnly` parameter can be used to validate only the `addr-spec` rule,
 *   otherwise the `address` rule is validated.
 * - The `permitComments` parameter can be used to allow comments in the email address.
 */
@JvmInline
value class Email private constructor(private val value: String) {
    fun validate(addressOnly: Boolean = false, permitComments: Boolean = true) {
        val lex = EmailLexer(value, !permitComments)
        val result = if (addressOnly) {
            lex.consumeAddrSpec()
        } else {
            lex.consumeAddress()
            lex.consumeEOF()
        }
        if (!permitComments) {
            require(result == value) { "Email address contains comments." }
        }
    }

    override fun toString() = value

    companion object {
        @JvmStatic
        operator fun invoke(value: String, validateAddressOnly: Boolean = false, permitComments: Boolean = true): Email {
            return Email(value).also { it.validate(validateAddressOnly, permitComments) }
        }

        @JvmStatic
        @UnsafeSkipValidation
        fun fromValid(email: String): Email {
            return Email(email)
        }
    }
}
