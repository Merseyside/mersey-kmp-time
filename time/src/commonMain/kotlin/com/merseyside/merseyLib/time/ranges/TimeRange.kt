package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.TimeUnit
import com.merseyside.merseyLib.time.ext.getHumanDate
import com.merseyside.merseyLib.time.minus

interface TimeRange : Comparable<TimeRange> {
    val start: TimeUnit
    val end: TimeUnit

    @Throws(IllegalArgumentException::class)
    fun requireValid() {
        if (start > end)
            throw IllegalArgumentException("Start value ${start.getHumanDate()} must be less than end value ${end.getHumanDate()}")
    }

    override fun compareTo(other: TimeRange): Int {
        return start.compareTo(other.start)
    }
}
