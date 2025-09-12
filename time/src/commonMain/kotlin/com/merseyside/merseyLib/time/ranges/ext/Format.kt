package com.merseyside.merseyLib.time.ranges.ext

import com.merseyside.merseyLib.time.Time
import com.merseyside.merseyLib.time.ext.toFormattedDate
import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.utils.Pattern

/**
 * Use $1 and $2 for start and end values accordingly.
 */
fun TimeRange.toHumanString(
    format: String = "$1 - $2",
    pattern: Pattern = Time.configuration.defaultPattern,
    includeLastMilli: Boolean = true,
    undefinedDate: String = ""
): String {
    return format.replace(
        "$1",
        start.toFormattedDate(pattern = pattern, undefinedDate = undefinedDate).date
    ).replace(
        "$2",
        getEndValue(includeLastMilli).toFormattedDate(pattern, undefinedDate = undefinedDate).date
    )
}

fun TimeRange.toHumanString(
    format: String = "$1 - $2",
    pattern: String,
    includeLastMilli: Boolean = true,
    undefinedDate: String = ""
): String {
    return toHumanString(format, Pattern.CUSTOM(pattern), includeLastMilli, undefinedDate)
}