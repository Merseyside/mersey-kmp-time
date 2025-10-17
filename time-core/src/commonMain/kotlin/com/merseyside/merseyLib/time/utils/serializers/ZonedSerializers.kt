package com.merseyside.merseyLib.time.utils.serializers

import com.merseyside.merseyLib.kotlin.utils.safeLet
import com.merseyside.merseyLib.time.ext.toFormattedDate
import com.merseyside.merseyLib.time.format.FormattedDate
import com.merseyside.merseyLib.time.format.ext.toTimeUnit
import com.merseyside.merseyLib.time.ranges.undefined.Undefined
import com.merseyside.merseyLib.time.ranges.undefined.UndefinedTimeRange
import com.merseyside.merseyLib.time.ranges.zone.ZonedTimeRange
import com.merseyside.merseyLib.time.ranges.zone.ext.toServerTimeZone
import com.merseyside.merseyLib.time.utils.Pattern
import com.merseyside.merseyLib.time.zone.TimeZone
import com.merseyside.merseyLib.time.zone.ZonedTimeUnit
import com.merseyside.merseyLib.time.zone.ext.toFormattedDate
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.nullable
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

class TimeZoneAsStringSerializer : KSerializer<TimeZone> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "com.merseyside.merseyLib.time.utils.serializers.TimeZoneAsStringSerializer",
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

class ServerZonedInstantSerializer : KSerializer<ZonedTimeUnit> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "com.merseyside.merseyLib.time.utils.ServerZonedInstantSerializer",
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


/**
 * Formats json array with two strings: f.e ["22:22+03:00", "23:23+03:00"]
 */
class ServerZonedTimeRangeAsArraySerializer : KSerializer<ZonedTimeRange> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "com.merseyside.merseyLib.time.utils.serializers.ServerZonedTimeRangeAsArraySerializer",
            PrimitiveKind.STRING
        )

    private val serializer: KSerializer<List<String?>> =
        ListSerializer(String.serializer().nullable)

    override fun serialize(encoder: Encoder, value: ZonedTimeRange) {
        val start: String?
        val end: String?

        val serverZonedRange = value.toServerTimeZone()

        with(serverZonedRange) {
            if (this is UndefinedTimeRange) {
                start = if (startZoned.gmtTimeUnit is Undefined) null
                else startZoned.localTimeUnit.toFormattedDate().date

                end = if (endZoned.gmtTimeUnit is Undefined) null
                else endZoned.localTimeUnit.toFormattedDate().date
            } else {
                start = startZoned.localTimeUnit.toFormattedDate().date
                end = endZoned.localTimeUnit.toFormattedDate().date
            }
        }

        encoder.encodeSerializableValue(serializer, listOf(start, end))
    }

    override fun deserialize(decoder: Decoder): ZonedTimeRange {
        val list = decoder.decodeSerializableValue(serializer)
        val startStr = list[0]
        val endStr = list[1]

        return safeLet(startStr, endStr) { start, end ->
            val start = FormattedDate(start).toTimeUnit()
            val end = FormattedDate(end).toTimeUnit()

            ZonedTimeRange.create(
                startZoned = ZonedTimeUnit.withServerTimeZone(start),
                endZoned = ZonedTimeUnit.withServerTimeZone(end)
            )
        } ?: run {
            if (startStr == null && endStr != null) ZonedTimeRange.createWithEnd(
                ZonedTimeUnit.withServerTimeZone(
                    FormattedDate(endStr).toTimeUnit()
                )
            ) else if (endStr == null && startStr != null) ZonedTimeRange.createWithStart(
                ZonedTimeUnit.withServerTimeZone(
                    FormattedDate(startStr).toTimeUnit()
                )
            ) else throw IllegalArgumentException("Both ranges are null!")
        }
    }
}