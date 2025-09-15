package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.TimeZone
import com.merseyside.merseyLib.time.ranges.undefined.Undefined
import com.merseyside.merseyLib.time.ranges.undefined.UndefinedTimeRange
import com.merseyside.merseyLib.time.ranges.undefined.UndefinedTimeRangeImpl
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.ZonedTimeUnit
import kotlinx.serialization.Serializable

interface ZonedTimeRange : TimeRange {
    val startZoned: ZonedTimeUnit
    val endZoned: ZonedTimeUnit

    companion object {
        fun create(startZoned: ZonedTimeUnit, endZoned: ZonedTimeUnit): ZonedTimeRange {
            return ZonedTimeRangeImpl(startZoned, endZoned)
        }

        fun createWithStart(startZoned: ZonedTimeUnit): ZonedTimeRange {
            return UndefinedZonedTimeRange(
                startZoned,
                ZonedTimeUnit(Undefined.MAX(), TimeZone.NOT_SET_ZONE)
            )
        }

        fun createWithEnd(endZoned: ZonedTimeUnit): ZonedTimeRange {
            return UndefinedZonedTimeRange(
                ZonedTimeUnit(Undefined.MIN(), TimeZone.NOT_SET_ZONE),
                endZoned
            )
        }
    }
}

@Serializable
internal data class ZonedTimeRangeImpl(
    override val startZoned: ZonedTimeUnit,
    override val endZoned: ZonedTimeUnit
) : ZonedTimeRange {

    override val start: TimeUnit = startZoned.gmtTimeUnit
    override val end: TimeUnit = endZoned.gmtTimeUnit

    init {
        requireValid()
    }
}

@Serializable
internal data class UndefinedZonedTimeRange(
    override val startZoned: ZonedTimeUnit,
    override val endZoned: ZonedTimeUnit
) : ZonedTimeRange, UndefinedTimeRange {
    override val start: TimeUnit = startZoned.gmtTimeUnit
    override val end: TimeUnit = endZoned.gmtTimeUnit
}