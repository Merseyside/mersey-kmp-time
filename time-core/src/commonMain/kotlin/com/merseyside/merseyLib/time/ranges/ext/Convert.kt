package com.merseyside.merseyLib.time.ranges.ext

import com.merseyside.merseyLib.time.ext.toDayOfWeek
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.units.DayOfWeek
import com.merseyside.merseyLib.time.units.Days
import com.merseyside.merseyLib.time.units.Weeks

fun <T : TimeRange> T.toDaysOfWeek(): List<DayOfWeek> {
    checkUndefined()

    return if (getGap() > Weeks(1)) {
        DayOfWeek.entries
    } else {
        val startDay = start.toDayOfWeek()
        val endDay = end.toDayOfWeek()

        if (startDay == endDay) {
            if (getGap() < Days(1)) {
                return listOf(startDay)
            } else {
                DayOfWeek.entries
            }
        } else {
            val list = DayOfWeek.entries.toMutableList()
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
    val range = unite()
    return range.toDaysOfWeek()
}