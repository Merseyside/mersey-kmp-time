@file:JvmName("AndroidTime")

package com.merseyside.merseyLib.time

import com.merseyside.merseyLib.time.exception.TimeParseException
import com.merseyside.merseyLib.time.units.*
import com.merseyside.merseyLib.time.units.DayOfWeek
import com.merseyside.merseyLib.time.units.Month
import com.merseyside.merseyLib.time.utils.Pattern
import com.merseyside.merseyLib.time.utils.patternToDateTimeFormatter
import java.text.SimpleDateFormat
import java.time.*
import java.time.temporal.ChronoUnit
import java.time.temporal.UnsupportedTemporalTypeException
import java.util.*
import java.util.TimeZone as SystemTimeZone

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

actual fun getDayOfMonth(timeUnit: TimeUnit): Days {
    return Days(getUnit(timeUnit, Calendar.DAY_OF_MONTH))
}

@Throws(TimeParseException::class)
actual fun getFormattedDate(
    timeUnit: TimeUnit,
    pattern: Pattern,
    language: String,
    country: String
): PatternedFormattedDate {
    if (pattern.isOffsetPattern()) throw TimeParseException("Time unit can not be parsed with " +
            "offset pattern. Only ZonedTimeUnit can be parsed with this $pattern")

    return try {
        val formatter = patternToDateTimeFormatter(pattern)
        var instant = Instant.ofEpochMilli(timeUnit.millis)

        if (pattern.isTruncatesToMinutes()) {
            instant = instant.truncatedTo(ChronoUnit.MINUTES)
        }

        val formattedDate = when (pattern) {
            is Pattern.EMPTY -> throw TimeParseException("Can not parse with empty pattern!")
            is Pattern.CUSTOM -> parseCustomDate(timeUnit, pattern.value)
            else -> formatter.format(instant)
        }

        PatternedFormattedDate(formattedDate, pattern)
    } catch (e: UnsupportedTemporalTypeException) {
        throw TimeParseException("Can not parse timeUnit with $pattern pattern", e)
    }
}

actual fun getDayOfWeek(timeUnit: TimeUnit): DayOfWeek {
    val index = getUnit(timeUnit, Calendar.DAY_OF_WEEK)
    return DayOfWeek.getByPlatformIndex(index)
}

actual fun getMonth(timeUnit: TimeUnit): Month {
    return Month.getByIndex(getUnit(timeUnit, Calendar.MONTH))
}

actual fun getYear(timeUnit: TimeUnit): Years {
    return Years(getUnit(timeUnit, Calendar.YEAR))
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