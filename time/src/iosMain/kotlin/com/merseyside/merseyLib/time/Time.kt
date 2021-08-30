package com.merseyside.merseyLib.time

import platform.Foundation.*

actual fun getCurrentTime(): TimeUnit {
    return Seconds(NSDate().timeIntervalSince1970)
}

actual fun getDayOfMonth(timeUnit: TimeUnit, timeZone: String): Days {
    return Days(getComponents(timeUnit, NSDayCalendarUnit, timeZone).day)
}

actual fun getDayOfWeek(timeUnit: TimeUnit, timeZone: String): DayOfWeek {
    return DayOfWeek.getByPlatformIndex(
        getComponents(
            timeUnit,
            NSWeekdayCalendarUnit,
            timeZone
        ).weekday.toInt()
    )
}

actual fun getDayOfWeekHuman(
    timeUnit: TimeUnit,
    language: Language,
    pattern: String,
    timeZone: String
): FormattedDate {
    return FormattedDate("")
}

actual fun getFormattedDate(timeUnit: TimeUnit, pattern: String, timeZone: String): FormattedDate {
    return FormattedDate("")
}

actual fun getSecondsOfDay(timeUnit: TimeUnit, timeZone: String): Seconds {
    return Seconds(0)
}

actual fun getMinutesOfHour(timeUnit: TimeUnit, timeZone: String): Minutes {
    return Minutes(0)
}

actual fun getHoursOfDay(timeUnit: TimeUnit, timeZone: String): Hours {
    return Hours(0)
}

actual fun getMonth(timeUnit: TimeUnit, timeZone: String): Month {
    return Month.APRIL
}

actual fun getYear(timeUnit: TimeUnit, timeZone: String): Years {
    return Years(0)
}

private fun getDate(timeUnit: TimeUnit): NSDate {
    return NSDate.dateWithTimeIntervalSince1970(timeUnit.toSeconds().value.toDouble())
}

private fun getComponents(timeUnit: TimeUnit, unit: NSCalendarUnit, timeZone: String): NSDateComponents {
    val date = getDate(timeUnit)
    val calendar = getCalendar(timeZone)

    return calendar.components(unit, date)
}

private fun getCalendar(timeZone: String): NSCalendar {
    val calendar = NSCalendar.currentCalendar
    return calendar.apply {
        this.timeZone = if (timeZone != Time.TimeZone.SYSTEM.name) {
            NSTimeZone.timeZoneWithAbbreviation(timeZone) ?: NSTimeZone.systemTimeZone
        } else {
            NSTimeZone.systemTimeZone
        }
    }
}
