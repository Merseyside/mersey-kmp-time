package com.merseyside.merseyLib.time

import com.merseyside.merseyLib.time.ext.includeLastValue
import com.merseyside.merseyLib.time.ext.toHoursMinutesOfDay
import com.merseyside.merseyLib.time.ext.toMonthRange
import com.merseyside.merseyLib.time.ext.toWeekRange
import com.merseyside.merseyLib.time.ranges.MonthRange
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.ranges.TimeUnitRange

object Time {
    enum class TimeZone { SYSTEM, GMT }

    val now: TimeUnit
        get() {
            return getCurrentTime()
        }

    val today: Days
        get() {
            return now.toDays().round()
        }

    val todayRange: TimeRange
        get() {
            return TimeUnitRange(today, today + Days(1))
        }

    fun getCurrentDayTime(timeZone: String = TimeZone.SYSTEM.toString()): TimeUnit {
        return now.toHoursMinutesOfDay(timeZone)
    }

    fun getDay(includeLastMilli: Boolean = true): TimeUnit =
        Days(1).includeLastValue(includeLastMilli)

    fun getWeek(includeLastMilli: Boolean = true): TimeUnit =
        Weeks(1).includeLastValue(includeLastMilli)

    fun getCurrentWeekRange(): TimeRange {
        return now.toWeekRange()
    }

    fun getCurrentMonthRange(): MonthRange {
        return now.toMonthRange()
    }

    fun getCurrentYear(): Years {
        return getYear(now)
    }
}

expect fun getCurrentTime(): TimeUnit

internal expect fun getDayOfMonth(
    timeUnit: TimeUnit,
    timeZone: String = TimeConfiguration.timeZone
): Days

internal expect fun getDayOfWeek(
    timeUnit: TimeUnit,
    timeZone: String = TimeConfiguration.timeZone
): DayOfWeek

//internal expect fun getDayOfWeekHuman(
//    timeUnit: TimeUnit,
//    pattern: String = TimeConfiguration.dayOfWeekPattern,
//    timeZone: String = TimeConfiguration.timeZone,
//    language: Language = TimeConfiguration.language,
//    country: String = TimeConfiguration.country
//): FormattedDate

internal expect fun getFormattedDate(
    timeUnit: TimeUnit,
    pattern: String,
    timeZone: String = TimeConfiguration.timeZone,
    language: String = TimeConfiguration.language,
    country: String = TimeConfiguration.country
): FormattedDate

internal expect fun getSecondsOfMinute(
    timeUnit: TimeUnit,
    timeZone: String = TimeConfiguration.timeZone
): Seconds

internal expect fun getMinutesOfHour(
    timeUnit: TimeUnit,
    timeZone: String = TimeConfiguration.timeZone
): Minutes

internal expect fun getHoursOfDay(
    timeUnit: TimeUnit,
    timeZone: String = TimeConfiguration.timeZone
): Hours

internal expect fun getMonth(
    timeUnit: TimeUnit,
    timeZone: String = TimeConfiguration.timeZone
): Month

internal expect fun getYear(
    timeUnit: TimeUnit,
    timeZone: String = TimeConfiguration.timeZone
): Years
