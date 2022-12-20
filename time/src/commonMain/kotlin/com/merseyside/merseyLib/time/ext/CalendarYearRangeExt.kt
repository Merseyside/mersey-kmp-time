package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.ranges.CalendarYearsRange
import com.merseyside.merseyLib.time.units.CalendarYears

fun CalendarYearsRange.toYears(): CalendarYears {
    return start.toYears()
}