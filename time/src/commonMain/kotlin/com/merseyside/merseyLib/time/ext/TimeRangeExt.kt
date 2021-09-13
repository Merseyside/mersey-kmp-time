package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.logger.Logger
import com.merseyside.merseyLib.time.*
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.ranges.TimeUnitRange

fun TimeRange.toTimeUnitRange(): TimeUnitRange {
    return TimeUnitRange(start, end)
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
    return TimeUnitRange(start.toHoursMinutesOfDay(), end.toHoursMinutesOfDay())
}

fun TimeRange.isIntersect(other: TimeRange, includeLast: Boolean = true): Boolean {
    return start <= other.getEndValue(includeLast) && start >= other.start ||
            getEndValue(includeLast) > other.start && getEndValue(includeLast) < other.getEndValue(includeLast) ||
            start >= other.start && getEndValue(includeLast) <= other.getEndValue(includeLast)
}

fun TimeRange.contains(other: TimeRange, includeLast: Boolean = true): Boolean {
    return start <= other.start && getEndValue(includeLast) >= other.getEndValue(includeLast)
}

fun TimeRange.contains(timeUnit: TimeUnit, includeLast: Boolean = true): Boolean {
    return timeUnit in start..getEndValue(includeLast)
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

private fun TimeRange.checkIntersection(other: TimeRange) {
    if (!isIntersect(other)) throw IllegalArgumentException("Ranges don't intersect!")
}

/**
 * Use %1 and %2 for start and end values accordingly.
 */
fun TimeRange.toHumanString(
    format: String,
    pattern: String = TimeConfiguration.defaultPattern
): String {
    return format.replace("$1", start.toFormattedDate(pattern).value)
        .replace("$2", end.toFormattedDate(pattern).value)
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

fun TimeRange.getEndValue(includeLast: Boolean = true): TimeUnit {
    return if (includeLast) end
    else end.toEndValue()
}