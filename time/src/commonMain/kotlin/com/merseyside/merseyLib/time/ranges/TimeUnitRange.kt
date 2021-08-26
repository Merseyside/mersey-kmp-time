package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.TimeUnit

data class TimeUnitRange(
    override val start: TimeUnit,
    override val end: TimeUnit
): TimeRange {
    init { requireValid() }
}
