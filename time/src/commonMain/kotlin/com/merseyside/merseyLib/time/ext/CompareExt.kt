package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.units.*
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
fun <T : TimeUnit> TimeUnit.castTo(clazz: KClass<out T>): T {
    return when(clazz) {
        Millis::class -> toMillis()
        Seconds::class -> toSeconds()
        Minutes::class -> toMinutes()
        Hours::class -> toHours()
        Days::class -> toDays()
        Weeks::class -> toWeeks()

        else -> toMillis()
    } as T
}

inline fun <reified T : TimeUnit> T.compareTo(other: TimeUnit): Int {
    val second: T = other.castTo(this::class)
    return value.compareTo(second.value)
}

inline fun <reified T : TimeUnit> T.lessThen(other: TimeUnit): Boolean {
    return when (compareTo<T>(other)) {
        -1 -> true
        else -> false
    }
}

inline fun <reified T : TimeUnit> T.equalsOrLessThen(other: TimeUnit): Boolean {
    return when (compareTo<T>(other)) {
        -1, 0 -> true
        else -> false
    }
}

inline fun <reified T : TimeUnit> T.equalsTo(other: TimeUnit): Boolean {
    return when (compareTo<T>(other)) {
        0 -> true
        else -> false
    }
}

inline fun <reified T : TimeUnit> T.notEqualsTo(other: TimeUnit): Boolean {
    return !equalsTo(other)
}

inline fun <reified T : TimeUnit> T.moreThen(other: TimeUnit): Boolean {
    return when (compareTo<T>(other)) {
        1 -> true
        else -> false
    }
}

inline fun <reified T : TimeUnit> T.equalsOrMoreThen(other: TimeUnit): Boolean {
    return when (compareTo<T>(other)) {
        0, 1 -> true
        else -> false
    }
}