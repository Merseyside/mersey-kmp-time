package com.merseyside.merseyLib.time.zone.ext

import com.merseyside.merseyLib.time.*
import com.merseyside.merseyLib.time.ext.checkUndefined
import com.merseyside.merseyLib.time.format.PatternedFormattedDate
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
    return ZonedTimeUnit(block(gmtTimeUnit), timeZone)
}

fun ZonedTimeUnit.toTimeZone(timeZone: TimeZone): ZonedTimeUnit {
    gmtTimeUnit.checkUndefined { return this }
    return ZonedTimeUnit(gmtTimeUnit, Time.configuration.systemTimeZone)
}

fun ZonedTimeUnit.toSystemTimeZone(): ZonedTimeUnit {
    return toTimeZone(Time.configuration.systemTimeZone)
}

fun ZonedTimeUnit.toServerTimeZone(): ZonedTimeUnit {
    return toTimeZone(Time.configuration.serverTimeZone)
}

expect fun ZonedTimeUnit.toFormattedDate(
    pattern: Pattern.Offset = Time.configuration.zonedDefaultPattern
): PatternedFormattedDate