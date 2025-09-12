package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.parseByCalendarUnits
import com.merseyside.merseyLib.time.ranges.ext.splitToMonthRanges
import com.merseyside.merseyLib.time.ranges.month.MonthRange
import com.merseyside.merseyLib.time.ranges.years.CalendarYearsRange
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.CalendarYears
import com.merseyside.merseyLib.time.units.inc

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
    return toYearsRange().splitToMonthRanges()
}