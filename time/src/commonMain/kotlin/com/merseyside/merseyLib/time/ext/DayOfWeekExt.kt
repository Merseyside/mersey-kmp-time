package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.*

fun DayOfWeek.toTimeUnit(): TimeUnit {
    return Days(index)
}

fun DayOfWeek.isWeekendDay(): Boolean {
    return this == DayOfWeek.SATURDAY || this == DayOfWeek.SUNDAY
}

fun DayOfWeek.isWeekDay(): Boolean = !isWeekendDay()

fun DayOfWeek.getHuman(
    pattern: String = TimeConfiguration.dayOfWeekPattern,
    language: Language = TimeConfiguration.language,
    country: Country = TimeConfiguration.country,
    timeZone: String = TimeConfiguration.timeZone
): String {
    val timeUnit = Days(4) + toTimeUnit() // Add 4 days because 1970-01-01 is Thursday
    return timeUnit.toDayOfWeekHuman(pattern, timeZone, language, country)
}