package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.*
import com.merseyside.merseyLib.time.exception.TimeParseException
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.ZonedTimeUnit
import com.merseyside.merseyLib.time.utils.Pattern

@Throws(TimeParseException::class)
actual fun String.toTimeUnit(
    pattern: Pattern,
    country: Country,
    language: Language
): TimeUnit {
    TODO()
}

@Throws(TimeParseException::class)
actual fun String.toZonedTimeUnit(pattern: Pattern.Offset): ZonedTimeUnit {
    TODO()
}