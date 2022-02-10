package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.utils.Pattern
import com.merseyside.merseyLib.time.PatternedFormattedDate
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.ZonedTimeUnit
import com.merseyside.merseyLib.time.exception.TimeParseException

@Throws(TimeParseException::class)
fun PatternedFormattedDate.toPattern(newPattern: Pattern): PatternedFormattedDate {
    val timeUnit = toTimeUnit(pattern)
    return PatternedFormattedDate(timeUnit.toFormattedDate(newPattern), newPattern)
}

@Throws(TimeParseException::class)
fun PatternedFormattedDate.toPattern(newPattern: String): PatternedFormattedDate {
    return toPattern(Pattern.CUSTOM(newPattern))
}

fun PatternedFormattedDate.toTimeUnit(): TimeUnit {
    return date.toTimeUnit(pattern)
}

@Throws(TimeParseException::class)
fun PatternedFormattedDate.toZonedTimeUnit(): ZonedTimeUnit {
    if (pattern is Pattern.Offset) {
        return ZonedTimeUnit.of(date, pattern)
    } else throw TimeParseException("Pattern have to be Offset!")
}