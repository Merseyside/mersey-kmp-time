package com.merseyside.merseyLib.time.ranges.undefined

import com.merseyside.merseyLib.time.ranges.TimeRange
import com.merseyside.merseyLib.time.units.TimeUnit
import kotlinx.serialization.Serializable

interface UndefinedTimeRange : TimeRange {

    companion object {
        fun createWithStart(start: TimeUnit): UndefinedTimeRange {
            return UndefinedTimeRangeImpl(start, Undefined.MAX())
        }

        fun createWithEnd(end: TimeUnit): UndefinedTimeRange {
            return UndefinedTimeRangeImpl(Undefined.MIN(), end)
        }
    }
}

@Serializable
class Undefined private constructor(override val millis: Long) : TimeUnit {
    override val value: Long
        get() = throw UnsupportedOperationException(errorMsg)

    override fun newInstance(value: Long): TimeUnit {
        return this
    }

    override fun newInstanceMillis(millis: Long): TimeUnit {
        return this
    }

    override fun toString(): String {
        return "UNDEFINED"
    }

    override fun equals(other: Any?): Boolean {
        return this === other
    }

    override fun hashCode(): Int {
        var result = millis.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }

    companion object Companion {
        const val errorMsg = "UNDEFINED timeUnit doesn't support any operations"

        internal fun MAX() = Undefined(Long.MAX_VALUE)
        internal fun MIN() = Undefined(Long.MIN_VALUE)
    }

}