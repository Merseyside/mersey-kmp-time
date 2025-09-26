package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.exception.TimeInitializeException
import com.merseyside.merseyLib.time.ext.getHumanDate
import com.merseyside.merseyLib.time.ranges.ext.getGap
import com.merseyside.merseyLib.time.ranges.ext.toHumanString
import com.merseyside.merseyLib.time.ranges.week.WeekRange
import com.merseyside.merseyLib.time.utils.Pattern
import kotlinx.serialization.Serializable

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
    }

    override fun compareTo(other: TimeRange): Int {
        return getGap().compareTo(other.getGap())
    }

    companion object {
        fun empty(): TimeRange {
            return TimeRangeImpl(TimeUnit.empty(), TimeUnit.empty())
        }

        fun create(start: TimeUnit, end: TimeUnit): TimeRange {
            return TimeRangeImpl(start, end)
        }
    }
}

@Serializable
internal class TimeRangeImpl(
    override val start: TimeUnit,
    override val end: TimeUnit
): TimeRange {
    constructor(timeRange: TimeRange): this(timeRange.start, timeRange.end)

    init { requireValid() }

    override fun toString(): String {
        return toHumanString(pattern = Pattern.ISO_INSTANT)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TimeRangeImpl) return false

        if (start != other.start) return false
        if (end != other.end) return false

        return true
    }

    override fun hashCode(): Int {
        var result = start.hashCode()
        result = 31 * result + end.hashCode()
        return result
    }
}
