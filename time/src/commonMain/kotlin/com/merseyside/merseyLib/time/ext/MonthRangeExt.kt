package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.*
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.ranges.MonthRange

fun MonthRange.getNextMonth(): MonthRange {
    return getEnd().getMonthRange()
}

fun MonthRange.getPrevMonth(): MonthRange {
    return (getStart() - Days(1)).getMonthRange()
}

fun MonthRange.getFirstDay(): TimeRange {
    return getStart().toDayTimeRange()
}

fun MonthRange.getLastDay(): TimeRange {
    return (getEnd() - Days(1)).toDayTimeRange()
}

fun MonthRange.getDay(number: Int): TimeRange {
    val timeUnit = getStart() + Days(number - 1)
    return if (contains(timeUnit)) timeUnit.toDayTimeRange()
    else throw IllegalArgumentException("Month has only ${getMonth().days} days.")
}