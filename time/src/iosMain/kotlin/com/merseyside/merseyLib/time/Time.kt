package com.merseyside.merseyLib.time

import com.merseyside.merseyLib.logger.Logger
import platform.Foundation.*
import platform.darwin.NSInteger

actual fun getCurrentTime(): TimeUnit {
    return Seconds(NSDate().timeIntervalSince1970)
}

actual fun getDayOfMonth(timeUnit: TimeUnit, timeZone: String): Days {
    return Days(getUnit(timeUnit, 0, timeZone))
}

actual fun getDayOfWeek(timeUnit: TimeUnit, timeZone: String): DayOfWeek {
    return DayOfWeek.MONDAY
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

private fun getDate(millis: TimeUnit): NSDate {
    return NSDate(millis.millis.toDouble())
}

private fun getUnit(timeUnit: TimeUnit, unit: NSInteger, timeZone: String): Int {
    val date = getDate(timeUnit)
    val calendar = getCalendar(timeZone)

    val components = NSDateComponents()
    calendar.dateFromComponents(components)
    Logger.log("Time", components.day)
    return 0
}

private fun getCalendar(timeZone: String): NSCalendar {
    return NSCalendar().apply {
        this.timeZone = if (timeZone != Time.TimeZone.SYSTEM.name) {
            NSTimeZone.timeZoneWithAbbreviation(timeZone) ?: NSTimeZone.systemTimeZone
        } else {
            NSTimeZone.systemTimeZone
        }
    }
}
