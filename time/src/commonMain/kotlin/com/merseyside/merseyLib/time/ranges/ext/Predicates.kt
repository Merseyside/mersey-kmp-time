package com.merseyside.merseyLib.time.ranges.ext

import com.merseyside.merseyLib.time.ext.roundByDivider
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.ranges.TimeRangeImpl
import com.merseyside.merseyLib.time.units.TimeUnit

fun TimeRange.isIntersect(other: TimeRange, includeLastMilli: Boolean = false): Boolean {
    return contains(other.start, includeLastMilli) || contains(other.end, includeLastMilli)
            || other.contains(start, includeLastMilli) || other.contains(end, includeLastMilli)
}

fun TimeRange.contains(other: TimeRange, includeLastMilli: Boolean = true): Boolean {
    return start <= other.start && getEndValue(includeLastMilli) >= other.getEndValue(includeLastMilli)
}

fun TimeRange.contains(timeUnit: TimeUnit, includeLastMilli: Boolean = true): Boolean {
    return timeUnit in start..getEndValue(includeLastMilli)
}

fun TimeRange.roundByDivider(divider: TimeUnit): TimeRange {
    return TimeRangeImpl(start.roundByDivider(divider), end.roundByDivider(divider))
}