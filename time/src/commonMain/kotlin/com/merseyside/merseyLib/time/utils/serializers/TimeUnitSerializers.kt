package com.merseyside.merseyLib.time.utils.serializers

import com.merseyside.merseyLib.time.units.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class LongAsMillisSerializer : KSerializer<Millis> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "com.merseyside.merseyLib.time.utils.serializers.LongAsMillisSerializer",
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
        "com.merseyside.merseyLib.time.utils.serializers.LongAsSecondsSerializer",
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
            "com.merseyside.merseyLib.time.utils.serializers.LongAsMinutesSerializer",
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
            "com.merseyside.merseyLib.time.utils.serializers.LongAsHoursSerializer",
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
            "com.merseyside.merseyLib.time.utils.serializers.LongAsDaysSerializer",
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
            "com.merseyside.merseyLib.time.utils.serializers.LongAsWeeksSerializer",
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
            "com.merseyside.merseyLib.time.utils.serializers.DayOfWeekSerializer",
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

internal object YearsAsIntSerializer : KSerializer<Years> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "com.merseyside.merseyLib.time.utils.serializers.Years",
            PrimitiveKind.INT
        )

    override fun serialize(encoder: Encoder, value: Years) {
        encoder.encodeInt(value.value)
    }

    override fun deserialize(decoder: Decoder): Years {
        val value = decoder.decodeInt()
        return Years(value)
    }
}