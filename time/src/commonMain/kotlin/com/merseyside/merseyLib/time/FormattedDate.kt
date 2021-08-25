package com.merseyside.merseyLib.time

import com.merseyside.merseyLib.time.ext.toFormattedDate
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = StringAsFormattedDateSerializer::class)
class FormattedDate(val value: String) {

    override fun toString(): String {
        return value
    }

    companion object {
        fun empty(): FormattedDate = FormattedDate("")

        fun from(
            timeUnit: TimeUnit,
            pattern: String = TimeConfiguration.defaultPattern
        ): FormattedDate {
            return timeUnit.toFormattedDate(pattern)
        }
    }
}

fun FormattedDate.isEmpty() = value.isEmpty()

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
        encoder.encodeString(value.value)
    }
}