package com.merseyside.merseyLib.time.ranges.ext

import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.ranges.TimeRangeImpl
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.ext.max
import com.merseyside.merseyLib.time.units.ext.min

/**
 * Unite two ranges(if they intersect) into one range
 * by setting start value to the most min start value of both ranges
 */
fun TimeRange.uniteWith(other: TimeRange): TimeRange {
    checkIntersection(other)

    val start = min(start, other.start)
    val end = max(end, other.end)

    return TimeRangeImpl(start, end)
}

/**
 * Unite list of ranges into one range by setting start value to the most min start value of all ranges
 * and end to the most max end value of all ranges
 */
fun <T : TimeRange> List<T>.unite(): TimeRange {
    if (isEmpty()) throw IllegalArgumentException("List can not be empty!")
    val mutList = this.toMutableList()

    return if (size > 1) {

        val first = mutList.removeFirst()
        var min: TimeUnit = first.start
        var max: TimeUnit = first.end

        mutList.forEach { timeUnit ->
            timeUnit.start.let { if (it < min) min = it }
            timeUnit.end.let { if (it > max) max = it }
        }

        TimeRangeImpl(min, max)

    } else {
        TimeRangeImpl(first().start, first().end)
    }
}

fun <T : TimeRange> List<T>.unite(block: (TimeRange) -> T): T {
    val range = unite()
    return block(range)
}

fun TimeRange.intersect(other: TimeRange, includeLastMilli: Boolean = true): TimeRange? {
    return if (isIntersect(other, includeLastMilli)) {
        when {
            contains(other, includeLastMilli) -> other
            other.contains(this, includeLastMilli) -> this
            else -> {
                val start = if (contains(other.start, includeLastMilli)) other.start else this.start
                val end = if (contains(other.end, includeLastMilli)) other.end else this.end

                TimeRangeImpl(start, end)
            }
        }
    } else null
}