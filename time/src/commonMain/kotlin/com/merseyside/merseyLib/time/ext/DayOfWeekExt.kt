package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.*
import com.merseyside.merseyLib.time.units.DayOfWeek
import com.merseyside.merseyLib.time.units.Days
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.plus
import com.merseyside.merseyLib.time.utils.Pattern

fun DayOfWeek.toTimeUnit(): TimeUnit {
    return Days(index)
}

fun DayOfWeek.isWeekendDay(): Boolean {
    return this == DayOfWeek.SATURDAY || this == DayOfWeek.SUNDAY
}

fun DayOfWeek.isWeekDay(): Boolean = !isWeekendDay()

fun DayOfWeek.getHuman(
    pattern: Pattern = TimeConfiguration.dayOfWeekPattern,
    language: Language = TimeConfiguration.language,
    country: Country = TimeConfiguration.country
): String {
    val timeUnit = Days(4) + toTimeUnit() // Add 4 days because 1970-01-01 is Thursday
    return timeUnit.toDayOfWeekHuman(pattern, language, country)
}