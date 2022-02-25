package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.PatternedFormattedDate
import com.merseyside.merseyLib.time.exception.TimeParseException
import com.merseyside.merseyLib.time.getDate
import com.merseyside.merseyLib.time.units.ZonedTimeUnit
import com.merseyside.merseyLib.time.utils.Pattern
import com.merseyside.merseyLib.time.utils.patternToFormattedOptions
import platform.Foundation.NSISO8601DateFormatter
import platform.Foundation.NSTimeZone
import platform.Foundation.timeZoneWithAbbreviation

actual fun ZonedTimeUnit.toFormattedDate(pattern: Pattern): PatternedFormattedDate {
    val options = patternToFormattedOptions(pattern)
    val dateFormatter = NSISO8601DateFormatter().apply {
        timeZone = NSTimeZone.timeZoneWithAbbreviation(this@toFormattedDate.timeZone.zoneId)
            ?: throw TimeParseException("Can not parse time!")
        formatOptions = options
    }

    val date = getDate(gmtTimeUnit)

    val formatted = dateFormatter.stringFromDate(date)

    return PatternedFormattedDate(formatted, pattern)
}