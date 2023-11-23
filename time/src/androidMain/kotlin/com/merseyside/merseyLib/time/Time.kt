@file:JvmName("AndroidTime")

package com.merseyside.merseyLib.time

import android.content.Context
import com.merseyside.merseyLib.kotlin.logger.log
import com.merseyside.merseyLib.time.exception.TimeParseException
import com.merseyside.merseyLib.time.units.*
import com.merseyside.merseyLib.time.utils.Pattern
import com.merseyside.merseyLib.time.utils.patternToDateTimeFormatter
import com.russhwolf.settings.SharedPreferencesSettings
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.time.temporal.UnsupportedTemporalTypeException
import java.util.*
import java.util.TimeZone as SystemTimeZone

fun Time.init(context: Context) {
    val prefs = context.getSharedPreferences("mersey_time_prefs", Context.MODE_PRIVATE)
    val settings = SharedPreferencesSettings(delegate = prefs)
    onInitialized(settings)
}

actual fun getCurrentTimeGMT(): TimeUnit {
    return Millis(System.currentTimeMillis())
}

actual fun getSecondsOfMinute(timeUnit: TimeUnit): Seconds {
    return Seconds(getUnit(timeUnit, Calendar.SECOND))
}

actual fun getMinutesOfHour(timeUnit: TimeUnit): Minutes {
    return Minutes(getUnit(timeUnit, Calendar.MINUTE))
}

actual fun getHoursOfDay(timeUnit: TimeUnit): Hours {
    return Hours(getUnit(timeUnit, Calendar.HOUR_OF_DAY))
}

actual fun getDayOfYear(timeUnit: TimeUnit): Int {
    return getUnit(timeUnit, Calendar.DAY_OF_YEAR)
}

actual fun getDayOfMonth(timeUnit: TimeUnit): Int {
    return getUnit(timeUnit, Calendar.DAY_OF_MONTH)
}

@Throws(TimeParseException::class)
actual fun getFormattedDate(
    timeUnit: TimeUnit,
    pattern: Pattern,
    language: String,
    country: String
): PatternedFormattedDate {

    if (pattern.isOffsetPattern()) throw TimeParseException(
        "Time unit can not be parsed with " +
                "offset pattern. Only ZonedTimeUnit can be parsed with this $pattern"
    )

    return try {
        val formattedDate = when (pattern) {
            is Pattern.EMPTY -> throw TimeParseException("Can not parse with empty pattern!")
            is Pattern.CUSTOM -> parseCustomDate(timeUnit, pattern.value)
            else -> {
                val formatter = patternToDateTimeFormatter(pattern)
                var instant = Instant.ofEpochMilli(timeUnit.millis)

                var ldt = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"))

                formatter.format(ldt)
            }
        }

        PatternedFormattedDate(formattedDate, pattern)
    } catch (e: UnsupportedTemporalTypeException) {
        throw TimeParseException("Can not parse timeUnit $timeUnit with $pattern pattern", e)
    }
}

actual fun getDayOfWeek(timeUnit: TimeUnit): DayOfWeek {
    val index = getUnit(timeUnit, Calendar.DAY_OF_WEEK)
    return DayOfWeek.getByPlatformIndex(index)
}

actual fun getMonth(timeUnit: TimeUnit): Month {
    return Month.getByIndex(getUnit(timeUnit, Calendar.MONTH))
}

actual fun getYear(timeUnit: TimeUnit): CalendarYears {
    return CalendarYears(getUnit(timeUnit, Calendar.YEAR))
}

private fun getUnit(
    timeUnit: TimeUnit,
    unit: Int
): Int {
    val calendar = Calendar.getInstance()
    calendar.time = Date(timeUnit.millis)
    calendar.timeZone = SystemTimeZone.getTimeZone(TimeZone.GMT.zoneId)

    return calendar.get(unit)
}

@Throws(TimeParseException::class)
private fun parseCustomDate(timeUnit: TimeUnit, pattern: String): String {
    return try {
        val sdf = SimpleDateFormat(pattern, getLocale())

        val netDate = Date(timeUnit.millis)

        sdf.timeZone = SystemTimeZone.getTimeZone(TimeZone.GMT.zoneId)
        sdf.format(netDate)
    } catch (e: Exception) {
        e.printStackTrace()
        throw TimeParseException()
    }
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
    val calendar = Calendar.getInstance(SystemTimeZone.getTimeZone(TimeZone.GMT.zoneId)).apply {
        set(Calendar.MILLISECOND, millis)
        set(Calendar.SECOND, seconds)
        set(Calendar.MINUTE, minutes)
        set(Calendar.HOUR, hours)
        set(Calendar.DAY_OF_MONTH, days)
        set(Calendar.MONTH, month)
        set(Calendar.YEAR, year)
    }

    return Millis(calendar.timeInMillis)

}