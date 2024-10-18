package com.martmists.multiplatform.validation.serialization

import com.martmists.multiplatform.validation.Email
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Since this takes parameters, it cannot be an object
 * Instead, create and instance and use it as argument to `encodeToString` and `decodeFromString`, OR
 * add it to a SerializersModule.
 */
open class EmailSerializer(private val validateAddressOnly: Boolean, private val permitComments: Boolean) : KSerializer<Email> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Email", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Email) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Email {
        return Email(decoder.decodeString(), validateAddressOnly, permitComments)
    }
}
