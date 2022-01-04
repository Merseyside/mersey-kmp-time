package com.merseyside.merseyLib.time

import com.merseyside.merseyLib.time.ext.includeLastValue
import com.merseyside.merseyLib.time.ext.toHoursMinutesOfDay
import com.merseyside.merseyLib.time.ext.toMonthRange
import com.merseyside.merseyLib.time.ext.toWeekRange
import com.merseyside.merseyLib.time.ranges.MonthRange
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.ranges.TimeUnitRange
import com.merseyside.merseyLib.time.ranges.WeekRange
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

object Time {
    enum class TimeZone { SYSTEM, GMT }

    val now: TimeUnit
        get() = getCurrentTime()

    val today: Days
        get() {
            return now.toDays().round()
        }

    val todayRange: TimeRange
        get() {
            return TimeUnitRange(today, today + Days(1))
        }

    fun getCurrentDayTime(timeZone: String = TimeConfiguration.timeZone): TimeUnit {
        return now.toHoursMinutesOfDay(timeZone)
    }

    fun getDay(includeLastMilli: Boolean = true): TimeUnit =
        Days(1).includeLastValue(includeLastMilli)

    fun getWeek(includeLastMilli: Boolean = true): TimeUnit =
        Weeks(1).includeLastValue(includeLastMilli)

    fun getCurrentWeekRange(): WeekRange {
        return now.toWeekRange()
    }

    fun getCurrentMonthRange(): MonthRange {
        return now.toMonthRange()
    }

    fun getCurrentDayOfMonth(): Days {
        return getDayOfMonth(now)
    }

    fun getCurrentMonth(): Month {
        return getMonth(now)
    }

    fun getCurrentYear(): Years {
        return getYear(now)
    }

    val serializersModule = SerializersModule {
        polymorphic(TimeUnit::class) {
            subclass(Millis::class)
            subclass(Seconds::class)
            subclass(Minutes::class)
            subclass(Hours::class)
            subclass(Days::class)
            subclass(Weeks::class)
        }

        polymorphic(TimeRange::class) {
            subclass(TimeUnitRange::class)
            subclass(WeekRange::class)
            subclass(MonthRange::class)
        }
    }
}

internal expect fun getCurrentTime(): TimeUnit

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

internal expect fun getDayOfWeek(
    timeUnit: TimeUnit,
    timeZone: String = TimeConfiguration.timeZone
): DayOfWeek

internal expect fun getDayOfMonth(
    timeUnit: TimeUnit,
    timeZone: String = TimeConfiguration.timeZone
): Days

internal expect fun getDayOfYear(
    timeUnit: TimeUnit,
    timeZone: String = TimeConfiguration.timeZone
): Days

internal expect fun getMonth(
    timeUnit: TimeUnit,
    timeZone: String = TimeConfiguration.timeZone
): Month

internal expect fun getYear(
    timeUnit: TimeUnit,
    timeZone: String = TimeConfiguration.timeZone
): Years

internal expect fun getTimeZoneOffset(timeZone: String): TimeUnit
