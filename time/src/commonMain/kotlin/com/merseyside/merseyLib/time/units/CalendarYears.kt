package com.merseyside.merseyLib.time.units

import com.merseyside.merseyLib.time.utils.serializers.YearsAsIntSerializer
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Presents calendar years
 * Could only starts from 1970 y.
 */
@JvmInline
@Serializable(with = YearsAsIntSerializer::class)
value class CalendarYears(val value: Int) {
    init {
        if (value < EPOCH_YEAR) throw IllegalArgumentException("Could only starts from 1970")
    }

    companion object {
        private const val EPOCH_YEAR = 1970
    }
}

operator fun CalendarYears.plus(other: CalendarYears): CalendarYears {
    return CalendarYears(value + other.value)
}

operator fun CalendarYears.minus(other: CalendarYears): CalendarYears {
    return CalendarYears(value - other.value)
}

operator fun CalendarYears.inc(): CalendarYears {
    return CalendarYears(value + 1)
}

operator fun CalendarYears.dec(): CalendarYears {
    return CalendarYears(value - 1)
}

operator fun CalendarYears.compareTo(other: CalendarYears): Int {
    return value.compareTo(other.value)
}