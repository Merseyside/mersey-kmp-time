package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.units.Month
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.ext.getNextMonth
import com.merseyside.merseyLib.time.ext.getPrevMonth
import com.merseyside.merseyLib.time.ext.toMonth
import kotlinx.serialization.Serializable

@Serializable
class MonthRange internal constructor(
    override val start: TimeUnit,
    override val end: TimeUnit
): TimeRange {

    fun getStartMonth(): Month {
        return start.toMonth()
    }

    fun getEndMonth(): Month {
        return end.toMonth()
    }
}

operator fun MonthRange.plus(count: Int): MonthRange {
    var month = this
    for (i in 1..count) month = month.getNextMonth()
    return month
}

operator fun MonthRange.minus(count: Int): MonthRange {
    var month = this
    for (i in 1..count) month = month.getPrevMonth()
    return month
}

operator fun MonthRange.inc(): MonthRange = this.getNextMonth()
operator fun MonthRange.dec(): MonthRange = this.getPrevMonth()