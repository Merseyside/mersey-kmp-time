package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.*

fun Month.getDayCount(year: Years = TimeConfiguration.year): Days {
    return if (this != Month.FEBRUARY) {
        days
    } else {
        if (year.isLeap()) days + 1
        else days
    }
}

fun Month.getHuman(
    pattern: String = TimeConfiguration.monthPattern,
    language: Language = TimeConfiguration.language,
    country: Country = TimeConfiguration.country,
): String {
    val timeUnit = Days(31 * index)
    return timeUnit.toFormattedDate(pattern, language = language, country = country).value
}