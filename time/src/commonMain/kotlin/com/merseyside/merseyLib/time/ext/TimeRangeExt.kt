package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.kotlin.Logger
import com.merseyside.merseyLib.time.*
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.ranges.TimeUnitRange
import com.merseyside.merseyLib.time.units.*
import com.merseyside.merseyLib.time.utils.Pattern

fun TimeRange.isEmpty(): Boolean {
    return start.isEmpty() && end.isEmpty()
}

fun TimeRange.toTimeUnitRange(): TimeUnitRange {
    return if (this is TimeUnitRange) this
    else TimeUnitRange(start, end)
}

fun <T : TimeRange> List<T>.findEdge(): TimeUnitRange {
    if (isEmpty()) throw  IllegalArgumentException("List can not be empty!")
    val mutList = this.toMutableList()

    return if (size > 1) {

        val first = mutList.removeFirst()
        var min: TimeUnit = first.start
        var max: TimeUnit = first.end

        mutList.forEach { timeUnit ->
            timeUnit.start.let { if (it < min) min = it }
            timeUnit.end.let { if (it > max) max = it }
        }

        TimeUnitRange(min, max)

    } else {
        TimeUnitRange(first().start, first().end)
    }
}

fun <T : TimeRange> List<T>.findEdge(block: (TimeUnitRange) -> T): T {
    val range = findEdge()
    return block(range)
}

fun <T : TimeRange> T.toDaysOfWeek(): List<DayOfWeek> {
    return if (getGap() > Weeks(1)) {
        DayOfWeek.values().toList()
    } else {
        val startDay = start.toDayOfWeek()
        val endDay = end.toDayOfWeek()

        if (startDay == endDay) {
            if (getGap() < Days(1)) {
                return listOf(startDay)
            } else {
                DayOfWeek.values().toList()
            }
        } else {
            val list = DayOfWeek.values().toMutableList()
            if (endDay.index < startDay.index) {
                val indexRange = (endDay.index + 1 until startDay.index)
                list.filter { !indexRange.contains(it.index) }.toList()
            } else {
                val indexRange = (startDay.index..endDay.index)
                list.filter { indexRange.contains(it.index) }.toList()
            }
        }
    }
}

fun <T : TimeRange> List<T>.toDaysOfWeek(): List<DayOfWeek> {
    val range = findEdge()
    return range.toDaysOfWeek()
}

fun TimeRange.toRangeList(gap: TimeUnit): List<TimeRange> {
    val list = mutableListOf<TimeRange>()
    var newRange: TimeRange = TimeUnitRange(start, start + gap)
    list.add(intersect(newRange) ?: throw Exception("Should never happened"))

    while(newRange.end < end) {
        newRange = newRange.shift(gap)
        list.add(newRange)
    }

    return list
}

fun TimeRange.toTimeUnitList(gap: TimeUnit): List<TimeUnit> {
    val list = mutableListOf<TimeUnit>()
    var timeUnit: TimeUnit = start
    list.add(timeUnit)

    while(timeUnit < end) {
        timeUnit += gap
        if (contains(timeUnit)) {
            list.add(timeUnit)
        } else {
            end
        }
    }

    return list
}

fun TimeRange.toDayRanges(): List<TimeRange> {
    var nextDay = start.getNextDay()
    val dayRanges = mutableListOf<TimeRange>()

    dayRanges.add(TimeUnitRange(start, nextDay))

    while (nextDay < end) {
        val tempNextDay = nextDay.getNextDay()
        if (tempNextDay < end) {
            dayRanges.add(TimeUnitRange(nextDay, tempNextDay))
        } else {
            dayRanges.add(TimeUnitRange(nextDay, end))
        }

        nextDay = tempNextDay
    }

    return dayRanges
}

@Throws(IllegalArgumentException::class)
fun TimeRange.toHoursMinutesOfDay(): TimeUnitRange {
    val start = start.toHoursMinutesOfDay()
    var end = end.toHoursMinutesOfDay()

    if (end.isEmpty()) {
       end = Days(1).excludeMilli()
    }

    return TimeUnitRange(start, end)
}

fun TimeRange.isIntersect(other: TimeRange, includeLastMilli: Boolean = false): Boolean {
    return contains(other.start, includeLastMilli) || contains(other.end, includeLastMilli)
        || other.contains(start, includeLastMilli) || other.contains(end, includeLastMilli)
}

fun TimeRange.contains(other: TimeRange, includeLastMilli: Boolean = true): Boolean {
    return start <= other.start && getEndValue(includeLastMilli) >= other.getEndValue(includeLastMilli)
}

fun TimeRange.contains(timeUnit: TimeUnit, includeLastMilli: Boolean = true): Boolean {
    return timeUnit in start..getEndValue(includeLastMilli)
}

fun TimeRange.getGap(): TimeUnit {
    return end - start
}

fun TimeRange.shift(timeUnit: TimeUnit): TimeRange {
    return TimeUnitRange(start + timeUnit, end + timeUnit)
}

fun TimeRange.shiftBack(timeUnit: TimeUnit): TimeRange {
    return TimeUnitRange(start - timeUnit, end - timeUnit)
}

fun TimeRange.shiftOnGap(): TimeRange {
    val gap = getGap()
    return TimeUnitRange(start + gap, end + gap)
}

fun TimeRange.shiftBackOnGap(): TimeRange {
    val gap = getGap()
    return TimeUnitRange(start - gap, end - gap)
}

fun TimeRange.uniteWith(other: TimeRange): TimeRange {
    checkIntersection(other)

    val start = min(start, other.start)
    val end = max(end, other.end)

    return TimeUnitRange(start, end)
}

fun TimeRange.intersect(other: TimeRange, includeLastMilli: Boolean = true): TimeRange? {
    return if (isIntersect(other, includeLastMilli)) {
        when {
            contains(other, includeLastMilli) -> other
            other.contains(this, includeLastMilli) -> this
            else -> {
                val start = if (contains(other.start, includeLastMilli)) other.start else this.start
                val end = if (contains(other.end, includeLastMilli)) other.end else this.end

                TimeUnitRange(start, end)
            }
        }
    } else null
}

fun TimeRange.roundByDivider(divider: TimeUnit): TimeRange {
    return TimeUnitRange(start.roundByDivider(divider), end.roundByDivider(divider))
}

/**
 * Use $1 and $2 for start and end values accordingly.
 */
fun TimeRange.toHumanString(
    format: String = "$1 - $2",
    pattern: Pattern = TimeConfiguration.defaultPattern,
    includeLastMilli: Boolean = true
): String {
    return format.replace("$1", start.toFormattedDate(pattern).date)
        .replace("$2", getEndValue(includeLastMilli).toFormattedDate(pattern).date)
}

fun TimeRange.toHumanString(
    format: String = "$1 - $2",
    pattern: String,
    includeLastMilli: Boolean = true
): String {
    return toHumanString(format, Pattern.CUSTOM(pattern), includeLastMilli)
}

fun <T : TimeRange> T.logHuman(
    tag: String = this::class.simpleName ?: "ITimeRange",
    prefix: String = "",
    suffix: String = ""
): T {
    Logger.log(
        tag,
        "$prefix start = ${start.getHumanDate()} end = ${end.getHumanDate()} $suffix"
    )
    return this
}

fun <T : TimeRange> List<T>.logHuman(
    tag: String = this::class.simpleName ?: "ITimeRange",
    prefix: String = ""
): List<T> {
    forEach { it.logHuman(tag, prefix) }
    return this
}

fun TimeRange.getEndValue(includeLastMilli: Boolean = true): TimeUnit {
    return end.includeMilli(includeLastMilli)
}

fun TimeRange.excludeMilli(): TimeRange {
    return TimeUnitRange(start, end.excludeMilli())
}

private fun TimeRange.checkIntersection(other: TimeRange) {
    if (!isIntersect(other)) throw IllegalArgumentException("Ranges don't intersect!")
}