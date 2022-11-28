package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.ranges.YearsRange
import com.merseyside.merseyLib.time.units.Years
import com.merseyside.merseyLib.time.units.minus

fun YearsRange.hasLeapYears(): Boolean {
    return any { it.isLeap() }
}

fun YearsRange.getLeapYears(): List<Years> {
    return filter { it.isLeap() }
}

fun YearsRange.getNotLeapYears(): List<Years> {
    return filter { !it.isLeap() }
}

fun YearsRange.size(): Int {
    return (endYear - startYear).value + 1
}