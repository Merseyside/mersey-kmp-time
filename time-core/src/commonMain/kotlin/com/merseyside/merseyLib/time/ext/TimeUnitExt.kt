@file:Suppress("UNCHECKED_CAST")
package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.kotlin.logger.Logger
import com.merseyside.merseyLib.time.*
import com.merseyside.merseyLib.time.calendar.Calendar
import com.merseyside.merseyLib.time.calendar.CalendarDate
import com.merseyside.merseyLib.time.format.PatternedFormattedDate
import com.merseyside.merseyLib.time.format.UndefinedPatternedFormattedDate
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.ranges.TimeRangeImpl
import com.merseyside.merseyLib.time.ranges.month.MonthRange
import com.merseyside.merseyLib.time.ranges.week.WeekRange
import com.merseyside.merseyLib.time.units.*
import com.merseyside.merseyLib.time.utils.Pattern
import com.merseyside.merseyLib.time.zone.TimeZone
import com.merseyside.merseyLib.time.zone.ZonedTimeUnit
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Checks if the TimeUnit is Undefined and executes the provided lambda if it is.
 *
 * @param undefined Lambda to be executed if the TimeUnit is Undefined.
 * @throws UnsupportedOperationException if the TimeUnit is Undefined and no lambda is provided.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testCheckUndefined
 */
inline fun TimeUnit.checkUndefined(undefined: () -> Unit = {
    throw UnsupportedOperationException("Undefined time unit doesn't support that operation") }
) {
    if (this is Undefined) undefined()
}

/**
 * Executes a lambda and returns its result if the TimeUnit is Undefined.
 *
 * @param undefined Lambda to be executed if the TimeUnit is Undefined.
 * @return The result of the lambda if the TimeUnit is Undefined, otherwise null.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testIfUndefined
 */
inline fun <T> TimeUnit.ifUndefined(undefined: () -> T): T? {
    checkUndefined {
        return undefined()
    }

    return null
}

/**
 * Converts a TimeUnit to a CalendarDate.
 *
 * @return A CalendarDate object representing the same date and time.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToCalendarDate
 */
fun TimeUnit.toCalendarDate(): CalendarDate {
    return toCalendarBuilder().build()
}

/**
 * Converts a TimeUnit to a Calendar.Builder.
 *
 * @return A Calendar.Builder object initialized with the year, month, and day of the TimeUnit.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToCalendarBuilder
 */
fun TimeUnit.toCalendarBuilder(): Calendar.Builder {
    return Calendar.Builder()
        .setYear(toYearsSince1970())
        .setMonth(toMonth())
        .setDay(toDayOfMonth())
}

/**
 * Formats the TimeUnit to a string with the specified pattern.
 *
 * @param pattern The pattern to use for formatting. See [Pattern].
 * @param includeLastMilli Whether to include the last millisecond.
 * @param language The language to use for formatting.
 * @param country The country to use for formatting.
 * @param undefinedDate The string to return if the TimeUnit is Undefined.
 * @return A [PatternedFormattedDate] object containing the formatted date string.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToFormattedDateWithPattern
 */
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

/**
 * Formats the TimeUnit to a string with the specified pattern.
 *
 * @param pattern The custom pattern string to use for formatting.
 * @param includeLastMilli Whether to include the last millisecond.
 * @param language The language to use for formatting.
 * @param country The country to use for formatting.
 * @param undefinedDate The string to return if the TimeUnit is Undefined.
 * @return A [PatternedFormattedDate] object containing the formatted date string.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToFormattedDateWithPatternString
 */
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

/**
 * Converts a local TimeUnit to a ZonedTimeUnit.
 *
 * @param timeZone The target time zone. Defaults to the system's time zone.
 * @return A ZonedTimeUnit representing the same time in the specified time zone.
 * @throws UnsupportedOperationException if the TimeUnit is Undefined.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToZonedTimeUnit
 */
@Throws(UnsupportedOperationException::class)
fun TimeUnit.toZonedTimeUnit(timeZone: TimeZone = TimeZone.SYSTEM): ZonedTimeUnit {
    checkUndefined()

    return ZonedTimeUnit.ofLocalTime(this, timeZone)
}

/**
 * Returns the absolute value of a TimeUnit.
 *
 * @return A new TimeUnit with the absolute value of the original.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testAbs
 */
fun <T : TimeUnit> T.abs(): T {
    return newInstance(kotlin.math.abs(value)) as T
}

/**
 * Makes a TimeUnit negative.
 *
 * @return A new TimeUnit with the negated value of the original.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testMakeNegative
 */
fun <T : TimeUnit> T.makeNegative(): T {
    return newInstance(value * -1) as T
}

/**
 * Returns the seconds part of a TimeUnit.
 *
 * @return A Seconds object representing the seconds part of the TimeUnit.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToSecondsOfMinute
 */
fun TimeUnit.toSecondsOfMinute(): Seconds {
    checkUndefined()
    return getSecondsOfMinute(this)
}

/**
 * Returns the minutes part of a TimeUnit.
 *
 * @return A Minutes object representing the minutes part of the TimeUnit.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToMinutesOfHour
 */
fun TimeUnit.toMinutesOfHour(): Minutes {
    checkUndefined()
    return getMinutesOfHour(this)
}

/**
 * Returns the hours part of a TimeUnit.
 *
 * @return An Hours object representing the hours part of the TimeUnit.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToHoursOfDay
 */
fun TimeUnit.toHoursOfDay(): Hours {
    checkUndefined()
    return getHoursOfDay(this)
}

/**
 * Returns the date part of a TimeUnit as a formatted string.
 *
 * @return A [PatternedFormattedDate] object containing the formatted date string.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testGetDate
 */
fun TimeUnit.getDate(): PatternedFormattedDate {
    checkUndefined()
    return getFormattedDate(this, Time.configuration.datePattern)
}

/**
 * Returns the start of the day for a TimeUnit.
 *
 * @return A Days object representing the start of the day.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testGetStartOfDate
 */
fun TimeUnit.getStartOfDate(): Days {
    checkUndefined()
    return toDays().round()
}

/**
 * Returns the end of the day for a TimeUnit.
 *
 * @return A TimeUnit object representing the end of the day.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testGetEndOfDateTimeUnit
 */
fun TimeUnit.getEndOfDateTimeUnit(): TimeUnit {
    checkUndefined()
    return getNextDay().excludeMilli()
}

/**
 * Returns the date and time part of a TimeUnit as a formatted string.
 *
 * @return A [PatternedFormattedDate] object containing the formatted date and time string.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testGetDateWithTime
 */
fun TimeUnit.getDateWithTime(): PatternedFormattedDate {
    checkUndefined()
    return getFormattedDate(this, Time.configuration.dateWithTimePattern)
}

/**
 * Returns the hours and minutes part of a TimeUnit.
 *
 * @return A TimeUnit object representing the hours and minutes of the day.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToHoursMinutesOfDay
 */
fun TimeUnit.toHoursMinutesOfDay(): TimeUnit {
    checkUndefined()
    return (getHoursOfDay(this) + getMinutesOfHour(this))
}

/**
 * Returns the day of the year for a TimeUnit.
 *
 * @return The day of the year as an integer.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToDayOfYear
 */
fun TimeUnit.toDayOfYear(): Int {
    checkUndefined()
    return getDayOfYear(this)
}

/**
 * Converts a TimeUnit to Years.
 *
 * @return A Years object representing the total number of years in the TimeUnit.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToYears
 */
fun TimeUnit.toYears(): Years {
    checkUndefined()
    val years = this.toDays().value / Days(Years.DAYS_CONST).value
    return Years(years.toInt())
}

/**
 * Returns the year for a TimeUnit since 1970.
 *
 * @return A CalendarYears object representing the year of the TimeUnit.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToYearsSince1970
 */
fun TimeUnit.toYearsSince1970(): CalendarYears {
    checkUndefined()
    return getYear(this)
}

/**
 * Returns the hours and minutes part of a TimeUnit as a formatted string.
 *
 * @param pattern The pattern to use for formatting. See [Pattern].
 * @return A [PatternedFormattedDate] object containing the formatted hours and minutes string.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToFormattedHoursMinutesOfDay
 */
fun TimeUnit.toFormattedHoursMinutesOfDay(
    pattern: Pattern = Time.configuration.hoursMinutesPattern
): PatternedFormattedDate {
    checkUndefined()
    return toHoursMinutesOfDay().toFormattedDate(pattern)
}

/**
 * Returns the day of the week for a TimeUnit.
 *
 * @return The day of the week as a [DayOfWeek] enum.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToDayOfWeek
 */
fun TimeUnit.toDayOfWeek(): DayOfWeek {
    checkUndefined()
    return getDayOfWeek(this)
}

/**
 * Returns the day of the week for a TimeUnit as a human-readable string.
 *
 * @param pattern The pattern to use for formatting. See [Pattern].
 * @param language The language to use for formatting.
 * @param country The country to use for formatting.
 * @return The day of the week as a formatted string.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToDayOfWeekHuman
 */
fun TimeUnit.toDayOfWeekHuman(
    pattern: Pattern = Time.configuration.dayOfWeekPattern,
    language: Language = Time.configuration.language,
    country: String = Time.configuration.country
): String {
    checkUndefined()
    return toFormattedDate(pattern, includeLastMilli = true, language, country).date
}

/**
 * Returns the day of the month for a TimeUnit.
 *
 * @return The day of the month as an integer.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToDayOfMonth
 */
fun TimeUnit.toDayOfMonth(): Int {
    checkUndefined()
    return getDayOfMonth(this)
}

/**
 * Returns a human-readable date string for a TimeUnit.
 *
 * @param pattern The pattern to use for formatting. See [Pattern].
 * @return A [PatternedFormattedDate] object containing the formatted date string.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testGetHumanDateWithPattern
 */
fun TimeUnit.getHumanDate(pattern: Pattern = Time.configuration.dateWithTimePattern): PatternedFormattedDate {
    checkUndefined { return UndefinedPatternedFormattedDate() }

    return if (!isMoreThanDay()) toFormattedHoursMinutesOfDay()
    else toFormattedDate(pattern)
}

/**
 * Returns a human-readable date string for a TimeUnit.
 *
 * @param pattern The custom pattern string to use for formatting.
 * @return A [PatternedFormattedDate] object containing the formatted date string.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testGetHumanDateWithPatternString
 */
fun TimeUnit.getHumanDate(pattern: String): PatternedFormattedDate {
    return getHumanDate(Pattern.CUSTOM(pattern))
}

/**
 * Checks if a TimeUnit has expired.
 *
 * @return True if the TimeUnit is in the past, false otherwise.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testIsExpired
 */
fun TimeUnit.isExpired(): Boolean {
    checkUndefined()
    return Time.nowGMT - this > TimeUnit.empty()
}

/**
 * Checks if a TimeUnit is more than one day long.
 *
 * @return True if the TimeUnit is longer than one day, false otherwise.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testIsMoreThanDay
 */
fun TimeUnit.isMoreThanDay(): Boolean {
    checkUndefined()
    return Days(1) < this
}

/**
 * Creates a TimeRange for the entire day of a TimeUnit.
 *
 * @param includeMilli Whether to include the last millisecond.
 * @return A TimeRange object from the start of the day to the end of the day.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToDayTimeRange
 */
fun TimeUnit.toDayTimeRange(includeMilli: Boolean = true): TimeRange {
    checkUndefined()
    val dayStart = getStartOfDate()
    return dayStart.toTimeRange(startShift = Days(1).includeMilli(includeMilli))
}

/**
 * Creates a TimeRange around a TimeUnit with the specified shift.
 *
 * @param shift The shift to apply to both the start and end of the TimeRange.
 * @return A TimeRange object.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToTimeRangeWithShift
 */
fun TimeUnit.toTimeRange(shift: TimeUnit): TimeRange {
    checkUndefined()
    return toTimeRange(shift, shift)
}

/**
 * Creates a TimeRange around a TimeUnit with the specified start and back shifts.
 *
 * @param startShift The shift to apply to the end of the TimeRange.
 * @param backShift The shift to apply to the start of the TimeRange.
 * @return A TimeRange object.
 * @throws IllegalArgumentException if both shifts are empty.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToTimeRangeWithStartAndBackShift
 */
fun TimeUnit.toTimeRange(
    startShift: TimeUnit = TimeUnit.empty(),
    backShift: TimeUnit = TimeUnit.empty()
): TimeRange {
    checkUndefined()
    if (startShift.isEmpty() && backShift.isEmpty())
        throw IllegalArgumentException("Pass at least one shift value!")

    return TimeRangeImpl(this - backShift, this + startShift)
}

/**
 * Returns the next day.
 *
 * @return A Days object representing the next day.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testGetNextDay
 */
fun TimeUnit.getNextDay(): Days {
    checkUndefined()
    var currentDay = getStartOfDate()
    return ++currentDay
}

/**
 * Returns the previous day.
 *
 * @return A Days object representing the previous day.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testGetPrevDay
 */
fun TimeUnit.getPrevDay(): Days {
    checkUndefined()
    var currentDay = getStartOfDate()
    return --currentDay
}

/**
 * Returns a WeekRange for the week of the TimeUnit.
 * The week starts on Monday (00:00) and ends on Sunday (23:59:59.9999).
 *
 * @return A WeekRange object.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToWeekRange
 */
fun TimeUnit.toWeekRange(): WeekRange {
    checkUndefined()

    val dayOfWeek = toDayOfWeek()
    val dayStart = getStartOfDate()

    val monday = dayStart - dayOfWeek.toTimeUnit()
    val endOfSunday = monday + Days(7).excludeMilli()

    return WeekRange(monday, endOfSunday)
}

/**
 * Returns the month of the TimeUnit.
 *
 * @return The month as a [Month] enum.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToMonth
 */
fun TimeUnit.toMonth(): Month {
    checkUndefined()
    return getMonth(this)
}

/**
 * Finds which month includes the TimeUnit and returns a range with the start and end of the month.
 *
 * @return A MonthRange object.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testToMonthRange
 */
fun TimeUnit.toMonthRange(): MonthRange {
    checkUndefined()

    val dayStart: Days = getStartOfDate()

    val dayOfMonth = getDayOfMonth(this)

    val month = getMonth(this)

    val monthStart = dayStart + 1 - dayOfMonth
    val monthEnd = monthStart + month.getDayCount(getYear(this))
    return MonthRange(monthStart, monthEnd.excludeMilli())
}

/**
 * Finds the minimum and maximum values in a list of TimeUnits and returns them as a TimeRange.
 *
 * @return A TimeRange object.
 * @throws IllegalArgumentException if the list size is less than 2.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testFindEdge
 */
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

/**
 * Checks if two TimeUnits are on the same date.
 *
 * @param other The other TimeUnit to compare with.
 * @return True if the TimeUnits are on the same date, false otherwise.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testIsTheSameDate
 */
fun TimeUnit.isTheSameDate(other: TimeUnit): Boolean {
    val thisDay = toDays()
    val otherDays = other.toDays()

    return thisDay.value == otherDays.value
}

/**
 * Logs a human-readable date string for a TimeUnit.
 *
 * @param tag The log tag.
 * @param prefix A prefix to add to the log message.
 * @return The original TimeUnit.
 */
fun <T : TimeUnit> T.logHuman(
    tag: String = this::class.simpleName ?: "TimeUnit",
    prefix: String = ""
): T {
    Logger.log(tag, "$prefix ${getHumanDate()}")
    return this
}

/**
 * Logs a human-readable date string for a list of TimeUnits.
 *
 * @param tag The log tag.
 * @param prefix A prefix to add to the log message.
 * @return The original list of TimeUnits.
 */
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

/**
 * Excludes the last millisecond from a TimeUnit.
 *
 * @return A new TimeUnit with the last millisecond subtracted.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testExcludeMilli
 */
fun TimeUnit.excludeMilli(): TimeUnit {
    return includeMilli(false)
}

/**
 * Adds one millisecond to a TimeUnit.
 *
 * @return A new TimeUnit with one millisecond added.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testAddMilli
 */
fun TimeUnit.addMilli(): TimeUnit {
    return this + Millis(1)
}

/**
 * Rounds a TimeUnit by a divider.
 *
 * @param divider The divider to round by.
 * @return A new rounded TimeUnit.
 *
 * @sample com.merseyside.merseyLib.time.ext.TimeUnitExtTest.testRoundByDivider
 */
fun TimeUnit.roundByDivider(divider: TimeUnit): TimeUnit {
    val mod = this % divider
    return if (divider / 2 > mod) {
        this - mod
    } else {
        this - mod + divider
    }
}

/**
 * Checks if a TimeUnit is not null and not empty.
 *
 * @return True if the TimeUnit is not null and not empty, false otherwise.
 */
@OptIn(ExperimentalContracts::class)
fun <T : TimeUnit> T?.isNotNullAndEmpty(): Boolean {
    contract {
        returns(true) implies (this@isNotNullAndEmpty != null)
    }

    return this != null && this.isNotEmpty()
}

/**
 * Executes a block if a TimeUnit is not null and not empty.
 *
 * @param block The block to execute.
 * @return The result of the block if the TimeUnit is not null and not empty, null otherwise.
 */
fun <T : TimeUnit> T?.isNotNullAndEmpty(block: T.() -> T): T? {
    return if (isNotNullAndEmpty()) {
        this.block()
    } else {
        null
    }
}

/**
 * Checks if a TimeUnit is greater than or equal to one year.
 *
 * @return True if the TimeUnit is greater than or equal to one year, false otherwise.
 */
fun TimeUnit.moreOrEqualsYear(): Boolean {
    return Years.asDays() <= this
}

/**
 * Checks if a TimeUnit is greater than or equal to one month.
 *
 * @param daysInMonth The number of days in the month.
 * @return True if the TimeUnit is greater than or equal to one month, false otherwise.
 */
fun TimeUnit.moreOrEqualsMonth(daysInMonth: Days = Time.configuration.daysInMonth): Boolean {
    return daysInMonth <= this
}

/**
 * Checks if a TimeUnit is greater than or equal to one day.
 *
 * @return True if the TimeUnit is greater than or equal to one day, false otherwise.
 */
fun TimeUnit.moreOrEqualsDay(): Boolean {
    return Days(1) <= this
}

/**
 * Checks if a TimeUnit is greater than or equal to one hour.
 *
 * @return True if the TimeUnit is greater than or equal to one hour, false otherwise.
 */
fun TimeUnit.moreOrEqualsHour(): Boolean {
    return Hours(1) <= this
}

/**
 * Checks if a TimeUnit is greater than or equal to one minute.
 *
 * @return True if the TimeUnit is greater than or equal to one minute, false otherwise.
 */
fun TimeUnit.moreOrEqualsMinute(): Boolean {
    return Minutes(1) <= this
}

/**
 * Checks if a TimeUnit is greater than or equal to one second.
 *
 * @return True if the TimeUnit is greater than or equal to one second, false otherwise.
 */
fun TimeUnit.moreOrEqualsSecond(): Boolean {
    return Seconds(1) <= this
}

/**
 * Returns the next day.
 */
val TimeUnit.tomorrow: Days
    get() = this.toDays() + 1

/**
 * Returns the previous day.
 */
val TimeUnit.yesterday: Days
    get() = this.toDays() - 1