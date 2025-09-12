package com.merseyside.merseyLib.time.ranges.ext

import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.ranges.TimeRangeImpl
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.minus
import com.merseyside.merseyLib.time.units.plus

/**
 * Returns gap between ranges
 */
fun TimeRange.getGap(): TimeUnit {
    return end - start
}

fun TimeRange.shift(timeUnit: TimeUnit): TimeRange {
    return TimeRangeImpl(start + timeUnit, end + timeUnit)
}

fun TimeRange.shiftBack(timeUnit: TimeUnit): TimeRange {
    return TimeRangeImpl(start - timeUnit, end - timeUnit)
}

fun TimeRange.shiftOnGap(): TimeRange {
    val gap = getGap()
    return TimeRangeImpl(start + gap, end + gap)
}

fun TimeRange.shiftBackOnGap(): TimeRange {
    val gap = getGap()
    return TimeRangeImpl(start - gap, end - gap)
}