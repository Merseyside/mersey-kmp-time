package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.kotlin.logger.Logger
import com.merseyside.merseyLib.time.*
import com.merseyside.merseyLib.time.exception.TimeParseException
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.utils.Pattern

@Throws(TimeParseException::class)
fun FormattedDate.toPatternedFormattedDate(
    oldPattern: Pattern,
    newPattern: Pattern
): PatternedFormattedDate {
    val timeUnit = toTimeUnit(oldPattern)
    return PatternedFormattedDate(timeUnit.toFormattedDate(newPattern).date, newPattern)
}

fun FormattedDate.toSecondsOfMinute(): FormattedDate {
    return toTimeUnit().toSecondsOfMinute().toFormattedDate()
}

fun FormattedDate.toMinutesOfHour(): FormattedDate {
    return toTimeUnit().toMinutesOfHour().toFormattedDate()
}

fun FormattedDate.toHoursOfDay(): FormattedDate {
    return toTimeUnit().toHoursOfDay().toFormattedDate()
}

fun FormattedDate.toHoursMinutesOfDay(): FormattedDate {
    return toTimeUnit().toHoursMinutesOfDay().toFormattedDate()
}

@Throws(TimeParseException::class)
fun FormattedDate.toFormattedDate(toPattern: String): FormattedDate {
    return toFormattedDate(Pattern.CUSTOM(toPattern))
}

@Throws(TimeParseException::class)
fun FormattedDate.toFormattedDate(toPattern: Pattern): FormattedDate {
    val timeUnit = toTimeUnit()
    return timeUnit.toFormattedDate(toPattern)
}

@Throws(TimeParseException::class)
fun FormattedDate.toFormattedDate(fromPattern: Pattern, toPattern: Pattern): FormattedDate {
    val timeUnit = toTimeUnit(fromPattern)
    return timeUnit.toFormattedDate(toPattern)
}

@Throws(TimeParseException::class)
fun FormattedDate.toTimeUnit(pattern: Pattern? = null): TimeUnit {
    val patternsList: List<Pattern> =
        if (pattern != null) listOf(pattern)
        else Time.configuration.patterns

    patternsList.forEach {
        try {
            return date.toTimeUnit(pattern = it)
        } catch (e: TimeParseException) {
            Logger.logErr(tag = "TimeUnit", msg = "$it is wrong pattern to format ${this.date}")
        }
    }

    throw TimeParseException("Can not format $date with suggested patterns!")
}

