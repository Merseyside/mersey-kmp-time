package com.merseyside.merseyLib.time.ranges.years

import com.merseyside.merseyLib.time.ext.toYearsSince1970
import com.merseyside.merseyLib.time.units.CalendarYears

fun CalendarYearsRange.toYearsSince1970(): CalendarYears {
    return start.toYearsSince1970()
}