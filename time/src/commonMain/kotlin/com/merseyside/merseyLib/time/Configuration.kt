package com.merseyside.merseyLib.time

import com.merseyside.merseyLib.kotlin.serialization.deserialize
import com.merseyside.merseyLib.kotlin.serialization.serialize
import com.merseyside.merseyLib.time.utils.Pattern
import com.merseyside.merseyLib.time.utils.Pattern.*
import com.russhwolf.settings.Settings

class Configuration internal constructor(private val settings: Settings) {

    var checkPatternedDates = true

    var systemTimeZone: TimeZone = TimeZone.SYSTEM
    var serverTimeZone: TimeZone = TimeZone.NOT_SET_ZONE
        set(value) {
            settings.putString("server_time_zone", value.serialize())
            field = value
        }
        get() {
            return if (field != TimeZone.NOT_SET_ZONE) field
            else settings.getStringOrNull("server_time_zone")?.deserialize()
                ?: TimeZone.NOT_SET_ZONE
        }

    var language: Language = "en"
    var country: Country = "US"

    var hoursMinutesPattern = CUSTOM("HH:mm")
    var dayOfWeekPattern = CUSTOM("EE")
    var dayPattern = CUSTOM("dd")
    var monthPattern = CUSTOM("MM")
    var timePattern = CUSTOM("HH:mm:ss.SSS")
    var yearPattern = CUSTOM("yy")

    var datePattern: Pattern = CUSTOM("$dayPattern.$monthPattern.$yearPattern")
    var dateWithTimePattern: Pattern = CUSTOM("$datePattern $hoursMinutesPattern")
    var defaultPattern: Pattern = dateWithTimePattern
    var dayMonthPattern: Pattern = CUSTOM("dd MMMM")

    var zonedDefaultPattern: Offset = Offset.ISO_OFFSET_DATE_TIME

    internal var patterns = listOf(
        ISO_DATE_TIME,
        ISO_INSTANT,
        ISO_LOCAL_DATE,
        ISO_LOCAL_TIME
    )

    internal var offsetPatterns = listOf(
        Offset.ISO_OFFSET_DATE_TIME,
        Offset.ISO_OFFSET_DATE,
        Offset.ISO_OFFSET_TIME
    )

    fun addFormatPattern(pattern: Pattern) {
        patterns = patterns.toMutableList().apply { add(0, pattern) }
    }

    fun addFormatPattern(pattern: String) {
        addFormatPattern(CUSTOM(pattern))
    }

    fun addFormatPatterns(vararg patterns: String) {
        val newPatterns: MutableList<Pattern> =
            patterns.map { pattern -> CUSTOM(pattern) }.toMutableList()

        this.patterns = newPatterns.apply { addAll(this@Configuration.patterns) }
    }
}

typealias Country = String
typealias Language = String