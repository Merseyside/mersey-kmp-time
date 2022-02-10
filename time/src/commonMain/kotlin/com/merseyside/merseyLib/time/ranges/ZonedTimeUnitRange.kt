package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.ZonedTimeUnit

data class ZonedTimeUnitRange(
    val startZoned: ZonedTimeUnit,
    val endZoned: ZonedTimeUnit
) : TimeRange {

    override val start: TimeUnit = startZoned.gmtTimeUnit
    override val end: TimeUnit = endZoned.gmtTimeUnit

    init {
        requireValid()
    }
}