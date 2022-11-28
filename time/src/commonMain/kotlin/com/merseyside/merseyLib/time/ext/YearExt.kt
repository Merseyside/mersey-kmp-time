package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.parseByCalendarUnits
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.Years

fun Years.isLeap(): Boolean {
    return value % 4 == 0
}

fun Years.toTimeUnit(): TimeUnit {
    return parseByCalendarUnits(year = value)
}

fun Years.getDaysCount(): Int {
    return if (isLeap()) 366 else 365
}