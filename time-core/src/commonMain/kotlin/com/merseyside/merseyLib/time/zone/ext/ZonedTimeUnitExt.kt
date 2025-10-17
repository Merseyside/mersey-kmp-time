package com.merseyside.merseyLib.time.zone.ext

import com.merseyside.merseyLib.time.Time
import com.merseyside.merseyLib.time.ext.checkUndefined
import com.merseyside.merseyLib.time.ext.getNextDay
import com.merseyside.merseyLib.time.ext.getPrevDay
import com.merseyside.merseyLib.time.ext.getStartOfDate
import com.merseyside.merseyLib.time.format.PatternedFormattedDate
import com.merseyside.merseyLib.time.ranges.TimeRangeImpl
import com.merseyside.merseyLib.time.ranges.zone.ZonedTimeRange
import com.merseyside.merseyLib.time.units.*
import com.merseyside.merseyLib.time.utils.Pattern
import com.merseyside.merseyLib.time.zone.TimeZone
import com.merseyside.merseyLib.time.zone.ZonedTimeUnit

operator fun ZonedTimeUnit.plus(increment: ZonedTimeUnit): TimeUnit {
    return gmtTimeUnit + increment.gmtTimeUnit
}

operator fun ZonedTimeUnit.div(divider: ZonedTimeUnit): TimeUnit {
    return gmtTimeUnit / divider.gmtTimeUnit
}

operator fun ZonedTimeUnit.times(times: ZonedTimeUnit): TimeUnit {
    return gmtTimeUnit * times.gmtTimeUnit
}

operator fun ZonedTimeUnit.minus(unary: ZonedTimeUnit): TimeUnit {
    return gmtTimeUnit - unary.gmtTimeUnit
}

/**
 * Compares instant time of two ZonedTimeUnits
 */
operator fun ZonedTimeUnit.compareTo(other: ZonedTimeUnit): Int {
    return gmtTimeUnit.compareTo(other.gmtTimeUnit)
}

fun ZonedTimeUnit.isEqualInstantTime(other: ZonedTimeUnit): Boolean {
    return compareTo(other) == 0
}

fun ZonedTimeUnit.applyToTimeUnit(block: (TimeUnit) -> TimeUnit): ZonedTimeUnit {
    return ZonedTimeUnit.ofLocalTime(block(localTimeUnit), timeZone)
}

fun ZonedTimeUnit.toTimeZone(timeZone: TimeZone): ZonedTimeUnit {
    gmtTimeUnit.checkUndefined { return this }
    return ZonedTimeUnit.ofGMT(gmtTimeUnit, Time.configuration.systemTimeZone)
}

fun ZonedTimeUnit.toSystemTimeZone(): ZonedTimeUnit {
    return toTimeZone(Time.configuration.systemTimeZone)
}

fun ZonedTimeUnit.getStartOfDate(): ZonedTimeUnit {
    return ZonedTimeUnit.ofLocalTime(localTimeUnit.getStartOfDate(), timeZone)
}

fun ZonedTimeUnit.getNextDay(): ZonedTimeUnit {
    return applyToTimeUnit { it.getNextDay() }
}

fun ZonedTimeUnit.getPrevDay(): ZonedTimeUnit {
    return applyToTimeUnit { it.getPrevDay() }
}

fun ZonedTimeUnit.toServerTimeZone(): ZonedTimeUnit {
    return toTimeZone(Time.configuration.serverTimeZone)
}

fun ZonedTimeUnit.toDayTimeRange(): ZonedTimeRange {
    val daysTimeUnit = getStartOfDate()
    return daysTimeUnit.toZonedTimeRange(startShift = Day)
}

fun ZonedTimeUnit.toZonedTimeRange(
    startShift: TimeUnit = TimeUnit.empty(),
    backShift: TimeUnit = TimeUnit.empty()
): ZonedTimeRange {
    if (startShift.isEmpty() && backShift.isEmpty())
        throw IllegalArgumentException("Pass at least one shift value!")

    val timeRange = TimeRangeImpl(localTimeUnit - backShift, localTimeUnit + startShift)
    return ZonedTimeRange.create(timeRange, timeZone)
}

expect fun ZonedTimeUnit.toFormattedDate(
    pattern: Pattern.Offset = Time.configuration.zonedDefaultPattern
): PatternedFormattedDate

