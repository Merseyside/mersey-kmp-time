package com.merseyside.merseyLib.time

import com.merseyside.merseyLib.time.exception.TimeParseException
import com.merseyside.merseyLib.time.ext.abs
import com.merseyside.merseyLib.time.ext.toFormattedDate
import com.merseyside.merseyLib.time.units.Hours
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.utils.getSystemZone
import com.merseyside.merseyLib.time.utils.getTimeZone
import com.merseyside.merseyLib.time.utils.getZoneByOffset
import kotlinx.serialization.Serializable

@Serializable
class TimeZone internal constructor(val zoneId: String, val offset: TimeUnit) {

    companion object {
        @Throws(TimeParseException::class)
        fun of(zoneId: String): TimeZone {
            return getTimeZone(zoneId)
        }

        @Throws(TimeParseException::class)
        fun of(offset: TimeUnit): TimeZone {
            if (validateOffset(offset)) {
                return getZoneByOffset(offset)
            } else throw TimeParseException("Offset not in -18 to 18 hour range!")
        }

        /**
         * Checks offset in -18..18 hour range.
         */
        private fun validateOffset(offset: TimeUnit): Boolean {
            val absOffset = offset.abs().toHours()
            return absOffset in Hours(0)..Hours(18)
        }

        val SYSTEM: TimeZone
            get() { return getSystemZone() }

        val GMT: TimeZone
            get() { return of("GMT") }

        internal val NOT_SET_ZONE = TimeZone("GMT", TimeUnit.empty())
    }

    override fun toString(): String {
        return "zoneId = $zoneId offset = ${offset.toFormattedDate(Time.configuration.hoursMinutesPattern)}"
    }
}