package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.logger.Logger
import com.merseyside.merseyLib.time.*

fun FormattedDate.toSecondsOfDay(timeZone: String = TimeConfiguration.timeZone): FormattedDate {
    return toTimeUnit().toSecondsOfDay().toFormattedDate()
}

fun FormattedDate.toMinutesOfHour(timeZone: String = TimeConfiguration.timeZone): FormattedDate {
    return toTimeUnit().toMinutesOfHour(timeZone).toFormattedDate()
}

fun FormattedDate.toHoursOfDay(timeZone: String = TimeConfiguration.timeZone): FormattedDate {
    return toTimeUnit().toHoursOfDay(timeZone).toFormattedDate()
}

fun FormattedDate.toHoursMinutesOfDay(timeZone: String = TimeConfiguration.timeZone): FormattedDate {
    return toTimeUnit().toHoursMinutesOfDay(timeZone).toFormattedDate()
}

fun FormattedDate.toTimeUnit(vararg pattern: String): TimeUnit {
    val patternsList: List<String> = if (pattern.isNotEmpty()) pattern.toList() else TimeConfiguration.formatPatterns

    patternsList.forEach {
        try {
            value.toTimeUnit(it)?.let { timestamp -> return timestamp.toMillis() }
        } catch (e: Exception) {
            Logger.logErr(tag = "TimeUnit", msg = "$it is wrong pattern to format time")
        }
    }

    throw Exception("Can not format $value with suggested patterns!")
}

