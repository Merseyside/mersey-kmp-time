package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.logger.Logger
import com.merseyside.merseyLib.time.FormattedDate
import com.merseyside.merseyLib.time.TimeConfiguration
import com.merseyside.merseyLib.time.TimeUnit

fun FormattedDate.toSecondsOfMinute(timeZone: String = TimeConfiguration.timeZone): FormattedDate {
    return toTimeUnit().toSecondsOfMinute().toFormattedDate(timeZone = timeZone)
}

fun FormattedDate.toMinutesOfHour(timeZone: String = TimeConfiguration.timeZone): FormattedDate {
    return toTimeUnit().toMinutesOfHour(timeZone).toFormattedDate(timeZone = timeZone)
}

fun FormattedDate.toHoursOfDay(timeZone: String = TimeConfiguration.timeZone): FormattedDate {
    return toTimeUnit().toHoursOfDay(timeZone).toFormattedDate(timeZone = timeZone)
}

fun FormattedDate.toHoursMinutesOfDay(timeZone: String = TimeConfiguration.timeZone): FormattedDate {
    return toTimeUnit().toHoursMinutesOfDay(timeZone).toFormattedDate(timeZone = timeZone)
}

fun FormattedDate.toTimeUnit(
    vararg pattern: String,
    timeZone: String = TimeConfiguration.timeZone
): TimeUnit {
    val patternsList: List<String> = if (pattern.isNotEmpty()) pattern.toList() else TimeConfiguration.formatPatterns

    patternsList.forEach {
        try {
            value.toTimeUnit(
                dateFormat = it,
                timeZone = timeZone
            )?.let { timeUnit -> return timeUnit }
        } catch (e: Exception) {
            Logger.logErr(tag = "TimeUnit", msg = "$it is wrong pattern to format time")
        }
    }

    throw Exception("Can not format $value with suggested patterns!")
}

