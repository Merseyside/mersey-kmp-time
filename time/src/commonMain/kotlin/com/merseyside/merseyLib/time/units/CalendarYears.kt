package com.merseyside.merseyLib.time.units

import com.merseyside.merseyLib.kotlin.logger.log
import com.merseyside.merseyLib.time.utils.serializers.YearsAsIntSerializer
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Presents calendar years
 * Could only starts from 1970 y.
 */
@JvmInline
@Serializable(with = YearsAsIntSerializer::class)
value class CalendarYears(val value: Int = EPOCH_YEAR) : Comparable<CalendarYears> {
    init {
        if (value < EPOCH_YEAR) throw IllegalArgumentException("Could only starts from 1970")
    }

    override fun compareTo(other: CalendarYears): Int {
        return value.compareTo(other.value)
    }

    companion object {
        private const val EPOCH_YEAR = 1970
    }
}

operator fun CalendarYears.plus(other: Int): CalendarYears {
    return CalendarYears(value + other)
}

operator fun CalendarYears.minus(other: Int): CalendarYears {
    return CalendarYears(value - other)
}

operator fun CalendarYears.inc(): CalendarYears {
    return CalendarYears(value + 1)
}

operator fun CalendarYears.dec(): CalendarYears {
    return CalendarYears(value - 1)
}

operator fun CalendarYears.rangeTo(that: CalendarYears) = CalendarYearsRangeProgression(this, that)

class CalendarYearsRangeProgression(
    override val start: CalendarYears,
    override val endInclusive: CalendarYears,
    val stepDays: Int = 1
) : ClosedRange<CalendarYears>, Iterable<CalendarYears> {

    override fun iterator(): Iterator<CalendarYears> {
        return CalendarYearsRangeIterator(start, endInclusive, stepDays)
    }

    infix fun step(days: Int) = CalendarYearsRangeProgression(start, endInclusive, days)
}

class CalendarYearsRangeIterator(
    val start: CalendarYears,
    val endInclusive: CalendarYears,
    private val stepYears: Int
) : Iterator<CalendarYears> {

    private var currentYear: CalendarYears = start

    override fun hasNext(): Boolean {
        return (currentYear + stepYears).log("kek", "start") <= endInclusive.log("kek", "end")
    }

    override fun next(): CalendarYears {
        val next = currentYear
        currentYear += stepYears
        return next
    }
}