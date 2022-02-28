package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.Country
import com.merseyside.merseyLib.time.Language
import com.merseyside.merseyLib.time.TimeZone
import com.merseyside.merseyLib.time.exception.TimeParseException
import com.merseyside.merseyLib.time.units.Seconds
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.ZonedTimeUnit
import com.merseyside.merseyLib.time.utils.Pattern
import com.merseyside.merseyLib.time.utils.getOffsetFromString
import com.merseyside.merseyLib.time.utils.patternToFormattedOptions
import platform.Foundation.*

@Throws(TimeParseException::class)
actual fun String.toTimeUnit(
    pattern: Pattern,
    country: Country,
    language: Language
): TimeUnit {
    if (pattern.isOffsetPattern()) throw TimeParseException(
        "$this can not be parsed with " +
                "offset pattern. Use toZonedTimeUnit."
    )

    val options = patternToFormattedOptions(pattern)

    val dateFormatter = NSISO8601DateFormatter()
    dateFormatter.setFormatOptions(options)
    val date = dateFormatter.dateFromString(this) ?: throw TimeParseException("Can not parse time")
    return Seconds(date.timeIntervalSince1970)
}

@Throws(TimeParseException::class)
actual fun String.toZonedTimeUnit(pattern: Pattern.Offset): ZonedTimeUnit {
    if (!pattern.isOffsetPattern()) throw TimeParseException(
        "ZonedTimeUnit could be parsed " +
                "only with offset pattern!"
    )

    val options = patternToFormattedOptions(pattern)

    val offset = getOffsetFromString(this)
    val gmtDate = NSISO8601DateFormatter().run {
        formatOptions = options
        dateFromString(this@toZonedTimeUnit)
    } ?: throw TimeParseException("Can not parse time")

    val timeZone = NSTimeZone.timeZoneForSecondsFromGMT(offset.toSeconds().value)

    return ZonedTimeUnit.ofLocalTime(
        Seconds(gmtDate.timeIntervalSince1970).logHuman(),
        TimeZone(
            timeZone.abbreviation ?: throw TimeParseException("Can not parse time"),
            Seconds(offset)
        )
    )

}

private fun getCalendar(): NSCalendar {
    val calendar = NSCalendar.currentCalendar
    return calendar.apply {
        NSTimeZone.timeZoneWithAbbreviation("GMT")
            ?: throw TimeParseException("Can not get time zone!")
    }
}