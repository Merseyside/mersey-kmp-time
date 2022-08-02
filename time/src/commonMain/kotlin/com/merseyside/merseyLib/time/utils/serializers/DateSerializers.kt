package com.merseyside.merseyLib.time.utils.serializers

import com.merseyside.merseyLib.time.PatternedFormattedDate
import com.merseyside.merseyLib.time.ext.toFormattedDate
import com.merseyside.merseyLib.time.ext.toTimeUnit
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.utils.Pattern
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object IsoDateTimeUnitSerializer : KSerializer<TimeUnit> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "izibook.mobile.kmmlibrary.utils.serializators.IsoDateTimeUnitSerializer",
            PrimitiveKind.STRING
        )

    override fun serialize(encoder: Encoder, value: TimeUnit) {
        encoder.encodeString(value.toFormattedDate(Pattern.ISO_DATE_TIME).date)
    }

    override fun deserialize(decoder: Decoder): TimeUnit {
        val value = decoder.decodeString()
        return PatternedFormattedDate.of(value, Pattern.ISO_DATE_TIME).toTimeUnit()
    }
}