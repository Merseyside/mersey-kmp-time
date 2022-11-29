package com.merseyside.merseyLib.time

import com.merseyside.merseyLib.time.exception.TimeParseException
import com.merseyside.merseyLib.time.ext.*
import com.merseyside.merseyLib.time.ranges.MonthRange
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.ranges.TimeUnitRange
import com.merseyside.merseyLib.time.ranges.WeekRange
import com.merseyside.merseyLib.time.units.*
import com.merseyside.merseyLib.time.utils.Pattern
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

object Time {
    val now: ZonedTimeUnit
        get() = nowZoned(TimeConfiguration.timeZone)

    val nowGMT: TimeUnit
        get() = getCurrentTimeGMT()

    val systemTime: TimeUnit
        get() = now.localTimeUnit

    val today: Days
        get() = systemTime.toDays().round()

    val todayRange: TimeRange
        get() = today.toDayTimeRange()

    fun nowZoned(timeZone: TimeZone): ZonedTimeUnit {
        return getCurrentZonedTime(timeZone)
    }

    fun getCurrentDayTime(timeZone: TimeZone = TimeConfiguration.timeZone): TimeUnit {
        return getCurrentZonedTime(timeZone).localTimeUnit.toHoursMinutesOfDay()
    }

    @Throws(TimeParseException::class)
    fun of(
        value: String,
        pattern: Pattern,
        country: Country = TimeConfiguration.country,
        language: Language = TimeConfiguration.language
    ): TimeUnit {
        return value.toTimeUnit(pattern, country, language)
    }

    @Throws(TimeParseException::class)
    fun of(
        value: String,
        pattern: String,
        country: Country = TimeConfiguration.country,
        language: Language = TimeConfiguration.language
    ): TimeUnit {
        return of(value, Pattern.CUSTOM(pattern), country, language)
    }

    fun getCurrentWeekRange(timeZone: TimeZone = TimeConfiguration.timeZone): WeekRange {
        return getCurrentZonedTime(timeZone).localTimeUnit.toWeekRange()
    }

    fun getCurrentMonthRange(timeZone: TimeZone = TimeConfiguration.timeZone): MonthRange {
        return getCurrentZonedTime(timeZone).localTimeUnit.toMonthRange()
    }

    fun getCurrentDayOfMonth(timeZone: TimeZone = TimeConfiguration.timeZone): Days {
        return getDayOfMonth(getCurrentZonedTime(timeZone).localTimeUnit)
    }

    fun getCurrentMonth(timeZone: TimeZone = TimeConfiguration.timeZone): Month {
        return getMonth(getCurrentZonedTime(timeZone).localTimeUnit)
    }

    fun getCurrentYear(timeZone: TimeZone = TimeConfiguration.timeZone): Years {
        return getYear(getCurrentZonedTime(timeZone).localTimeUnit)
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

    private fun getCurrentZonedTime(timeZone: TimeZone): ZonedTimeUnit {
        return ZonedTimeUnit.ofGMT(nowGMT, timeZone)
    }
}

internal expect fun getCurrentTimeGMT(): TimeUnit

internal expect fun getDayOfYear(timeUnit: TimeUnit): Days

internal expect fun getDayOfMonth(timeUnit: TimeUnit): Days

internal expect fun getDayOfWeek(timeUnit: TimeUnit): DayOfWeek

@Throws(TimeParseException::class)
internal expect fun getFormattedDate(
    timeUnit: TimeUnit,
    pattern: Pattern,
    language: String = TimeConfiguration.language,
    country: String = TimeConfiguration.country
): PatternedFormattedDate

internal expect fun getSecondsOfMinute(timeUnit: TimeUnit): Seconds

internal expect fun getMinutesOfHour(timeUnit: TimeUnit): Minutes

internal expect fun getHoursOfDay(timeUnit: TimeUnit): Hours

internal expect fun getMonth(timeUnit: TimeUnit): Month

internal expect fun getYear(timeUnit: TimeUnit): Years

internal expect fun parseByCalendarUnits(
    millis: Int = 0,
    seconds: Int = 0,
    minutes: Int = 0,
    hours: Int = 0,
    days: Int = 1,
    month: Int = 0,
    year: Int = 0
): TimeUnit
