package com.merseyside.merseyLib.time.ranges.undefined

import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.Undefined

interface UndefinedTimeRange : TimeRange {

    companion object {
        fun createWithStart(start: TimeUnit): UndefinedTimeRange {
            return UndefinedTimeRangeImpl(start, Undefined.MAX())
        }

        fun createWithEnd(end: TimeUnit): UndefinedTimeRange {
            return UndefinedTimeRangeImpl(Undefined.MIN(), end)
        }
    }
}