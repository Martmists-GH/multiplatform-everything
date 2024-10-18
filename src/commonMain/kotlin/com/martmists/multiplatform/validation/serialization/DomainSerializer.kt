package com.martmists.multiplatform.validation.serialization

import com.martmists.multiplatform.validation.Domain
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object DomainSerializer : KSerializer<Domain> {
    override val descriptor = PrimitiveSerialDescriptor("Domain", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Domain) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): Domain {
        return Domain(decoder.decodeString())
    }
}
