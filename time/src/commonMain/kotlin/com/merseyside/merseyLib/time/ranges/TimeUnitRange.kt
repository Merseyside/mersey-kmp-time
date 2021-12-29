package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.TimeUnit
import com.merseyside.merseyLib.time.TimeUnitRangeAsListSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimeUnitRange(
    override val start: TimeUnit,
    override val end: TimeUnit
): TimeRange {

    constructor(timeRange: TimeRange): this(timeRange.start, timeRange.end)

    init { requireValid() }
}
