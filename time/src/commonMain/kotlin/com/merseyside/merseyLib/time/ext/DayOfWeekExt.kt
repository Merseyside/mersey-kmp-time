package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.*

fun DayOfWeek.toTimeUnit(): TimeUnit {
    return Days(index)
}

fun DayOfWeek.isWeekendDay(): Boolean {
    return this == DayOfWeek.SATURDAY || this == DayOfWeek.SUNDAY
}

fun DayOfWeek.isWeekDay(): Boolean = !isWeekendDay()

/**
 * Add 4 days because 1970-01-01 is Thursday
 */
fun DayOfWeek.getHuman(
    pattern: String = TimeConfiguration.dayOfWeekPattern,
    language: Language = TimeConfiguration.language,
    timeZone: String = TimeConfiguration.timeZone
): FormattedDate {
    return getDayOfWeekHuman((Days(4) + toTimeUnit()), language, pattern, timeZone)
}