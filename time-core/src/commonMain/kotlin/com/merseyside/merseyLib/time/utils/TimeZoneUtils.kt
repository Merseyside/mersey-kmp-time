package com.merseyside.merseyLib.time.utils

import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.zone.TimeZone
import com.merseyside.merseyLib.time.exception.TimeParseException

@Throws(TimeParseException::class)
internal expect fun getTimeZone(zoneId: String): TimeZone

@Throws(TimeParseException::class)
internal expect fun getZoneByOffset(offset: TimeUnit): TimeZone

internal expect fun getSystemZone(): TimeZone

internal expect fun isSystemTimeZone(timeZone: TimeZone): Boolean