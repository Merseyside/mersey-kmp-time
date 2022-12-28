package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.plus

fun List<TimeUnit>.sum(): TimeUnit {
    var sum: TimeUnit = TimeUnit.empty()
    forEach { unit -> sum += unit }

    return sum
}

inline fun <T> Iterable<T>.sumOf(selector: (T) -> TimeUnit): TimeUnit {
    var sum: TimeUnit = TimeUnit.empty()
    for (element in this) {
        sum += selector(element)
    }
    return sum
}