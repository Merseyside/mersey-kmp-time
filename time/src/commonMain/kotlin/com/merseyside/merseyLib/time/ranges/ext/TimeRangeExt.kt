package com.merseyside.merseyLib.time.ranges.ext

import com.merseyside.merseyLib.time.ext.excludeMilli
import com.merseyside.merseyLib.time.ext.includeMilli
import com.merseyside.merseyLib.time.ext.toHoursMinutesOfDay
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.ranges.TimeRangeImpl
import com.merseyside.merseyLib.time.ranges.undefined.UndefinedTimeRange
import com.merseyside.merseyLib.time.units.Day
import com.merseyside.merseyLib.time.units.TimeUnit

inline fun TimeRange.checkUndefined(undefined: () -> Unit = {
    throw UnsupportedOperationException("Undefined time range doesn't support that operation")
}) {
    if (this is UndefinedTimeRange) undefined()
}

fun TimeRange.isEmpty(): Boolean {
    return start.isEmpty() && end.isEmpty()
}

@Throws(IllegalArgumentException::class, UnsupportedOperationException::class)
fun TimeRange.toHoursMinutesOfDay(): TimeRange {
    checkUndefined()
    check(getGap() <= Day) {
        throw IllegalArgumentException("Range is too big!")
    }

    val start = start.toHoursMinutesOfDay()
    var end = end.toHoursMinutesOfDay()

    if (end.isEmpty()) {
       end = Day.excludeMilli()
    }

    return TimeRangeImpl(start, end)
}

fun TimeRange.getEndValue(includeLastMilli: Boolean = true): TimeUnit {
    return end.includeMilli(includeLastMilli)
}

fun TimeRange.excludeMilli(): TimeRange {
    return TimeRangeImpl(start, end.excludeMilli())
}

internal fun TimeRange.checkIntersection(other: TimeRange) {
    if (!isIntersect(other)) throw IllegalArgumentException("Ranges don't intersect!")
}