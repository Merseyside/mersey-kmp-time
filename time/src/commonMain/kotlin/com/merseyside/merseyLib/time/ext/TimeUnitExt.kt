@file:Suppress("UNCHECKED_CAST")
package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.kotlin.logger.Logger
import com.merseyside.merseyLib.time.*
import com.merseyside.merseyLib.time.calendar.Calendar
import com.merseyside.merseyLib.time.calendar.CalendarDate
import com.merseyside.merseyLib.time.ranges.month.MonthRange
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.ranges.TimeRangeImpl
import com.merseyside.merseyLib.time.ranges.ext.checkUndefined
import com.merseyside.merseyLib.time.ranges.undefined.Undefined
import com.merseyside.merseyLib.time.ranges.week.WeekRange
import com.merseyside.merseyLib.time.units.*
import com.merseyside.merseyLib.time.utils.Pattern
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

inline fun TimeUnit.checkUndefined(undefined: () -> Unit = {
    throw UnsupportedOperationException("Undefined time unit doesn't support that operation") }
) {
    if (this is Undefined) undefined()
}

inline fun <T> TimeUnit.ifUndefined(undefined: () -> T): T? {
    checkUndefined {
        return undefined()
    }

    return null
}

fun TimeUnit.toCalendarDate(): CalendarDate {
    return toCalendarBuilder().build()
}

fun TimeUnit.toCalendarBuilder(): Calendar.Builder {
    return Calendar.Builder()
        .setYear(toYearsSince1970())
        .setMonth(toMonth())
        .setDay(toDayOfMonth())
}

fun TimeUnit.toFormattedDate(
    pattern: Pattern = Time.configuration.defaultPattern,
    includeLastMilli: Boolean = true,
    language: String = Time.configuration.language,
    country: String = Time.configuration.country,
    undefinedDate: String = ""
): PatternedFormattedDate {
    checkUndefined {
        return UndefinedPatternedFormattedDate(undefinedDate)
    }

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
    language: String = Time.configuration.language,
    country: String = Time.configuration.country,
    undefinedDate: String = ""
): PatternedFormattedDate {
    return toFormattedDate(
        Pattern.CUSTOM(pattern),
        includeLastMilli,
        language,
        country,
        undefinedDate
    )
}

@Throws(UnsupportedOperationException::class)
fun TimeUnit.toZonedTimeUnit(timeZone: TimeZone = TimeZone.SYSTEM): ZonedTimeUnit {
    checkUndefined()

    return ZonedTimeUnit.ofLocalTime(this, timeZone)
}

fun <T : TimeUnit> T.abs(): T {
    return newInstance(kotlin.math.abs(value)) as T
}

fun <T : TimeUnit> T.makeNegative(): T {
    return newInstance(value * -1) as T
}

fun TimeUnit.toSecondsOfMinute(): Seconds {
    checkUndefined()
    return getSecondsOfMinute(this)
}

fun TimeUnit.toMinutesOfHour(): Minutes {
    checkUndefined()
    return getMinutesOfHour(this)
}

fun TimeUnit.toHoursOfDay(): Hours {
    checkUndefined()
    return getHoursOfDay(this)
}

fun TimeUnit.getDate(): PatternedFormattedDate {
    checkUndefined()
    return getFormattedDate(this, Time.configuration.datePattern)
}

fun TimeUnit.getStartOfDate(): Days {
    checkUndefined()
    return toDays().round()
}

fun TimeUnit.getEndOfDateTimeUnit(): TimeUnit {
    checkUndefined()
    return getNextDay().excludeMilli()
}

fun TimeUnit.getDateWithTime(): PatternedFormattedDate {
    checkUndefined()
    return getFormattedDate(this, Time.configuration.dateWithTimePattern)
}

fun TimeUnit.toHoursMinutesOfDay(): TimeUnit {
    checkUndefined()
    return (getHoursOfDay(this) + getMinutesOfHour(this))
}

fun TimeUnit.toDayOfYear(): Int {
    checkUndefined()
    return getDayOfYear(this)
}

fun TimeUnit.toYears(): Years {
    checkUndefined()
    val years = this.toDays().value / Days(Years.DAYS_CONST).value
    return Years(years.toInt())
}

fun TimeUnit.toYearsSince1970(): CalendarYears {
    checkUndefined()
    return getYear(this)
}

fun TimeUnit.toFormattedHoursMinutesOfDay(
    pattern: Pattern = Time.configuration.hoursMinutesPattern
): PatternedFormattedDate {
    checkUndefined()
    return toHoursMinutesOfDay().toFormattedDate(pattern)
}

fun TimeUnit.toDayOfWeek(): DayOfWeek {
    checkUndefined()
    return getDayOfWeek(this)
}

fun TimeUnit.toDayOfWeekHuman(
    pattern: Pattern = Time.configuration.dayOfWeekPattern,
    language: Language = Time.configuration.language,
    country: String = Time.configuration.country
): String {
    checkUndefined()
    return toFormattedDate(pattern, includeLastMilli = true, language, country).date
}

fun TimeUnit.toDayOfMonth(): Int {
    checkUndefined()
    return getDayOfMonth(this)
}

fun TimeUnit.getHumanDate(pattern: Pattern = Time.configuration.dateWithTimePattern): PatternedFormattedDate {
    checkUndefined()
    return if (!isMoreThanDay()) toFormattedHoursMinutesOfDay()
    else toFormattedDate(pattern)
}

fun TimeUnit.getHumanDate(pattern: String): PatternedFormattedDate {
    checkUndefined()
    return getHumanDate(Pattern.CUSTOM(pattern))
}

fun TimeUnit.isExpired(): Boolean {
    checkUndefined()
    return Time.nowGMT - this > TimeUnit.empty()
}

fun TimeUnit.isMoreThanDay(): Boolean {
    checkUndefined()
    return Days(1) < this
}

fun TimeUnit.toDayTimeRange(includeMilli: Boolean = true): TimeRange {
    checkUndefined()
    val day = toDays().round()
    return day.toTimeRange(startShift = Days(1).includeMilli(includeMilli))
}

fun TimeUnit.toTimeRange(shift: TimeUnit): TimeRange {
    checkUndefined()
    return toTimeRange(shift, shift)
}

fun TimeUnit.toTimeRange(
    startShift: TimeUnit = TimeUnit.empty(),
    backShift: TimeUnit = TimeUnit.empty()
): TimeRange {
    checkUndefined()
    if (startShift.isEmpty() && backShift.isEmpty())
        throw IllegalArgumentException("Pass at least one shift value!")

    return TimeRangeImpl(this - backShift, this + startShift)
}

fun TimeUnit.getNextDay(): Days {
    checkUndefined()
    var currentDay = getStartOfDate()
    return ++currentDay
}

fun TimeUnit.getPrevDay(): Days {
    checkUndefined()
    var currentDay = getStartOfDate()
    return --currentDay
}

/**
 * @return WeekRange starts from monday (00:00) ends with sunday (23:59)
 */
fun TimeUnit.toWeekRange(): WeekRange {
    checkUndefined()

    val dayOfWeek = toDayOfWeek()
    val days = toDays().round()

    val monday = days - dayOfWeek.toTimeUnit()
    val endOfSunday = monday + Days(7).excludeMilli()

    return WeekRange(monday, endOfSunday)
}

fun TimeUnit.toMonth(): Month {
    checkUndefined()
    return getMonth(this)
}

/**
 * Finds which month includes TimeUnit and returns range with start and end of the month
 */
fun TimeUnit.toMonthRange(): MonthRange {
    checkUndefined()

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
            TimeRangeImpl(min, max)
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

fun TimeUnit.moreOrEqualsYear(): Boolean {
    return Years.asDays() <= this
}

fun TimeUnit.moreOrEqualsMonth(daysInMonth: Days = Time.configuration.daysInMonth): Boolean {
    return daysInMonth <= this
}

fun TimeUnit.moreOrEqualsDay(): Boolean {
    return Days(1) <= this
}

fun TimeUnit.moreOrEqualsHour(): Boolean {
    return Hours(1) <= this
}

fun TimeUnit.moreOrEqualsMinute(): Boolean {
    return Minutes(1) <= this
}

fun TimeUnit.moreOrEqualsSecond(): Boolean {
    return Seconds(1) <= this
}

val TimeUnit.tomorrow: Days
    get() = this.toDays() + 1

val TimeUnit.yesterday: Days
    get() = this.toDays() - 1