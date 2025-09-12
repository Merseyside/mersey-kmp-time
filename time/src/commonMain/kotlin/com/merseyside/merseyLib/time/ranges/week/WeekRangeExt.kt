package com.merseyside.merseyLib.time.ranges.week

import com.merseyside.merseyLib.time.ext.toMonthRange
import com.merseyside.merseyLib.time.ext.toTimeUnit
import com.merseyside.merseyLib.time.units.DayOfWeek
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.plus
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.ranges.TimeRangeImpl
import com.merseyside.merseyLib.time.ranges.ext.intersect
import com.merseyside.merseyLib.time.ranges.ext.shift
import com.merseyside.merseyLib.time.ranges.ext.shiftBack
import com.merseyside.merseyLib.time.units.Weeks

fun WeekRange.toTimeUnitByDayOfWeek(dayOfWeek: DayOfWeek): TimeUnit {
    return start + dayOfWeek.toTimeUnit()
}

fun WeekRange.toTimeUnitByDayOfWeek(index: Int): TimeUnit {
    return toTimeUnitByDayOfWeek(DayOfWeek.getByIndex(index))
}

fun WeekRange.intersectWithMonth(): TimeRange {
    val currentMonth = start.toMonthRange()
    return TimeRangeImpl(currentMonth.intersect(this) ?: throw Exception("Should never happened."))
}

fun WeekRange.getNextWeek(): WeekRange {
    return WeekRange(shift(Weeks(1)))
}

fun WeekRange.getPrevWeek(): WeekRange {
    return WeekRange(shiftBack(Weeks(1)))
}