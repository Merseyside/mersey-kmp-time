package com.merseyside.merseyLib.time.units

import com.merseyside.merseyLib.time.utils.serializers.YearsAsIntSerializer
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable(with = YearsAsIntSerializer::class)
value class Years(val value: Int)

operator fun Years.plus(other: Years): Years {
    return Years(value + other.value)
}

operator fun Years.minus(other: Years): Years {
    return Years(value - other.value)
}

operator fun Years.inc(): Years {
    return Years(value + 1)
}

operator fun Years.dec(): Years {
    return Years(value - 1)
}

operator fun Years.compareTo(other: Years): Int {
    return value.compareTo(other.value)
}