package com.merseyside.merseyLib.time.ranges.ext

import com.merseyside.merseyLib.time.ext.excludeMilli
import com.merseyside.merseyLib.time.ext.getNextDay
import com.merseyside.merseyLib.time.ext.toMonthRange
import com.merseyside.merseyLib.time.ranges.month.MonthRange
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.ranges.TimeRangeImpl
import com.merseyside.merseyLib.time.ranges.month.inc
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.plus

@Throws(UnsupportedOperationException::class)
fun TimeRange.splitToRangeList(gap: TimeUnit): List<TimeRange> {
    checkUndefined()

    val list = mutableListOf<TimeRange>()
    var newRange: TimeRange = TimeRangeImpl(start, start + gap)
    list.add(intersect(newRange) ?: throw Exception("Should never happened"))

    while(newRange.end < end) {
        newRange = newRange.shift(gap)
        list.add(newRange)
    }

    return list
}

@Throws(UnsupportedOperationException::class)
fun TimeRange.splitToTimeUnitList(gap: TimeUnit): List<TimeUnit> {
    checkUndefined()

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

@Throws(UnsupportedOperationException::class)
fun TimeRange.splitToMonthRanges(): List<MonthRange> {
    checkUndefined()

    val monthRanges = ArrayList<MonthRange>()

    var tempRange = start.toMonthRange()
    while(tempRange.end <= this.end) {
        monthRanges.add(tempRange)
        ++tempRange
    }

    return monthRanges
}

/**
 * Splits range by days and return list of ranges
 */
@Throws(UnsupportedOperationException::class)
fun TimeRange.splitToDayRanges(): List<TimeRange> {
    checkUndefined()

    var nextDay = start.getNextDay()
    val dayRanges = mutableListOf<TimeRange>()

    dayRanges.add(TimeRangeImpl(start, nextDay.excludeMilli()))

    while (nextDay < end) {
        val tempNextDay = nextDay.getNextDay()
        val endTime = if (tempNextDay < end) tempNextDay.excludeMilli() else end

        dayRanges.add(TimeRangeImpl(nextDay, endTime))

        nextDay = tempNextDay
    }

    return dayRanges
}