@file:JvmName("AndroidTimeZoneUtils")

package com.merseyside.merseyLib.time.utils

import com.merseyside.merseyLib.time.units.Seconds
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.TimeZone
import com.merseyside.merseyLib.time.exception.TimeParseException
import java.time.DateTimeException
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset

@Throws(TimeParseException::class)
internal actual fun getTimeZone(zoneId: String): TimeZone {
    return getTimeZone(getZoneId(zoneId))
}

@Throws(TimeParseException::class)
internal actual fun getTimeZoneOffset(zoneId: String): Seconds {
    return try {
        val zoneOffset = zoneIdToZoneOffset(zoneId)
        Seconds(zoneOffset.totalSeconds)
    } catch (e: DateTimeException) {
        throw TimeParseException(cause = e)
    }
}

@Throws(TimeParseException::class)
internal actual fun getZoneByOffset(offset: TimeUnit): TimeZone {
    return try {
        val zoneOffset = ZoneOffset.ofTotalSeconds(offset.toSeconds().intValue)
        TimeZone(zoneOffset.id, Seconds(zoneOffset.totalSeconds))
    } catch (e: DateTimeException) {
        throw TimeParseException(cause = e)
    }
}

internal actual fun getSystemZone(): TimeZone {
    return getTimeZone(ZoneId.systemDefault())
}

internal actual fun isSystemTimeZone(timeZone: TimeZone): Boolean {
    val systemZone = getSystemZone()
    return systemZone.offset == timeZone.offset
}

private fun getTimeZone(zoneId: ZoneId): TimeZone {
    val offset = zoneIdToZoneOffset(zoneId.id)
    return TimeZone(zoneId.id, Seconds(offset.totalSeconds))
}

private fun getZoneId(zoneId: String): ZoneId {
    return ZoneId.of(zoneId)
}

private fun zoneIdToZoneOffset(zoneId: String): ZoneOffset {
    val zone = getZoneId(zoneId)
    return Instant.now().atZone(zone).offset
}