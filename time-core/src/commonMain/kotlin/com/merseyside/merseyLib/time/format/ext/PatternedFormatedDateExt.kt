package com.merseyside.merseyLib.time.format.ext

import com.merseyside.merseyLib.time.utils.Pattern
import com.merseyside.merseyLib.time.format.PatternedFormattedDate
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.zone.ZonedTimeUnit
import com.merseyside.merseyLib.time.exception.TimeParseException
import com.merseyside.merseyLib.time.ext.toFormattedDate
import com.merseyside.merseyLib.time.ext.toTimeUnit
import com.merseyside.merseyLib.time.units.Undefined

inline fun PatternedFormattedDate.checkUndefined(undefined: () -> Unit) {
    if (pattern is Pattern.UNDEFINED) undefined()
}

@Throws(TimeParseException::class)
fun PatternedFormattedDate.toPattern(newPattern: Pattern): PatternedFormattedDate {
    checkUndefined { return this }

    val timeUnit = toTimeUnit(pattern)
    return PatternedFormattedDate(timeUnit.toFormattedDate(newPattern), newPattern)
}

@Throws(TimeParseException::class)
fun PatternedFormattedDate.toPattern(newPattern: String): PatternedFormattedDate {
    checkUndefined { return this }
    return toPattern(Pattern.CUSTOM(newPattern))
}

fun PatternedFormattedDate.toTimeUnit(): TimeUnit {
    checkUndefined { return Undefined.MIN() }

    return date.toTimeUnit(pattern)
}

@Throws(TimeParseException::class)
fun PatternedFormattedDate.toZonedTimeUnit(): ZonedTimeUnit {
    if (pattern is Pattern.Offset) {
        return ZonedTimeUnit.of(date, pattern)
    } else throw TimeParseException("Pattern have to be Offset!")
}