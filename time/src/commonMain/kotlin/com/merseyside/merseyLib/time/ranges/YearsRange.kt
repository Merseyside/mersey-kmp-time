package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.ext.toTimeUnit
import com.merseyside.merseyLib.time.units.*

class YearsRange internal constructor (
    val startYear: Years,
    val endYear: Years
): TimeRange, Iterable<Years> {

    override val start: TimeUnit by lazy { startYear.toTimeUnit() }
    override val end: TimeUnit by lazy { endYear.toTimeUnit() }

    init {
        requireValid()
    }

    override fun iterator(): Iterator<Years> {
        return YearRangeIterator()
    }

    inner class YearRangeIterator : Iterator<Years> {
        private var current: Years = startYear

        override fun hasNext(): Boolean {
            return current + Years(1) < endYear
        }

        override fun next(): Years {
            return ++current
        }

    }
}

operator fun YearsRange.plus(years: Years): YearsRange {
    return YearsRange(startYear, endYear + years)
}

operator fun YearsRange.minus(years: Years): YearsRange {
    return YearsRange(startYear, endYear + years)
}

operator fun YearsRange.inc(): YearsRange {
    return YearsRange(startYear, Years(endYear.value + 1))
}

operator fun YearsRange.dec(): YearsRange {
    return YearsRange(startYear, Years(endYear.value - 1))
}