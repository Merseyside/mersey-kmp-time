package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.ranges.YearsRange
import com.merseyside.merseyLib.time.units.Years
import com.merseyside.merseyLib.time.units.minus

fun YearsRange.toYears(): Years {
    return start.toYears()
}