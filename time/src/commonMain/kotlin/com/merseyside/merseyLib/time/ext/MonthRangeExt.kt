package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.ranges.MonthRange
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.units.*
import com.merseyside.merseyLib.time.getMonth as internalGetMonth

fun MonthRange.getMonth(): Month {
    return internalGetMonth(start)
}

fun MonthRange.getNextMonth(): MonthRange {
    return end.addMilli().toMonthRange()
}

fun MonthRange.getPrevMonth(): MonthRange {
    return start.excludeMilli().toMonthRange()
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

/**
 * Adds lacking days in the start of range.
 * For example, month starts with thursday then we add monday..thursday time range to the start.
 */
fun MonthRange.supplementToCalendarMonth(): TimeRange {
    var newRange: TimeRange = this
    val startOfMonth = start
    val startWeekRange = startOfMonth.toWeekRange()


    if (startOfMonth != startWeekRange.start) {
        newRange = newRange.uniteWith(startWeekRange)
    }

    val endOfMonth = end
    val endWeekRange = endOfMonth.toWeekRange()
    if (endOfMonth != endWeekRange.end) {
        newRange = newRange.uniteWith(endWeekRange)
    }

    return newRange
}