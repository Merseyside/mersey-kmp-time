@file:Suppress("UNCHECKED_CAST")
package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.kotlin.logger.Logger
import com.merseyside.merseyLib.time.*
import com.merseyside.merseyLib.time.ranges.MonthRange
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.ranges.TimeUnitRange
import com.merseyside.merseyLib.time.ranges.WeekRange
import com.merseyside.merseyLib.time.units.*
import com.merseyside.merseyLib.time.utils.Pattern
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

fun TimeUnit.toFormattedDate(
    pattern: Pattern = TimeConfiguration.defaultPattern,
    includeLastMilli: Boolean = true,
    language: String = TimeConfiguration.language,
    country: String = TimeConfiguration.country
): PatternedFormattedDate {
    return getFormattedDate(
        includeMilli(includeLastMilli),
        pattern,
        language,
        country
    )
}

fun TimeUnit.toFormattedDate(
    pattern: String,
    includeLastMilli: Boolean = true,
    language: String = TimeConfiguration.language,
    country: String = TimeConfiguration.country
): PatternedFormattedDate {
    return toFormattedDate(
        Pattern.CUSTOM(pattern),
        includeLastMilli,
        language,
        country
    )
}

fun TimeUnit.toZonedTimeUnit(timeZone: TimeZone = TimeZone.SYSTEM): ZonedTimeUnit {
    return ZonedTimeUnit.ofLocalTime(this, timeZone)
}

fun <T : TimeUnit> T.abs(): T {
    return newInstance(kotlin.math.abs(value)) as T
}

fun <T : TimeUnit> T.makeNegative(): T {
    return newInstance(value * -1) as T
}

fun TimeUnit.toSecondsOfMinute(): Seconds {
    return getSecondsOfMinute(this)
}

fun TimeUnit.toMinutesOfHour(): Minutes {
    return getMinutesOfHour(this)
}

fun TimeUnit.toHoursOfDay(): Hours {
    return getHoursOfDay(this)
}

fun TimeUnit.getDate(): PatternedFormattedDate {
    return getFormattedDate(this, TimeConfiguration.datePattern)
}

fun TimeUnit.getStartOfDate(): Days {
    return toDays().round()
}

fun TimeUnit.getEndOfDateTimeUnit(): TimeUnit {
    return getNextDay().excludeMilli()
}

fun TimeUnit.getDateWithTime(): PatternedFormattedDate {
    return getFormattedDate(this, TimeConfiguration.dateWithTimePattern)
}

fun TimeUnit.toHoursMinutesOfDay(): TimeUnit {
    return (getHoursOfDay(this) + getMinutesOfHour(this))
}

fun TimeUnit.toDayOfYear(): Days {
    return getDayOfYear(this)
}

fun TimeUnit.toYears(): CalendarYears {
    return getYear(this)
}

fun TimeUnit.toFormattedHoursMinutesOfDay(
    pattern: Pattern = TimeConfiguration.hoursMinutesPattern
): PatternedFormattedDate {
    return toHoursMinutesOfDay().toFormattedDate(pattern)
}

fun TimeUnit.toDayOfWeek(): DayOfWeek {
    return getDayOfWeek(this)
}

fun TimeUnit.toDayOfWeekHuman(
    pattern: Pattern = TimeConfiguration.dayOfWeekPattern,
    language: Language = TimeConfiguration.language,
    country: String = TimeConfiguration.country
): String {
    return toFormattedDate(pattern, includeLastMilli = true, language, country).date
}

fun TimeUnit.toDayOfMonth(): Days {
    return getDayOfMonth(this)
}

fun TimeUnit.getHumanDate(pattern: Pattern = TimeConfiguration.dateWithTimePattern): PatternedFormattedDate {
    return if (!isMoreThanDay()) toFormattedHoursMinutesOfDay()
    else toFormattedDate(pattern)
}

fun TimeUnit.getHumanDate(pattern: String): PatternedFormattedDate {
    return getHumanDate(Pattern.CUSTOM(pattern))
}

fun TimeUnit.isExpired(): Boolean {
    return Time.nowGMT - this > TimeUnit.getEmpty()
}

fun TimeUnit.isMoreThanDay(): Boolean {
    return Days(1) < this
}

fun TimeUnit.toDayTimeRange(includeMilli: Boolean = true): TimeRange {
    val day = toDays().round()
    return day.toTimeRange(startShift = Days(1).includeMilli(includeMilli))
}

fun TimeUnit.toTimeRange(
    shift: TimeUnit,
): TimeRange {
    return toTimeRange(shift, shift)
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
    var currentDay = getStartOfDate()
    return ++currentDay
}

fun TimeUnit.getPrevDay(): Days {
    var currentDay = getStartOfDate()
    return --currentDay
}

/**
 * @return WeekRange starts from monday ends with sunday
 */
fun TimeUnit.toWeekRange(): WeekRange {
    val dayOfWeek = toDayOfWeek()
    val days = toDays().round()

    val monday = days - dayOfWeek.toTimeUnit()
    val endOfSunday = monday + Days(7)

    return WeekRange(monday, endOfSunday)
}

fun TimeUnit.toMonth(): Month {
    return getMonth(this)
}

/**
 * Finds which month includes TimeUnit and returns range with start and end of the month
 */
fun TimeUnit.toMonthRange(): MonthRange {
    val days: Days = toDays().round()

    val dayOfMonth = getDayOfMonth(this)

    val month = getMonth(this)

    val monthStart = days + 1 - dayOfMonth
    val monthEnd = monthStart + month.getDayCount(getYear(this))
    return MonthRange(monthStart, monthEnd.excludeMilli())
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

fun TimeUnit.isTheSameDate(other: TimeUnit): Boolean {
    val thisDay = toDays()
    val otherDays = other.toDays()

    return thisDay.value == otherDays.value
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
    Logger.log(tag, "$prefix ${joinToString(separator = ", ") { it.getHumanDate().date }}")
    return this
}

internal fun TimeUnit.includeMilli(includeLastMilli: Boolean): TimeUnit {
    return if (!includeLastMilli) this - Millis(1)
    else this
}

fun TimeUnit.excludeMilli(): TimeUnit {
    return includeMilli(false)
}

fun TimeUnit.addMilli(): TimeUnit {
    return this + Millis(1)
}

fun TimeUnit.roundByDivider(divider: TimeUnit): TimeUnit {
    val mod = this % divider
    return if (divider / 2 > mod) {
        this - mod
    } else {
        this - mod + divider
    }
}

fun TimeUnit.moreThanYear(): Boolean {
    return Years(1).toTimeUnit() > this
}

@OptIn(ExperimentalContracts::class)
fun <T : TimeUnit> T?.isNotNullAndEmpty(): Boolean {
    contract {
        returns(true) implies (this@isNotNullAndEmpty != null)
    }

    return this != null && this.isNotEmpty()
}

fun <T : TimeUnit> T?.isNotNullAndEmpty(block: T.() -> T): T? {
    return if (isNotNullAndEmpty()) {
        this.block()
    } else {
        null
    }
}