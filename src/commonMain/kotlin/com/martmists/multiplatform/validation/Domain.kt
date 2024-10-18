package com.martmists.multiplatform.validation

import com.martmists.multiplatform.validation.lexer.DomainLexer
import com.martmists.multiplatform.validation.serialization.DomainSerializer
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

/**
 * Represents an RFC 1035 domain name.
 */
@JvmInline
@Serializable(with = DomainSerializer::class)
value class Domain private constructor(private val value: String) {
    fun validate() {
        DomainLexer(value).consumeDomain()
    }

    override fun toString() = value

    companion object {
        @JvmStatic
        operator fun invoke(value: String): Domain {
            return Domain(value).also { it.validate() }
        }

        @JvmStatic
        @UnsafeSkipValidation
        fun fromValid(email: String): Domain {
            return Domain(email)
        }
    }
}
