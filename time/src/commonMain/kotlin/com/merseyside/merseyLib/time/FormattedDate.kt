package com.merseyside.merseyLib.time

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = StringAsFormattedDateSerializer::class)
open class FormattedDate (val date: String) {

    override fun toString(): String {
        return date
    }

    companion object {
        fun empty(): FormattedDate = FormattedDate("")
    }
}

fun FormattedDate.isEmpty() = date.isEmpty()

class StringAsFormattedDateSerializer : KSerializer<FormattedDate> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "com.merseyside.merseyLib.time.StringAsFormattedDateSerializer",
            PrimitiveKind.STRING
        )

    override fun deserialize(decoder: Decoder): FormattedDate {
        return FormattedDate(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: FormattedDate) {
        encoder.encodeString(value.date)
    }
}