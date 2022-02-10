package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.units.DayOfWeek
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.plus
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.ranges.TimeUnitRange
import com.merseyside.merseyLib.time.ranges.WeekRange

fun WeekRange.toTimeUnitByDayOfWeek(dayOfWeek: DayOfWeek): TimeUnit {
    return start + dayOfWeek.toTimeUnit()
}

fun WeekRange.toTimeUnitByDayOfWeek(index: Int): TimeUnit {
    return toTimeUnitByDayOfWeek(DayOfWeek.getByIndex(index))
}

fun WeekRange.intersectWithMonth(): TimeRange {
    val currentMonth = start.toMonthRange()
    return TimeUnitRange(currentMonth.intersect(this) ?: throw Exception("Should never happened."))
}

fun WeekRange.getNextWeek(): WeekRange {
    return WeekRange(shiftOnGap())
}

fun WeekRange.getPrevWeek(): WeekRange {
    return WeekRange(shiftBackOnGap())
}