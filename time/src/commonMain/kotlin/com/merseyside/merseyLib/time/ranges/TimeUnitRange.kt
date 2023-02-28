package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.ext.toHumanString
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.utils.Pattern
import kotlinx.serialization.Serializable

@Serializable
data class TimeUnitRange(
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
        if (other !is TimeUnitRange) return false

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
