@file:JvmName("AndroidZonedUnitExt")

package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.utils.Pattern
import com.merseyside.merseyLib.time.PatternedFormattedDate
import com.merseyside.merseyLib.time.units.ZonedTimeUnit
import com.merseyside.merseyLib.time.utils.patternToDateTimeFormatter
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

actual fun ZonedTimeUnit.toFormattedDate(pattern: Pattern): PatternedFormattedDate {
    val zonedDateTime = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(gmtTimeUnit.millis),
        ZoneId.of(timeZone.zoneId)
    )

    return PatternedFormattedDate(
        zonedDateTime.format(patternToDateTimeFormatter(pattern)),
        pattern
    )
}