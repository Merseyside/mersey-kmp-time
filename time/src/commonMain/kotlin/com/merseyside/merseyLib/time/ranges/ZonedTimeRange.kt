package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.TimeZone
import com.merseyside.merseyLib.time.ranges.undefined.Undefined
import com.merseyside.merseyLib.time.ranges.undefined.UndefinedTimeRange
import com.merseyside.merseyLib.time.ranges.undefined.UndefinedTimeRangeImpl
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.ZonedTimeUnit
import kotlinx.serialization.Serializable

@Serializable
data class ZonedTimeRange(
    val startZoned: ZonedTimeUnit,
    val endZoned: ZonedTimeUnit
) : TimeRange {

    override val start: TimeUnit = startZoned.gmtTimeUnit
    override val end: TimeUnit = endZoned.gmtTimeUnit

    init {
        requireValid()
    }
}

@Serializable
class UndefinedZonedTimeRange internal constructor(
    val startZoned: ZonedTimeUnit,
    val endZoned: ZonedTimeUnit
) : UndefinedTimeRange {
    override val start: TimeUnit = startZoned.gmtTimeUnit
    override val end: TimeUnit = endZoned.gmtTimeUnit

    companion object {
        fun createWithStart(start: ZonedTimeUnit): UndefinedZonedTimeRange {
            return UndefinedZonedTimeRange(
                start,
                ZonedTimeUnit(Undefined.MAX(), TimeZone.NOT_SET_ZONE)
            )
        }

        fun createWithEnd(end: ZonedTimeUnit): UndefinedZonedTimeRange {
            return UndefinedZonedTimeRange(
                ZonedTimeUnit(Undefined.MIN(), TimeZone.NOT_SET_ZONE),
                end
            )
        }
    }
}