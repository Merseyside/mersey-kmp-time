package com.merseyside.merseyLib.time.units

import com.merseyside.merseyLib.time.ext.toTimeUnit

/**
 * Contains number of "years". It converts to TimeUnit by next formula
 * value * Days(365)
 */
class Years(val value: Int) {

    companion object {

        fun asDays(): Days {
            return Days(DAYS_CONST)
        }

        internal const val DAYS_CONST = 365
        internal const val LEAP_YEAR_DAYS_CONST = 366
    }
}

operator fun Years.compareTo(other: TimeUnit): Int {
    return toTimeUnit().compareTo(other)
}

operator fun Years.plus(other: Years): Years {
    return Years(value + other.value)
}

operator fun Years.minus(other: Years): Years {
    return Years(value - other.value)
}

operator fun Years.inc(): Years {
    return Years(value + 1)
}

operator fun Years.dec(): Years {
    return Years(value - 1)
}

operator fun Years.compareTo(other: Years): Int {
    return value.compareTo(other.value)
}