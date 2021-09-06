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

actual fun getFormattedDate(
    timeUnit: TimeUnit,
    pattern: String,
    timeZone: String,
    language: String,
    country: String
): FormattedDate {
    val date = getDate(timeUnit)
    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = pattern
    return FormattedDate(dateFormatter.stringFromDate(date))
}

actual fun getSecondsOfMinute(timeUnit: TimeUnit, timeZone: String): Seconds {
    return Seconds(getComponents(timeUnit, NSSecondCalendarUnit, timeZone).second)
}

actual fun getMinutesOfHour(timeUnit: TimeUnit, timeZone: String): Minutes {
    return Minutes(getComponents(timeUnit, NSMinuteCalendarUnit, timeZone).minute)
}

actual fun getHoursOfDay(timeUnit: TimeUnit, timeZone: String): Hours {
    return Hours(getComponents(timeUnit, NSHourCalendarUnit, timeZone).hour)
}

actual fun getMonth(timeUnit: TimeUnit, timeZone: String): Month {
    return Month.getByIndex(getComponents(timeUnit, NSMonthCalendarUnit, timeZone).month.toInt())
}

actual fun getYear(timeUnit: TimeUnit, timeZone: String): Years {
    return Years(getComponents(timeUnit, NSYearCalendarUnit, timeZone).year.toInt())
}

private fun getDate(timeUnit: TimeUnit): NSDate {
    return NSDate.dateWithTimeIntervalSince1970(timeUnit.toSeconds().value.toDouble())
}

private fun getComponents(
    timeUnit: TimeUnit,
    unit: NSCalendarUnit,
    timeZone: String
): NSDateComponents {
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
