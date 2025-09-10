package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.exception.TimeInitializeException
import com.merseyside.merseyLib.time.ext.getGap
import com.merseyside.merseyLib.time.ext.getHumanDate
import com.merseyside.merseyLib.time.units.compareTo
import com.merseyside.merseyLib.time.units.unaryMinus

interface TimeRange : Comparable<TimeRange> {
    val start: TimeUnit
    val end: TimeUnit

    @Throws(IllegalArgumentException::class)
    fun requireValid() {
        check(start < end) {
            throw TimeInitializeException(
                "Start value ${start.getHumanDate()} must be less" +
                        " than end value ${end.getHumanDate()}"
            )
        }

        check(start >= 0 || start == -TimeUnit.UNDEFINED) {
            throw TimeInitializeException(
                "Start value ${start.getHumanDate()} must be positive or equal to UNDEFINED"
            )
        }
    }

    override fun compareTo(other: TimeRange): Int {
        return getGap().compareTo(other.getGap())
    }

    companion object {
        fun empty(): TimeRange {
            return TimeUnitRange(TimeUnit.empty(), TimeUnit.empty())
        }
    }
}