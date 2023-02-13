package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.ext.getNextWeek
import com.merseyside.merseyLib.time.ext.getPrevWeek
import kotlinx.serialization.Serializable

/**
 * WeekRange starts from monday (00:00) ends with sunday (23:59)
 */
@Serializable
class WeekRange internal constructor(
    override val start: TimeUnit,
    override val end: TimeUnit
): TimeRange {
    internal constructor(timeRange: TimeRange) : this(timeRange.start, timeRange.end)

    override fun equals(other: Any?): Boolean {
        return if (other is TimeRange) {
            start == other.start && end == other.end
        } else false
    }

    override fun hashCode(): Int {
        var result = start.hashCode()
        result = 31 * result + end.hashCode()
        return result
    }
}

operator fun WeekRange.inc(): WeekRange {
    return getNextWeek()
}

operator fun WeekRange.dec(): WeekRange {
    return getPrevWeek()
}

operator fun WeekRange.plus(count: Int): WeekRange {
    var week = this
    for (i in 1..count) week = week.getNextWeek()
    return week
}

operator fun WeekRange.minus(count: Int): WeekRange {
    var week = this
    for (i in 1..count) week = week.getPrevWeek()
    return week
}