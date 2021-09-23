package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.TimeUnit
import com.merseyside.merseyLib.time.ext.getNextWeek
import com.merseyside.merseyLib.time.ext.getPrevWeek

class WeekRange internal constructor(
    override val start: TimeUnit,
    override val end: TimeUnit
): TimeRange {

    internal constructor(timeRange: TimeRange) : this(timeRange.start, timeRange.end)
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