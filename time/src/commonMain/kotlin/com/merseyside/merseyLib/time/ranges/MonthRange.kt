package com.merseyside.merseyLib.time.ranges

import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.ext.getNextMonth
import com.merseyside.merseyLib.time.ext.getPrevMonth
import com.merseyside.merseyLib.time.ext.toHumanString
import com.merseyside.merseyLib.time.utils.Pattern

import kotlinx.serialization.Serializable


/**
 * Presents only One month with start and end in TimeUnits
 */
@Serializable
class MonthRange internal constructor(
    override val start: TimeUnit,
    override val end: TimeUnit
): TimeRange {

    override fun toString(): String {
        return toHumanString(pattern = Pattern.ISO_INSTANT)
    }
}

operator fun MonthRange.plus(count: Int): MonthRange {
    var month = this
    for (i in 1..count) month = month.getNextMonth()
    return month
}

operator fun MonthRange.minus(count: Int): MonthRange {
    var month = this
    for (i in 1..count) month = month.getPrevMonth()
    return month
}

operator fun MonthRange.inc(): MonthRange = this.getNextMonth()
operator fun MonthRange.dec(): MonthRange = this.getPrevMonth()