package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.ranges.CalendarYearsRange
import com.merseyside.merseyLib.time.units.CalendarYears

fun CalendarYearsRange.toYearsSince1970(): CalendarYears {
    return start.toYearsSince1970()
}