package com.merseyside.merseyLib.time

import com.merseyside.merseyLib.kotlin.serialization.deserialize
import com.merseyside.merseyLib.kotlin.serialization.serialize
import com.merseyside.merseyLib.time.ranges.MonthRange
import com.merseyside.merseyLib.time.ranges.TimeUnitRange
import com.merseyside.merseyLib.time.ranges.WeekRange
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class LongAsMillisSerializer : KSerializer<Millis> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "com.merseyside.merseyLib.time.LongAsMillisSerializer",
        PrimitiveKind.LONG
    )

    override fun deserialize(decoder: Decoder): Millis {
        val value = decoder.decodeLong()
        return Millis(value)
    }

    override fun serialize(encoder: Encoder, value: Millis) {
        encoder.encodeLong(value.value)
    }
}

class LongAsSecondsSerializer : KSerializer<Seconds> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "com.merseyside.merseyLib.time.LongAsSecondsSerializer",
        PrimitiveKind.LONG
    )

    override fun deserialize(decoder: Decoder): Seconds {
        val value = decoder.decodeLong()
        return Seconds(value)
    }

    override fun serialize(encoder: Encoder, value: Seconds) {
        encoder.encodeLong(value.value)
    }
}

class LongAsMinutesSerializer : KSerializer<Minutes> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "com.merseyside.merseyLib.time.LongAsMinutesSerializer",
            PrimitiveKind.LONG
        )

    override fun deserialize(decoder: Decoder): Minutes {
        val value = decoder.decodeLong()
        return Minutes(value)
    }

    override fun serialize(encoder: Encoder, value: Minutes) {
        encoder.encodeLong(value.value)
    }
}

class LongAsHoursSerializer : KSerializer<Hours> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "com.merseyside.merseyLib.time.LongAsHoursSerializer",
            PrimitiveKind.LONG
        )

    override fun deserialize(decoder: Decoder): Hours {
        val value = decoder.decodeLong()
        return Hours(value)
    }

    override fun serialize(encoder: Encoder, value: Hours) {
        encoder.encodeLong(value.value)
    }
}

class LongAsDaysSerializer : KSerializer<Days> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "com.merseyside.merseyLib.time.LongAsDaysSerializer",
            PrimitiveKind.LONG
        )

    override fun deserialize(decoder: Decoder): Days {
        val value = decoder.decodeLong()
        return Days(value)
    }

    override fun serialize(encoder: Encoder, value: Days) {
        encoder.encodeLong(value.value)
    }
}

class LongAsWeeksSerializer : KSerializer<Weeks> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "com.merseyside.merseyLib.time.LongAsWeeksSerializer",
            PrimitiveKind.LONG
        )

    override fun deserialize(decoder: Decoder): Weeks {
        val value = decoder.decodeLong()
        return Weeks(value)
    }

    override fun serialize(encoder: Encoder, value: Weeks) {
        encoder.encodeLong(value.value)
    }
}

class DayOfWeekSerializer : KSerializer<DayOfWeek> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "com.merseyside.merseyLib.time.DayOfWeekSerializer",
            PrimitiveKind.INT
        )

    override fun deserialize(decoder: Decoder): DayOfWeek {
        val value = decoder.decodeInt()
        return DayOfWeek.getByPlatformIndex(value)
    }

    override fun serialize(encoder: Encoder, value: DayOfWeek) {
        encoder.encodeInt(value.toPlatformIndex())
    }
}

internal object TimeUnitRangeAsListSerializer : KSerializer<TimeUnitRange> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "com.merseyside.merseyLib.time.TimeUnitRangeAsListSerializer",
            PrimitiveKind.STRING
        )

    private val serializer: KSerializer<List<String>> =
        ListSerializer(String.serializer())

    override fun serialize(encoder: Encoder, value: TimeUnitRange) {
        with(value) {
            val start = start.serialize()
            val end = end.serialize()

            encoder.encodeSerializableValue(
                serializer,
                listOf(start, end)
            )
        }
    }

    override fun deserialize(decoder: Decoder): TimeUnitRange {
        val list = decoder.decodeSerializableValue(serializer)
        return TimeUnitRange(
            start = list[0].deserialize(),
            end = list[1].deserialize()
        )
    }
}

internal object WeekRangeAsListSerializer : KSerializer<WeekRange> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "com.merseyside.merseyLib.time.WeekRangeAsListSerializer",
            PrimitiveKind.STRING
        )

    private val serializer: KSerializer<List<String>> =
        ListSerializer(String.serializer())

    override fun serialize(encoder: Encoder, value: WeekRange) {
        with(value) {
            val start = start.serialize()
            val end = end.serialize()

            encoder.encodeSerializableValue(
                serializer,
                listOf(start, end)
            )
        }
    }

    override fun deserialize(decoder: Decoder): WeekRange {
        val list = decoder.decodeSerializableValue(serializer)
        return WeekRange(
            start = list[0].deserialize(),
            end = list[1].deserialize()
        )
    }
}

internal object MonthRangeAsListSerializer : KSerializer<MonthRange> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "com.merseyside.merseyLib.time.MonthRangeAsListSerializer",
            PrimitiveKind.STRING
        )

    private val serializer: KSerializer<List<String>> =
        ListSerializer(String.serializer())

    override fun serialize(encoder: Encoder, value: MonthRange) {
        with(value) {
            val start = start.serialize()
            val end = end.serialize()

            encoder.encodeSerializableValue(
                serializer,
                listOf(start, end)
            )
        }
    }

    override fun deserialize(decoder: Decoder): MonthRange {
        val list = decoder.decodeSerializableValue(serializer)
        return MonthRange(
            start = list[0].deserialize(),
            end = list[1].deserialize()
        )
    }
}