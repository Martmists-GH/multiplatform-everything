package com.martmists.multiplatform.validation

import com.martmists.multiplatform.validation.lexer.URILexer
import com.martmists.multiplatform.validation.serialization.URISerializer
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

/**
 * Represents an RFC 3986 URI.
 *
 * Implementation details:
 * - Does not support URI references.
 * - Rejects numeric-only hostnames that are not valid IPv4 addresses.
 */
@JvmInline
@Serializable(with = URISerializer::class)
value class URI private constructor(private val value: String) {
    fun validate() {
        URILexer(value).consumeURI()
    }

    override fun toString() = value

    companion object {
        @JvmStatic
        operator fun invoke(value: String): URI {
            return URI(value).also { it.validate() }
        }

        @JvmStatic
        @UnsafeSkipValidation
        fun fromValid(email: String): URI {
            return URI(email)
        }
    }
}
