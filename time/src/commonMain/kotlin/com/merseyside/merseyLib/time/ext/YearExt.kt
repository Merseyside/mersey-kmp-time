package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.parseByCalendarUnits
import com.merseyside.merseyLib.time.ranges.MonthRange
import com.merseyside.merseyLib.time.ranges.YearsRange
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.Years
import com.merseyside.merseyLib.time.units.plus

fun Years.isLeap(): Boolean {
    return value % 4 == 0
}

fun Years.toTimeUnit(): TimeUnit {
    return parseByCalendarUnits(year = value)
}

fun Years.getDaysCount(): Int {
    return if (isLeap()) 366 else 365
}

fun Years.toYearsRange(): YearsRange {
    val nextYear = this + Years(1)
    return YearsRange(toTimeUnit(), nextYear.toTimeUnit().excludeMilli())
}

fun Years.toMonthRanges(): List<MonthRange> {
    return toYearsRange().toMonthRanges()
}