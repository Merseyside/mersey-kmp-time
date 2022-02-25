package com.merseyside.merseyLib.time.units

import com.merseyside.merseyLib.kotlin.Logger
import com.merseyside.merseyLib.kotlin.extensions.forEachNotNull
import com.merseyside.merseyLib.time.TimeConfiguration
import com.merseyside.merseyLib.time.TimeZone
import com.merseyside.merseyLib.time.exception.TimeParseException
import com.merseyside.merseyLib.time.ext.getHumanDate
import com.merseyside.merseyLib.time.ext.toZonedTimeUnit
import com.merseyside.merseyLib.time.utils.Pattern
import kotlinx.serialization.Serializable

@Serializable
data class ZonedTimeUnit internal constructor(
    val gmtTimeUnit: TimeUnit,
    val timeZone: TimeZone
) {

    val localTimeUnit: TimeUnit
        get() = gmtTimeUnit + timeZone.offset

    override fun toString(): String {
        return "gmt = ${gmtTimeUnit.getHumanDate()} local = ${localTimeUnit.getHumanDate()} $timeZone"
    }

    companion object {

        @Throws(TimeParseException::class)
        fun ofGMT(gmtTimeUnit: TimeUnit, timeZone: TimeZone): ZonedTimeUnit {
            return ZonedTimeUnit(gmtTimeUnit, timeZone)
        }

        /**
         * Sets local time. It means that time zone offset will be deducted from local time unit
         * in order to get GMT time unit.
         */
        @Throws(TimeParseException::class)
        fun ofLocalTime(localTimeUnit: TimeUnit, timeZone: TimeZone): ZonedTimeUnit {
            return ZonedTimeUnit(localTimeUnit - timeZone.offset, timeZone)
        }

        @Throws(TimeParseException::class)
        fun of(date: String, pattern: Pattern.Offset? = null): ZonedTimeUnit {
            val patternList = if (pattern == null) TimeConfiguration.offsetPatterns
            else listOf(pattern)

            patternList.forEachNotNull {
                try {
                    return date.toZonedTimeUnit(it)
                } catch (e: TimeParseException) {
                    Logger.logErr(msg = "Can not parse with $it")
                    e.printStackTrace()
                }
            }

            Logger.logErr("Can not parse!")
            throw TimeParseException("Can not format $date with suggested patterns!")
        }
    }
}
