package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.ext.*
import com.merseyside.merseyLib.time.units.*

/**
 * Presents only ONE calendar year with start and end TimeUnits
 */
class CalendarYearsRange internal constructor(
    override val start: TimeUnit,
    override val end: TimeUnit
): TimeRange {

    init {
        requireValid()
    }

    companion object {
        fun from(calendarYears: CalendarYears): CalendarYearsRange {
            return calendarYears.toYearsRange()
        }

        fun from(yearsInt: Int): CalendarYearsRange {
            return from(CalendarYears(yearsInt))
        }

        fun getYearsRanges(from: Int, to: Int): List<CalendarYearsRange> {
            return (from..to).map { year -> from(year) }
        }

        fun getYearsRanges(from: CalendarYears, to: CalendarYears): List<CalendarYearsRange> {
            return ((from.value)..(to.value)).map { year -> from(year) }
        }
    }
}

operator fun CalendarYearsRange.plus(calendarYears: CalendarYears): TimeRange {
    return TimeUnitRange(start, end + calendarYears.toTimeUnit())
}

operator fun CalendarYearsRange.minus(calendarYears: CalendarYears): TimeRange {
    return TimeUnitRange(start, end - calendarYears.toTimeUnit())
}

operator fun CalendarYearsRange.inc(): CalendarYearsRange {
    val year = toYears().value + 1
    return CalendarYearsRange.from(year)
}

operator fun CalendarYearsRange.dec(): CalendarYearsRange {
    val year = toYears().value - 1
    return CalendarYearsRange.from(year)
}