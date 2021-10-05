package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.TimeUnit
import com.merseyside.merseyLib.time.ext.contains
import com.merseyside.merseyLib.time.ext.getHumanDate

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

    companion object {
        fun empty(): TimeRange {
            return TimeUnitRange(TimeUnit.getEmpty(), TimeUnit.getEmpty())
        }
    }
}

operator fun <T: TimeUnit> TimeRange.compareTo(timeUnit: T): Int {
    return when {
        contains(timeUnit) -> 0
        start > timeUnit -> 1
        else -> -1
    }
}
