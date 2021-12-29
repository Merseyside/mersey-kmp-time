@file:Suppress("UNCHECKED_CAST")

package com.merseyside.merseyLib.time

import kotlinx.serialization.Serializable

object Conversions {
    const val MILLIS_CONST = 1000L
    const val SECONDS_MINUTES_CONST = 60L
    const val HOURS_CONST = 24L
    const val WEEK_CONST = 7L
}

operator fun <T : TimeUnit> T.plus(increment: Number): T {
    return this + newInstance(increment.toLong())
}

operator fun <T : TimeUnit> T.minus(unary: Number): T {
    return this - newInstance(unary.toLong())
}

operator fun <T : TimeUnit> T.div(divider: Number): T {
    return newInstanceMillis(this.millis / divider.toLong()) as T
}

operator fun <T : TimeUnit> T.times(times: Number): T {
    return newInstanceMillis(this.millis * times.toLong()) as T
}

operator fun <T : TimeUnit> T.plus(increment: TimeUnit): T {
    return newInstanceMillis(this.millis + increment.millis) as T
}

operator fun <T : TimeUnit> T.div(divider: TimeUnit): T {
    return newInstanceMillis(this.millis / divider.millis) as T
}

operator fun <T : TimeUnit> T.times(times: TimeUnit): T {
    return newInstanceMillis(this.millis * times.millis) as T
}

operator fun <T : TimeUnit> T.minus(unary: TimeUnit): T {
    return newInstanceMillis(this.millis - unary.millis) as T
}

operator fun <T : TimeUnit> T.inc(): T {
    return this + newInstance(1)
}

operator fun <T : TimeUnit> T.dec(): T {
    return this - newInstance(1)
}

fun <T : TimeUnit> T.isEqual(other: T): Boolean {
    return this.millis == other.millis
}

operator fun <T : TimeUnit> T.compareTo(value: Number): Int {
    return this.millis.compareTo(newInstanceMillis(value.toLong()).millis)
}

operator fun <T : TimeUnit> T.rem(other: TimeUnit): T {
    return newInstanceMillis(millis % other.millis) as T
}

fun <T : TimeUnit> T.isNotEqual(other: T) = !isEqual(other)

fun <T : TimeUnit> T.round() = newInstance(value) as T

fun <T : TimeUnit> T.isRound() = newInstance(value).millis == millis

interface TimeUnit : Comparable<TimeUnit> {

    val millis: Long
    val value: Long

    fun toMillis(): Millis {
        return Millis(millis)
    }

    fun toSeconds(): Seconds {
        return Seconds(this)
    }

    fun toMinutes(): Minutes {
        return Minutes(this)
    }

    fun toHours(): Hours {
        return Hours(this)
    }

    fun toDays(): Days {
        return Days(this)
    }

    fun toWeeks(): Weeks {
        return Weeks(this)
    }

    fun newInstance(value: Long): TimeUnit

    fun newInstanceMillis(millis: Long): TimeUnit

    override fun toString(): String

    /**
     * Returns true if value == 0
     */
    fun isEmpty(): Boolean {
        return millis == 0L
    }

    fun isNotEmpty() = !isEmpty()

    override fun compareTo(other: TimeUnit): Int {
        return this.millis.compareTo(other.millis)
    }

    fun getDebugString(): String {
        return "${this::class.simpleName} = $value Millis = $millis"
    }

    fun getString(): String {
        return value.toString()
    }

    fun roundWithFraction(): TimeUnit {
        val rounded = round()
        val one = newInstance(1)
        val half = one / 2

        val fraction = this - rounded
        return if (fraction >= half) {
            rounded + one
        } else {
            rounded
        }
    }

    val intValue: Int
        get() { return value.toInt() }

    override fun equals(other: Any?): Boolean

    companion object {
        fun getEmpty(): TimeUnit {
            return Millis(0)
        }
    }
}

@Serializable
class Millis(override val millis: Long) : TimeUnit {

    override val value: Long = millis

    internal constructor(unit: TimeUnit) : this(unit.millis)

    constructor(number: Number = 0) : this(number.toLong())
    constructor(str: String) : this(str.toLong())
    constructor() : this(0)

    override fun newInstance(value: Long): TimeUnit {
        return Millis(value)
    }

    override fun newInstanceMillis(millis: Long): Millis {
        return Millis(millis)
    }

    override fun toString(): String {
        return getString()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is TimeUnit) return false
        return isEqual(other)
    }

    override fun hashCode(): Int {
        return millis.hashCode()
    }
}

@Serializable
class Seconds private constructor(override val millis: Long) : TimeUnit {

    override val value: Long = millis / Conversions.MILLIS_CONST

    internal constructor(unit: TimeUnit) : this(unit.millis)

    constructor(number: Number = 0) : this(
        Conversions.MILLIS_CONST * number.toLong()
    )

    constructor(str: String) : this(number = str.toLong())
    constructor() : this(0)

    override fun newInstance(value: Long): Seconds {
        return Seconds(Conversions.MILLIS_CONST * value)
    }

    override fun newInstanceMillis(millis: Long): Seconds {
        return Seconds(millis)
    }

    override fun toString(): String {
        return getString()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is TimeUnit) return false
        return isEqual(other)
    }

    override fun hashCode(): Int {
        return millis.hashCode()
    }
}

@Serializable
class Minutes private constructor(override val millis: Long) : TimeUnit {

    override val value: Long = toSeconds().value / Conversions.SECONDS_MINUTES_CONST

    internal constructor(unit: TimeUnit) : this(unit.millis)

    constructor(number: Number = 0) : this(
        (Seconds(Conversions.SECONDS_MINUTES_CONST).millis * number.toLong())
    )

    constructor(str: String) : this(number = str.toLong())
    constructor() : this(0)

    override fun newInstance(value: Long): TimeUnit {
        return Minutes(Seconds(Conversions.SECONDS_MINUTES_CONST).millis * value)
    }

    override fun newInstanceMillis(millis: Long): Minutes {
        return Minutes(millis)
    }

    override fun toString(): String {
        return getString()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is TimeUnit) return false
        return isEqual(other)
    }

    override fun hashCode(): Int {
        return millis.hashCode()
    }
}

@Serializable
class Hours private constructor(override val millis: Long) : TimeUnit {

    override val value: Long = toMinutes().value / Conversions.SECONDS_MINUTES_CONST

    internal constructor(unit: TimeUnit) : this(unit.millis)

    constructor(number: Number = 0) : this(
        (Minutes(Conversions.SECONDS_MINUTES_CONST).millis * number.toLong())
    )

    constructor(str: String) : this(number = str.toLong())
    constructor() : this(0)

    override fun newInstance(value: Long): TimeUnit {
        return Hours(Minutes(Conversions.SECONDS_MINUTES_CONST).millis * value)
    }

    override fun newInstanceMillis(millis: Long): Hours {
        return Hours(millis)
    }

    override fun toString(): String {
        return getString()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is TimeUnit) return false
        return isEqual(other)
    }

    override fun hashCode(): Int {
        return millis.hashCode()
    }
}

@Serializable
class Days private constructor(override val millis: Long) : TimeUnit {

    override val value: Long = toHours().value / Conversions.HOURS_CONST

    internal constructor(unit: TimeUnit) : this(unit.millis)

    constructor(number: Number = 0) : this(
        (Hours(Conversions.HOURS_CONST).millis * number.toLong())
    )

    constructor(str: String) : this(number = str.toLong())
    constructor() : this(0)

    override fun newInstance(value: Long): TimeUnit {
        return Days(Hours(Conversions.HOURS_CONST).millis * value)
    }

    override fun newInstanceMillis(millis: Long): Days {
        return Days(millis)
    }

    override fun toString(): String {
        return getString()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is TimeUnit) return false
        return isEqual(other)
    }

    override fun hashCode(): Int {
        return millis.hashCode()
    }
}

@Serializable
class Weeks private constructor(override val millis: Long) : TimeUnit {

    override val value: Long = toDays().value / Conversions.WEEK_CONST

    internal constructor(unit: TimeUnit) : this(unit.millis)

    constructor(number: Number = 0) : this(
        (Days(Conversions.WEEK_CONST).millis * number.toLong())
    )

    constructor(str: String) : this(number = str.toLong())
    constructor() : this(0)

    override fun newInstance(value: Long): TimeUnit {
        return Weeks(Days(Conversions.WEEK_CONST).millis * value)
    }

    override fun newInstanceMillis(millis: Long): Weeks {
        return Weeks(millis)
    }

    override fun toString(): String {
        return getString()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is TimeUnit) return false
        return isEqual(other)
    }

    override fun hashCode(): Int {
        return millis.hashCode()
    }
}