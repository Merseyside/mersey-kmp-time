package com.merseyside.merseyLib.time.utils.serializers

import com.merseyside.merseyLib.time.*
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

internal object IsoInstantTimeUnitSerializer : KSerializer<TimeUnit> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "com.merseyside.merseyLib.time.utils.serializers.IsoInstantTimeUnitSerializer",
            PrimitiveKind.STRING
        )

    override fun serialize(encoder: Encoder, value: TimeUnit) {
        encoder.encodeString(value.toFormattedDate(Pattern.ISO_INSTANT).date)
    }

    override fun deserialize(decoder: Decoder): TimeUnit {
        val value = decoder.decodeString()
        return PatternedFormattedDate.of(value, Pattern.ISO_INSTANT).toTimeUnit()
    }
}