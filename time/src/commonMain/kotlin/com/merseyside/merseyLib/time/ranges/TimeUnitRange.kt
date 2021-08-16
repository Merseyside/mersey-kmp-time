package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.TimeUnit

data class TimeUnitRange(
    val startTime: TimeUnit,
    val endTime: TimeUnit
): TimeRange {

    init {
        requireValid()
    }

    override fun getStart() = startTime
    override fun getEnd() = endTime
}
