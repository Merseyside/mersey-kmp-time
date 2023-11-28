package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.Country
import com.merseyside.merseyLib.time.Language
import com.merseyside.merseyLib.time.Time
import com.merseyside.merseyLib.time.units.CalendarYears
import com.merseyside.merseyLib.time.units.Days
import com.merseyside.merseyLib.time.units.Month
import com.merseyside.merseyLib.time.utils.Pattern

fun Month.getDayCount(year: CalendarYears = Time.getCurrentYear()): Int {
    return if (this == Month.FEBRUARY && year.isLeap()) {
        days + 1
    } else days
}

fun Month.getHuman(
    pattern: Pattern = Time.configuration.monthPattern,
    language: Language = Time.configuration.language,
    country: Country = Time.configuration.country,
): String {
    val timeUnit = Days(31 * index)
    return timeUnit.toFormattedDate(pattern, language = language, country = country).date
}

fun Month.getHuman(
    pattern: String,
    language: Language = Time.configuration.language,
    country: Country = Time.configuration.country,
): String {
    return getHuman(Pattern.CUSTOM(pattern), language, country)
}