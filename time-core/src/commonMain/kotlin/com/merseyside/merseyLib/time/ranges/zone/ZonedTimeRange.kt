package com.merseyside.merseyLib.time.ranges.zone

import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.ranges.undefined.Undefined
import com.merseyside.merseyLib.time.ranges.undefined.UndefinedTimeRange
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.zone.TimeZone
import com.merseyside.merseyLib.time.zone.ZonedTimeUnit
import kotlinx.serialization.Serializable

interface ZonedTimeRange : TimeRange {
    val startZoned: ZonedTimeUnit
    val endZoned: ZonedTimeUnit

    companion object {
        fun create(startZoned: ZonedTimeUnit, endZoned: ZonedTimeUnit): ZonedTimeRange {
            check(startZoned.timeZone == endZoned.timeZone) {
                "Start and end time zones must be the same!"
            }
            return ZonedTimeRangeImpl(startZoned, endZoned)
        }

        fun create(
            startTimeUnit: TimeUnit,
            endTimeUnit: TimeUnit,
            timeZone: TimeZone
        ): ZonedTimeRange {
            return create(
                ZonedTimeUnit.ofLocalTime(startTimeUnit, timeZone),
                ZonedTimeUnit.ofLocalTime(endTimeUnit, timeZone)
            )
        }

        fun create(timeRange: TimeRange, timeZone: TimeZone): ZonedTimeRange {
            return create(timeRange.start, timeRange.end, timeZone)
        }

        fun createWithStart(startZoned: ZonedTimeUnit): ZonedTimeRange {
            return UndefinedZonedTimeRange(
                startZoned,
                ZonedTimeUnit.ofGMT(Undefined.MAX(), startZoned.timeZone)
            )
        }

        fun createWithEnd(endZoned: ZonedTimeUnit): ZonedTimeRange {
            return UndefinedZonedTimeRange(
                ZonedTimeUnit.ofGMT(Undefined.MIN(), endZoned.timeZone),
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