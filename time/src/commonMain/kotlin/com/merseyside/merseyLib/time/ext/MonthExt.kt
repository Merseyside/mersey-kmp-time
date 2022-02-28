package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.*
import com.merseyside.merseyLib.time.units.Days
import com.merseyside.merseyLib.time.units.Month
import com.merseyside.merseyLib.time.units.Years
import com.merseyside.merseyLib.time.units.plus
import com.merseyside.merseyLib.time.utils.Pattern

fun Month.getDayCount(year: Years = Time.getCurrentYear()): Days {
    return if (this == Month.FEBRUARY && year.isLeap()) {
        days + 1
    } else days
}

fun Month.getHuman(
    pattern: Pattern = TimeConfiguration.monthPattern,
    language: Language = TimeConfiguration.language,
    country: Country = TimeConfiguration.country,
): String {
    val timeUnit = Days(31 * index)
    return timeUnit.toFormattedDate(pattern, language = language, country = country).date
}

fun Month.getHuman(
    pattern: String,
    language: Language = TimeConfiguration.language,
    country: Country = TimeConfiguration.country,
): String {
    return getHuman(Pattern.CUSTOM(pattern), language, country)
}