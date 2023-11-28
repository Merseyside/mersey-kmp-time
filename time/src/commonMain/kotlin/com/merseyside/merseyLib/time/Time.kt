package com.merseyside.merseyLib.time

import com.merseyside.merseyLib.time.exception.TimeParseException
import com.merseyside.merseyLib.time.ext.*
import com.merseyside.merseyLib.time.ranges.MonthRange
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.ranges.TimeUnitRange
import com.merseyside.merseyLib.time.ranges.WeekRange
import com.merseyside.merseyLib.time.units.*
import com.merseyside.merseyLib.time.utils.Pattern
import com.russhwolf.settings.Settings
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object Time {


    lateinit var configuration: Configuration
    
    fun onInitialized(settings: Settings) {
        configuration = Configuration(settings)
    }
    
    val now: ZonedTimeUnit
        get() = ZonedTimeUnit.ofGMT(nowGMT, configuration.systemTimeZone)

    val nowGMT: TimeUnit
        get() = getCurrentTimeGMT()

    val systemTime: TimeUnit
        get() = now.localTimeUnit

    val today: Days
        get() = systemTime.toDays().round()

    val todayRange: TimeRange
        get() = today.toDayTimeRange()

    fun getCurrentDayTime(): TimeUnit {
        return now.localTimeUnit.toHoursMinutesOfDay()
    }

    @Throws(TimeParseException::class)
    fun of(
        value: String,
        pattern: Pattern,
        country: Country = configuration.country,
        language: Language = configuration.language
    ): TimeUnit {
        return value.toTimeUnit(pattern, country, language)
    }

    @Throws(TimeParseException::class)
    fun of(
        value: String,
        pattern: String,
        country: Country = configuration.country,
        language: Language = configuration.language
    ): TimeUnit {
        return of(value, Pattern.CUSTOM(pattern), country, language)
    }

    fun getCurrentWeekRange(): WeekRange {
        return now.localTimeUnit.toWeekRange()
    }

    fun getCurrentMonthRange(): MonthRange {
        return now.localTimeUnit.toMonthRange()
    }

    fun getCurrentDayOfMonth(): Int {
        return getDayOfMonth(now.localTimeUnit)
    }

    fun getCurrentMonth(): Month {
        return getMonth(now.localTimeUnit)
    }

    fun getCurrentYear(): CalendarYears {
        return getYear(now.localTimeUnit)
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

internal expect fun getCurrentTimeGMT(): TimeUnit

internal expect fun getDayOfYear(timeUnit: TimeUnit): Int

internal expect fun getDayOfMonth(timeUnit: TimeUnit): Int

internal expect fun getDayOfWeek(timeUnit: TimeUnit): DayOfWeek

@Throws(TimeParseException::class)
internal expect fun getFormattedDate(
    timeUnit: TimeUnit,
    pattern: Pattern,
    language: String = Time.configuration.language,
    country: String = Time.configuration.country
): PatternedFormattedDate

internal expect fun getSecondsOfMinute(timeUnit: TimeUnit): Seconds

internal expect fun getMinutesOfHour(timeUnit: TimeUnit): Minutes

internal expect fun getHoursOfDay(timeUnit: TimeUnit): Hours

internal expect fun getMonth(timeUnit: TimeUnit): Month

internal expect fun getYear(timeUnit: TimeUnit): CalendarYears

internal expect fun parseByCalendarUnits(
    millis: Int = 0,
    seconds: Int = 0,
    minutes: Int = 0,
    hours: Int = 0,
    days: Int = 1,
    month: Int = 0,
    year: Int = 0
): TimeUnit
