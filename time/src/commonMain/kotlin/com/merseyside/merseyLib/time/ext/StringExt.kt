package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.*
import com.merseyside.merseyLib.time.exception.TimeParseException
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.ZonedTimeUnit
import com.merseyside.merseyLib.time.utils.Pattern

@Throws(TimeParseException::class)
internal expect fun String.toTimeUnit(
    pattern: Pattern,
    country: Country = Time.configuration.country,
    language: Language = Time.configuration.language
): TimeUnit

@Throws(TimeParseException::class)
internal expect fun String.toZonedTimeUnit(pattern: Pattern.Offset): ZonedTimeUnit