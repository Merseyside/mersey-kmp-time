package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.units.Days
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.minus
import com.merseyside.merseyLib.time.units.plus
import com.merseyside.merseyLib.time.ranges.MonthRange
import com.merseyside.merseyLib.time.ranges.TimeRange

fun MonthRange.getNextMonth(): MonthRange {
    return end.toMonthRange()
}

fun MonthRange.getPrevMonth(): MonthRange {
    return (start - Days(1)).toMonthRange()
}

fun MonthRange.getFirstDay(): TimeRange {
    return start.toDayTimeRange()
}

fun MonthRange.getLastDay(): TimeRange {
    return (end - Days(1)).toDayTimeRange()
}

fun MonthRange.getDay(number: Int): TimeRange {
    val timeUnit = start + Days(number - 1)
    return if (contains(timeUnit)) timeUnit.toDayTimeRange()
    else throw IllegalArgumentException("Month has only ${getMonth().days} days.")
}

fun MonthRange.isIntersect(other: TimeRange, includeLastMilli: Boolean = false): Boolean {
    return (this as TimeRange).isIntersect(other, includeLastMilli)
}

fun MonthRange.contains(other: TimeRange, includeLastMilli: Boolean = false): Boolean {
    return (this as TimeRange).contains(other, includeLastMilli)
}

fun MonthRange.contains(timeUnit: TimeUnit, includeLastMilli: Boolean = false): Boolean {
    return (this as TimeRange).contains(timeUnit, includeLastMilli)
}