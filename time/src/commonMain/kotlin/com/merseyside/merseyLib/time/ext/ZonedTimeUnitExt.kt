package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.*
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.ZonedTimeUnit
import com.merseyside.merseyLib.time.utils.Pattern

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

expect fun ZonedTimeUnit.toFormattedDate(
    pattern: Pattern.Offset = TimeConfiguration.zonedDefaultPattern
): PatternedFormattedDate