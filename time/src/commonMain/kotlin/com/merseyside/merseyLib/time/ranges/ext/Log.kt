package com.merseyside.merseyLib.time.ranges.ext

import com.merseyside.merseyLib.kotlin.logger.Logger
import com.merseyside.merseyLib.time.ext.getHumanDate
import com.merseyside.merseyLib.time.ext.ifUndefined
import com.merseyside.merseyLib.time.ranges.TimeRange

fun <T : TimeRange> T.logHuman(
    tag: String = this::class.simpleName ?: "TimeRange",
    prefix: String = "",
    suffix: String = ""
): T {
    val start = start.ifUndefined { "undefined" } ?: start.getHumanDate()

    val end = end.ifUndefined { "undefined" } ?: end.getHumanDate()

    Logger.log(tag, "$prefix start = $start end = $end $suffix")
    return this
}

fun <T : TimeRange> List<T>.logHuman(
    tag: String = this::class.simpleName ?: "TimeRange",
    prefix: String = ""
): List<T> {
    forEach { it.logHuman(tag, prefix) }
    return this
}