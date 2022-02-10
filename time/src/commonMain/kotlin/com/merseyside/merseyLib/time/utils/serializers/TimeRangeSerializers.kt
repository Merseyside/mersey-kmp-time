package com.merseyside.merseyLib.time.utils.serializers

import com.merseyside.merseyLib.kotlin.serialization.deserialize
import com.merseyside.merseyLib.kotlin.serialization.serialize
import com.merseyside.merseyLib.time.ranges.MonthRange
import com.merseyside.merseyLib.time.ranges.TimeUnitRange
import com.merseyside.merseyLib.time.ranges.WeekRange
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal object TimeUnitRangeAsListSerializer : KSerializer<TimeUnitRange> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "com.merseyside.merseyLib.time.utils.serializers.TimeUnitRangeAsListSerializer",
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
            "com.merseyside.merseyLib.time.utils.serializers.WeekRangeAsListSerializer",
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
            "com.merseyside.merseyLib.time.utils.serializers.MonthRangeAsListSerializer",
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