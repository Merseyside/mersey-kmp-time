package com.merseyside.merseyLib.time.utils.serializers

import com.merseyside.merseyLib.time.FormattedDate
import com.merseyside.merseyLib.time.TimeZone
import com.merseyside.merseyLib.time.ext.toFormattedDate
import com.merseyside.merseyLib.time.ext.toTimeUnit
import com.merseyside.merseyLib.time.ranges.ZonedTimeUnitRange
import com.merseyside.merseyLib.time.units.ZonedTimeUnit
import com.merseyside.merseyLib.time.utils.Pattern
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class IsoOffsetDateTimeSerializer : KSerializer<ZonedTimeUnit> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "com.merseyside.merseyLib.time.utils.ISOOffsetDateTimeSerializer",
        PrimitiveKind.STRING
    )

    override fun deserialize(decoder: Decoder): ZonedTimeUnit {
        val date = decoder.decodeString()
        return ZonedTimeUnit.of(date, Pattern.Offset.ISO_OFFSET_DATE_TIME)
    }

    override fun serialize(encoder: Encoder, value: ZonedTimeUnit) {
        encoder.encodeString(value.toFormattedDate(Pattern.Offset.ISO_OFFSET_DATE_TIME).date)
    }
}

class StringAsTimeZoneSerializer : KSerializer<TimeZone> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "com.merseyside.merseyLib.time.utils.serializers.StringAsTimeZoneSerializer",
            PrimitiveKind.STRING
        )

    override fun deserialize(decoder: Decoder): TimeZone {
        val value = decoder.decodeString()
        return TimeZone.of(value)
    }

    override fun serialize(encoder: Encoder, value: TimeZone) {
        encoder.encodeString(value.zoneId)
    }
}

class StringAsServerTimeZoneSerializer : KSerializer<ZonedTimeUnit> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "com.merseyside.merseyLib.time.utils.StringAsServerTimeZoneSerializer",
        PrimitiveKind.STRING
    )

    override fun deserialize(decoder: Decoder): ZonedTimeUnit {
        val timeUnit = IsoInstantTimeUnitSerializer.deserialize(decoder)
        return ZonedTimeUnit.withServerTimeZone(timeUnit)
    }

    override fun serialize(encoder: Encoder, value: ZonedTimeUnit) {
        val string = value.localTimeUnit.toFormattedDate(Pattern.ISO_INSTANT).date
        encoder.encodeString(string)
    }
}


class StringAsServerTimeZoneRangeSerializer : KSerializer<ZonedTimeUnitRange> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "com.merseyside.merseyLib.time.utils.serializers.StringAsServerTimeZoneRangeSerializer",
            PrimitiveKind.STRING
        )

    private val serializer: KSerializer<List<String>> =
        ListSerializer(String.serializer())

    override fun serialize(encoder: Encoder, value: ZonedTimeUnitRange) {
        with(value) {
            val start = startZoned.localTimeUnit.toFormattedDate().date
            val end = endZoned.localTimeUnit.toFormattedDate().date
            encoder.encodeSerializableValue(
                serializer,
                listOf(start, end)
            )
        }
    }

    override fun deserialize(decoder: Decoder): ZonedTimeUnitRange {
        val list = decoder.decodeSerializableValue(serializer)
        val start = FormattedDate(list[0]).toTimeUnit()
        val end = FormattedDate(list[1]).toTimeUnit()
        return ZonedTimeUnitRange(
            startZoned = ZonedTimeUnit.withServerTimeZone(start),
            endZoned = ZonedTimeUnit.withServerTimeZone(end)
        )
    }
}