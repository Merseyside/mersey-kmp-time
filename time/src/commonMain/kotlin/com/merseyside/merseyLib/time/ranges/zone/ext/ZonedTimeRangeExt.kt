package com.merseyside.merseyLib.time.ranges.zone.ext

import com.merseyside.merseyLib.time.Time
import com.merseyside.merseyLib.time.ranges.zone.ZonedTimeRange
import com.merseyside.merseyLib.time.zone.TimeZone
import com.merseyside.merseyLib.time.zone.ext.toTimeZone

fun ZonedTimeRange.toTimeZone(timeZone: TimeZone): ZonedTimeRange {
    return ZonedTimeRange.Companion.create(
        startZoned = startZoned.toTimeZone(timeZone),
        endZoned = endZoned.toTimeZone(timeZone)
    )
}

fun ZonedTimeRange.toServerTimeZone(): ZonedTimeRange {
    return toTimeZone(Time.configuration.serverTimeZone)
}

fun ZonedTimeRange.toSystemTimeZone(): ZonedTimeRange {
    return toTimeZone(Time.configuration.systemTimeZone)

}