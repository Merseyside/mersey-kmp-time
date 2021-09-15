package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.TimeUnit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("timeUnitRange")
data class TimeUnitRange(
    override val start: TimeUnit,
    override val end: TimeUnit
): TimeRange {
    init { requireValid() }
}
