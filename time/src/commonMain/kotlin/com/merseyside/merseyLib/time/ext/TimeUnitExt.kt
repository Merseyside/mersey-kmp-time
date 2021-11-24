package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.logger.Logger
import com.merseyside.merseyLib.time.*
import com.merseyside.merseyLib.time.ranges.MonthRange
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.ranges.TimeUnitRange
import com.merseyside.merseyLib.time.ranges.WeekRange

fun TimeUnit.toFormattedDate(
    pattern: String = TimeConfiguration.defaultPattern,
    timeZone: String = TimeConfiguration.timeZone,
    language: String = TimeConfiguration.language,
    country: String = TimeConfiguration.country,
    includeLastMilli: Boolean = true
): FormattedDate {
    return getFormattedDate(
        includeLastValue(includeLastMilli),
        pattern,
        timeZone,
        language,
        country
    )
}

fun TimeUnit.toSecondsOfMinute(timeZone: String = TimeConfiguration.timeZone): Seconds {
    return getSecondsOfMinute(this, timeZone)
}

fun TimeUnit.toMinutesOfHour(timeZone: String = TimeConfiguration.timeZone): Minutes {
    return getMinutesOfHour(this, timeZone)
}

fun TimeUnit.toHoursOfDay(timeZone: String = TimeConfiguration.timeZone): Hours {
    return getHoursOfDay(this, timeZone)
}

fun TimeUnit.getDate(): FormattedDate {
    return getFormattedDate(this, TimeConfiguration.datePattern)
}

fun TimeUnit.getEndOfDateTimeUnit(): TimeUnit {
    val currentDays = toDays().round()
    return currentDays + Days(1) - Millis(1)
}

fun TimeUnit.getDateWithTime(): FormattedDate {
    return getFormattedDate(this, TimeConfiguration.dateWithTimePattern)
}

fun TimeUnit.toHoursMinutesOfDay(timeZone: String = TimeConfiguration.timeZone): TimeUnit {
    return (getHoursOfDay(this, timeZone) + getMinutesOfHour(this, timeZone))
}

fun TimeUnit.toFormattedHoursMinutesOfDay(
    pattern: String = TimeConfiguration.hoursMinutesPattern,
    timeZone: String = TimeConfiguration.timeZone
): FormattedDate {
    return toHoursMinutesOfDay(timeZone).toFormattedDate(pattern)
}

fun TimeUnit.toDayOfWeek(timeZone: String = TimeConfiguration.timeZone): DayOfWeek {
    return getDayOfWeek(this, timeZone)
}

fun TimeUnit.toDayOfWeekHuman(
    pattern: String = TimeConfiguration.dayOfWeekPattern,
    timeZone: String = TimeConfiguration.timeZone,
    language: Language = TimeConfiguration.language,
    country: String = TimeConfiguration.country
): String {
    return toFormattedDate(pattern, timeZone, language, country).value
}

fun TimeUnit.toDayOfMonth(timeZone: String = TimeConfiguration.timeZone): Days {
    return getDayOfMonth(this, timeZone)
}

fun TimeUnit.getHumanDate(pattern: String = TimeConfiguration.defaultPattern): FormattedDate {
    return if (!isMoreThanDay()) toFormattedHoursMinutesOfDay()
    else toFormattedDate(pattern)
}

fun TimeUnit.isExpired(): Boolean {
    return Time.now - this > TimeUnit.getEmpty()
}

fun TimeUnit.isMoreThanDay(): Boolean {
    return Days(1) < this
}

fun TimeUnit.toDayTimeRange(): TimeRange {
    val day = toDays().round()
    return TimeUnitRange(day, day + Days(1))
}

fun TimeUnit.toTimeRange(
    startShift: TimeUnit = TimeUnit.getEmpty(),
    backShift: TimeUnit = TimeUnit.getEmpty()
): TimeRange {
    if (startShift.isEmpty() && backShift.isEmpty())
        throw IllegalArgumentException("Pass at least one shift value!")

    return TimeUnitRange(this - backShift, this + startShift)
}

fun TimeUnit.getNextDay(): Days {
    var currentDay = toDays().round()
    return ++currentDay
}

fun TimeUnit.getPrevDay(): Days {
    var currentDay = toDays().round()
    return --currentDay
}

fun TimeUnit.toWeekRange(): WeekRange {
    val dayOfWeek = toDayOfWeek()
    val days = toDays().round()

    val monday = days - dayOfWeek.toTimeUnit()
    val endOfSunday = monday + Days(7)

    return WeekRange(monday, endOfSunday)
}

fun TimeUnit.toMonth(timeZone: String = TimeConfiguration.timeZone): Month {
    return getMonth(this, timeZone)
}

fun TimeUnit.toMonthRange(): MonthRange {
    val days: Days = toDays().round()

    val dayOfMonth = getDayOfMonth(this)

    val month = getMonth(this)

    val monthStart = days + 1 - dayOfMonth
    val monthEnd = monthStart + month.getDayCount(getYear(this))
    return MonthRange(monthStart, monthEnd)
}

fun <T : TimeUnit> List<T>.findEdge(): TimeRange {
    if (size > 1) {
        val min = minByOrNull { it.millis }
        val max = maxByOrNull { it.millis }

        return if (min != null && max != null) {
            TimeUnitRange(min, max)
        } else {
            throw IllegalArgumentException("Should never calls")
        }
    } else throw IllegalArgumentException("Size must be > 1")
}

fun <T : TimeUnit> T.logHuman(
    tag: String = this::class.simpleName ?: "TimeUnit",
    prefix: String = ""
): T {
    Logger.log(tag, "$prefix ${getHumanDate()}")
    return this
}

fun <T : TimeUnit> List<T>.logHuman(
    tag: String = this::class.simpleName ?: "TimeUnit",
    prefix: String = ""
): List<T> {
    Logger.log(tag, "$prefix ${joinToString(separator = ", ") { it.getHumanDate().value }}")
    return this
}

fun TimeUnit.includeLastValue(includeLastMilli: Boolean): TimeUnit {
    return if (!includeLastMilli) this - Millis(1)
    else this
}

fun TimeUnit.roundByDivider(divider: TimeUnit): TimeUnit {
    val mod = this % divider
    return if (divider / 2 > mod) {
        this - mod
    } else {
        this - mod + divider
    }
}