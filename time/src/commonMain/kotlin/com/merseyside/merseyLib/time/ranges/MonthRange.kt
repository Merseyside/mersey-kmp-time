package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.Month
import com.merseyside.merseyLib.time.TimeUnit
import com.merseyside.merseyLib.time.ext.getNextMonth
import com.merseyside.merseyLib.time.ext.getPrevMonth
import com.merseyside.merseyLib.time.ext.toMonth

class MonthRange internal constructor(
    private val start: TimeUnit,
    private val end: TimeUnit
): TimeRange {

    override fun getStart() = start
    override fun getEnd() = end

    fun getMonth(): Month {
        return start.toMonth()
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