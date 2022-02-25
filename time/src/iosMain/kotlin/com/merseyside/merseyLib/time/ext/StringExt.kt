package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.kotlin.extensions.log
import com.merseyside.merseyLib.time.*
import com.merseyside.merseyLib.time.exception.TimeParseException
import com.merseyside.merseyLib.time.units.Seconds
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.ZonedTimeUnit
import com.merseyside.merseyLib.time.utils.Pattern
import com.merseyside.merseyLib.time.utils.patternToFormattedOptions
import platform.Foundation.*
import platform.SensorKit.srAbsoluteTime

@Throws(TimeParseException::class)
actual fun String.toTimeUnit(
    pattern: Pattern,
    country: Country,
    language: Language
): TimeUnit {
    if (pattern.isOffsetPattern())  throw TimeParseException(
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

    val sourceDate = NSISO8601DateFormatter().run {
        //timeZone = NSTimeZone.localTimeZone ?: throw TimeParseException("Something wrong")
        setFormatOptions(patternToFormattedOptions(Pattern.ISO_DATE_TIME))
        dateFromString(this@toZonedTimeUnit) ?: throw TimeParseException("Can not parse time")
    }

    val gmtDate = NSISO8601DateFormatter().run {
        timeZone = NSTimeZone.timeZoneWithAbbreviation("BRT") ?: throw TimeParseException("Something wrong")
        setFormatOptions(options)
        dateFromString(this@toZonedTimeUnit) ?: throw TimeParseException("Can not parse time")
    }

    val offset = gmtDate.timeIntervalSince1970
        .log("since1970", "kek") { toSeconds() } -
            sourceDate.timeIntervalSince1970.log("since1970", "kek1") { toSeconds().getHumanDate() }
    val timeZone = NSTimeZone.timeZoneForSecondsFromGMT(offset.toLong().log("kek", "offset"))

    return ZonedTimeUnit.ofLocalTime(
        Seconds(gmtDate.timeIntervalSince1970),
        TimeZone(
            timeZone.abbreviation ?: throw TimeParseException("Can not parse time"),
            Seconds(offset))
    )
}

private fun getCalendar(): NSCalendar {
    val calendar = NSCalendar.currentCalendar
    return calendar.apply {
        NSTimeZone.timeZoneWithAbbreviation("GMT")
            ?: throw TimeParseException("Can not get time zone!")
    }
}