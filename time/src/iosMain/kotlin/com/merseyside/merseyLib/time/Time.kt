package com.merseyside.merseyLib.time

import com.merseyside.merseyLib.time.exception.TimeParseException
import com.merseyside.merseyLib.time.units.*
import com.merseyside.merseyLib.time.utils.Pattern
import com.merseyside.merseyLib.time.utils.patternToFormattedOptions
import platform.Foundation.*

actual fun getCurrentTimeGMT(): TimeUnit {
    return Seconds(NSDate().timeIntervalSince1970)
}

actual fun getDayOfYear(timeUnit: TimeUnit): Int {
    val calendar = getCalendar()

    return calendar.ordinalityOfUnit(
        NSDayCalendarUnit,
        NSYearCalendarUnit,
        getDate(timeUnit)
    ).toInt()

}

actual fun getDayOfMonth(timeUnit: TimeUnit): Int {
    return getComponents(timeUnit, NSDayCalendarUnit).day.toInt()
}

actual fun getDayOfWeek(timeUnit: TimeUnit): DayOfWeek {
    return DayOfWeek.getByPlatformIndex(
        getComponents(
            timeUnit,
            NSWeekdayCalendarUnit
        ).weekday.toInt()
    )
}

actual fun getFormattedDate(
    timeUnit: TimeUnit,
    pattern: Pattern,
    language: String,
    country: String
): PatternedFormattedDate {
    val date = getDate(timeUnit)
    val dateFormatter = NSDateFormatter().apply {
        timeZone = NSTimeZone.timeZoneWithName("GMT")!!
    }

    return if (pattern is Pattern.CUSTOM) {
        dateFormatter.dateFormat = pattern.value

        PatternedFormattedDate(dateFormatter.stringFromDate(date), pattern)
    } else {
        val formatter = NSISO8601DateFormatter()
        formatter.formatOptions = patternToFormattedOptions(pattern)

        PatternedFormattedDate(formatter.stringFromDate(date), pattern)
    }
}

actual fun getSecondsOfMinute(timeUnit: TimeUnit): Seconds {
    return Seconds(getComponents(timeUnit, NSSecondCalendarUnit).second)
}

actual fun getMinutesOfHour(timeUnit: TimeUnit): Minutes {
    return Minutes(getComponents(timeUnit, NSMinuteCalendarUnit).minute)
}

actual fun getHoursOfDay(timeUnit: TimeUnit): Hours {
    return Hours(getComponents(timeUnit, NSHourCalendarUnit).hour)
}

actual fun getMonth(timeUnit: TimeUnit): Month {
    return Month.getByIndex(getComponents(timeUnit, NSMonthCalendarUnit).month.toInt())
}

actual fun getYear(timeUnit: TimeUnit): CalendarYears {
    return CalendarYears(getComponents(timeUnit, NSYearCalendarUnit).year.toInt())
}

actual fun parseByCalendarUnits(
    millis: Int,
    seconds: Int,
    minutes: Int,
    hours: Int,
    days: Int,
    month: Int,
    year: Int
): TimeUnit {
    TODO()
}

internal fun getDate(timeUnit: TimeUnit): NSDate {
    return NSDate.dateWithTimeIntervalSince1970(timeUnit.toSeconds().value.toDouble())
}

private fun getComponents(timeUnit: TimeUnit, unit: NSCalendarUnit): NSDateComponents {
    val date = getDate(timeUnit)
    val calendar = getCalendar()

    return calendar.components(unit, date)
}

private fun getCalendar(): NSCalendar {
    val calendar = NSCalendar.currentCalendar
    return calendar.apply {
        NSTimeZone.timeZoneWithAbbreviation("GMT")
            ?: throw TimeParseException("Can not get time zone!")
    }
}
