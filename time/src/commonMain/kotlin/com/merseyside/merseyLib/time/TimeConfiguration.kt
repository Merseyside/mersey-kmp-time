package com.merseyside.merseyLib.time

import com.merseyside.merseyLib.time.utils.Pattern
import kotlin.native.concurrent.ThreadLocal
import com.merseyside.merseyLib.time.utils.Pattern.*

@ThreadLocal
object TimeConfiguration {

    var checkPatternedDates = true

    var timeZone: TimeZone = TimeZone.NOT_SET_ZONE
        get() {
            if (field == TimeZone.NOT_SET_ZONE) {
                field = TimeZone.SYSTEM
            }
            return field
        }

    var language: Language = "en"
    var country: Country = "US"

    var hoursMinutesPattern = CUSTOM("HH:mm")
    var dayOfWeekPattern = CUSTOM("EE")
    var dayPattern = CUSTOM("dd")
    var monthPattern = CUSTOM("MM")
    var timePattern = CUSTOM("HH:mm:ss.SSS")
    var yearPattern = CUSTOM("yy")

    var datePattern: Pattern = EMPTY
        get() {
            return if (field == EMPTY) {
                CUSTOM("$dayPattern.$monthPattern.$yearPattern")
            } else field
        }

    var dateWithTimePattern: Pattern = EMPTY
        get() {
            return if (field == EMPTY) {
                CUSTOM("$datePattern $hoursMinutesPattern")
            } else field
        }

    var defaultPattern: Pattern = EMPTY
        get() {
            return if (field == EMPTY) {
                dateWithTimePattern
            } else field
        }

    var zonedDefaultPattern: Offset = Offset.ISO_OFFSET_DATE_TIME

    var dayMonthPattern: Pattern = EMPTY
        get() = if (field == EMPTY) {
            CUSTOM("dd MMMM")
        } else field

    internal var patterns = listOf(
        ISO_DATE_TIME,
        ISO_INSTANT,
        ISO_LOCAL_DATE,
        ISO_LOCAL_TIME,
        ISO_LOCAL_FULL_TIME
    )

    internal var offsetPatterns = listOf(
        Offset.ISO_OFFSET_DATE_TIME,
        Offset.ISO_OFFSET_DATE,
        Offset.ISO_OFFSET_TIME,
        Offset.ISO_OFFSET_FULL_TIME
    )

    fun addFormatPattern(pattern: Pattern) {
        patterns = patterns.toMutableList().apply { add(0, pattern) }
    }

    fun addFormatPattern(pattern: String) {
        addFormatPattern(CUSTOM(pattern))
    }

    fun addFormatPatterns(vararg patterns: String) {
        val newPatterns: MutableList<Pattern> = patterns.map { CUSTOM(it) }.toMutableList()
        this.patterns = newPatterns.apply { addAll(this@TimeConfiguration.patterns) }
    }
}

typealias Country = String
typealias Language = String