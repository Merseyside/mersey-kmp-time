package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.ext.*
import com.merseyside.merseyLib.time.units.*

/**
 * Presents only ONE year with start and end TimeUnits
 */
class YearsRange internal constructor(
    override val start: TimeUnit,
    override val end: TimeUnit
): TimeRange {

    init {
        requireValid()
    }

    companion object {
        fun from(years: Years): YearsRange {
            return years.toYearsRange()
        }

        fun from(yearsInt: Int): YearsRange {
            return from(Years(yearsInt))
        }

        fun getYearsRanges(from: Int, to: Int): List<YearsRange> {
            return (from..to).map { year -> from(year) }
        }
    }
}

operator fun YearsRange.plus(years: Years): TimeRange {
    return TimeUnitRange(start, end + years.toTimeUnit())
}

operator fun YearsRange.minus(years: Years): TimeRange {
    return TimeUnitRange(start, end - years.toTimeUnit())
}

operator fun YearsRange.inc(): YearsRange {
    val year = toYears().value + 1
    return YearsRange.from(year)
}

operator fun YearsRange.dec(): YearsRange {
    val year = toYears().value - 1
    return YearsRange.from(year)
}