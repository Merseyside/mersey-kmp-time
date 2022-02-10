package com.merseyside.merseyLib.time.utils

import com.merseyside.merseyLib.time.units.Seconds
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.TimeZone
import com.merseyside.merseyLib.time.exception.TimeParseException

@Throws(TimeParseException::class)
internal actual fun getTimeZone(zoneId: String): TimeZone {
    TODO()
}

internal actual fun getTimeZoneOffset(zoneId: String): Seconds {
    TODO()
}

@Throws(TimeParseException::class)
internal actual fun getZoneByOffset(offset: TimeUnit): TimeZone {
    TODO()
}

internal actual fun getSystemZone(): TimeZone {
    TODO()
}

internal actual fun isSystemTimeZone(timeZone: TimeZone): Boolean {
    TODO()
}