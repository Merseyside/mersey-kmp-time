@file:JvmName("AndroidStringExt")

package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.*
import com.merseyside.merseyLib.time.exception.TimeParseException
import com.merseyside.merseyLib.time.units.Millis
import com.merseyside.merseyLib.time.units.Seconds
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.ZonedTimeUnit
import com.merseyside.merseyLib.time.utils.Pattern
import com.merseyside.merseyLib.time.utils.patternToDateTimeFormatter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeParseException
import java.util.TimeZone as SystemTimeZone

@Throws(TimeParseException::class)
internal actual fun String.toTimeUnit(
    pattern: Pattern,
    country: Country,
    language: Language
): TimeUnit {
    try {
        if (pattern.isOffsetPattern()) throw TimeParseException(
            "$this can not be parsed with " +
                    "offset pattern. Use toZonedTimeUnit."
        )

        val formatter = patternToDateTimeFormatter(pattern)
        val instant = when (pattern) {

            is Pattern.ISO_INSTANT -> {
                Instant.from(formatter.parse(this))
            }

            is Pattern.ISO_LOCAL_DATE -> {
                val localDate = LocalDate.parse(this)
                localDate.atStartOfDay(ZoneOffset.UTC).toInstant()
            }

            is Pattern.ISO_DATE_TIME -> {
                val localDateTime = LocalDateTime.parse(this, formatter)
                localDateTime.atZone(ZoneId.of("GMT")).toInstant()
            }

            is Pattern.ISO_LOCAL_TIME, is Pattern.ISO_LOCAL_FULL_TIME -> {
                val seconds = LocalTime.parse(this, formatter).toSecondOfDay()
                return Seconds(seconds)
            }

            is Pattern.EMPTY -> throw TimeParseException("Can not parse with empty pattern")
            is Pattern.CUSTOM -> return parseCustomDate(this, pattern.value)
            else -> throw TimeParseException("Should never happened.")
        }

        return Millis(instant.toEpochMilli())
    } catch (e: DateTimeParseException) {
        throw TimeParseException(msg = "Can not parse $this with $pattern pattern!", cause = e)
    }
}

@Throws(TimeParseException::class)
internal actual fun String.toZonedTimeUnit(pattern: Pattern.Offset): ZonedTimeUnit {
    return try {
        if (!pattern.isOffsetPattern()) throw TimeParseException(
            "ZonedTimeUnit could be parsed " +
                    "only with offset pattern!"
        )

        val dateTimeFormatter = patternToDateTimeFormatter(pattern)

        val zonedDateTime = ZonedDateTime.parse(this, dateTimeFormatter)
        val millis = Millis(zonedDateTime.toInstant().toEpochMilli())
        val zoneId = zonedDateTime.zone.id
        val offset = Seconds(zonedDateTime.offset.totalSeconds)

        ZonedTimeUnit(millis, TimeZone(zoneId, offset))
    } catch (e: DateTimeParseException) {
        throw TimeParseException(msg = "Can not parse zoned time!", cause = e)
    }
}

private fun parseCustomDate(date: String, pattern: String): TimeUnit {
    return try {
        val sdf = SimpleDateFormat(pattern, getLocale()).apply {
            isLenient = false
            this.timeZone = SystemTimeZone.getTimeZone("GMT")
        }.parse(date)

        if (sdf != null) {
            Millis(sdf.time)
        } else {
            throw TimeParseException("Can not parse time. Date is null!")
        }
    } catch (e: ParseException) {
        throw TimeParseException(cause = e)
    }
}