package com.merseyside.merseyLib.time

import com.merseyside.merseyLib.logger.log
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object TimeConfiguration {

    var timeZone: String = Time.TimeZone.SYSTEM.toString()

    var language: Language = "en"
    var country: Country = "US"
    var divider = "."
    var hoursMinutesPattern: String = "HH:mm"
    var dayOfWeekPattern: String = "EE"
    var dayPattern: String = "dd"
    var monthPattern: String = "MM"
    var timePattern = "HH:mm:ss.SSS"
    var yearPattern: String = "yy"

    var datePattern: String = ""
        get() {
            return if (field.isEmpty()) "$dayPattern$d$monthPattern$d$yearPattern"
            else field
        }

    var dateWithTimePattern = ""
        get() {
            return if (field.isEmpty()) "$datePattern $hoursMinutesPattern"
            else field
        }

    var defaultPattern: String = ""
        get() {
            return if (field.isEmpty()) dateWithTimePattern
            else field
        }

    var year = Time.getCurrentYear()

    private val d: String
        get() {
            return divider.log("time", "get devider")
        }

    var formatPatterns = listOf(
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        "yyyy-MM-dd'T'HH:mm:ss.SSS",
        "yyyy-MM-dd'T'HH:mm:ssZ",
        "HH:mm"
    )

    fun addFormatPattern(pattern: String) {
        formatPatterns = formatPatterns.toMutableList().apply { add(pattern) }
    }
}

typealias Country = String
typealias Language = String