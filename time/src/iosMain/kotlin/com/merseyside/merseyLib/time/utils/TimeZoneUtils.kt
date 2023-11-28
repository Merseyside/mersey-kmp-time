package com.merseyside.merseyLib.time.utils

import com.merseyside.merseyLib.time.TimeZone
import com.merseyside.merseyLib.time.exception.TimeParseException
import com.merseyside.merseyLib.time.ext.makeNegative
import com.merseyside.merseyLib.time.units.*
import platform.Foundation.*

@Throws(TimeParseException::class)
internal actual fun getTimeZone(zoneId: String): TimeZone {
    val timeZone = getNSTimeZone(zoneId)
    return TimeZone(
        timeZone.abbreviation ?: throw TimeParseException(),
        Seconds(timeZone.secondsFromGMT)
    )
}

@Throws(TimeParseException::class)
internal actual fun getZoneByOffset(offset: TimeUnit): TimeZone {
    val timeZone = NSTimeZone.timeZoneForSecondsFromGMT(offset.toSeconds().value)
    return TimeZone(
        timeZone.abbreviation ?: throw TimeParseException("No abbreviation"),
        offset
    )
}

internal actual fun getSystemZone(): TimeZone {
    return getTimeZone(NSTimeZone.systemTimeZone)
}

internal actual fun isSystemTimeZone(timeZone: TimeZone): Boolean {
    val system = NSTimeZone.systemTimeZone
    return system.secondsFromGMT == timeZone.offset.toSeconds().value
}

@Throws(TimeParseException::class)
private fun getTimeZone(nsTimeZone: NSTimeZone): TimeZone {
    return with(nsTimeZone) {

        val abbreviation = abbreviation ?: throw TimeParseException("No abbreviation")
        val offset = Seconds(secondsFromGMT)

        TimeZone(abbreviation, offset)
    }
}

private fun getNSTimeZone(zoneId: String): NSTimeZone {
    return NSTimeZone.timeZoneWithAbbreviation(zoneId)
        ?: throw TimeParseException("Can not obtain time zone with $zoneId abbreviation")
}

@Throws(TimeParseException::class)
internal fun getOffsetFromString(date: String) : TimeUnit {

    var zone = date.takeLast(6)
    val sign = zone.first()
    return if (sign ==  '-' || sign == '+') {
        zone = zone.drop(1)
        val hoursMinutes = zone.split(":")
        if (hoursMinutes.size != 2) throw TimeParseException("Zone not found!")

        val hours = hoursMinutes[0]
        val minutes = hoursMinutes[1]

        val timeUnit = Hours(hours) + Minutes(minutes)

        if (sign == '-') timeUnit.makeNegative()
        else timeUnit
    } else {
        throw TimeParseException("Zone not found!")
    }

}