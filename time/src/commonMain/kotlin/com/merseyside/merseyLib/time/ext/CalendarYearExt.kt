package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.parseByCalendarUnits
import com.merseyside.merseyLib.time.ranges.MonthRange
import com.merseyside.merseyLib.time.ranges.CalendarYearsRange
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.CalendarYears
import com.merseyside.merseyLib.time.units.inc
import com.merseyside.merseyLib.time.units.plus

fun CalendarYears.isLeap(): Boolean {
    return value % 4 == 0
}

fun CalendarYears.toTimeUnit(): TimeUnit {
    return parseByCalendarUnits(year = value)
}

fun CalendarYears.getDaysCount(): Int {
    return if (isLeap()) 366 else 365
}

/**
 * Creates a range from calendar year from 01-01 to 31-12.
 */
fun CalendarYears.toYearsRange(): CalendarYearsRange {
    val nextYear = inc()
    return CalendarYearsRange(toTimeUnit(), nextYear.toTimeUnit().excludeMilli())
}

fun CalendarYears.toMonthRanges(): List<MonthRange> {
    return toYearsRange().toMonthRanges()
}