package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.units.Days
import com.merseyside.merseyLib.time.units.Years
import com.merseyside.merseyLib.time.units.times

private const val YEAR_CONST = 365
private const val LEAP_YEAR_CONST = 366

fun Years.toTimeUnit(): Days {
    return Days(YEAR_CONST) * value
}