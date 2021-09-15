package com.merseyside.merseyLib.time

import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object TimeConfiguration {

    var timeZone: String = Time.TimeZone.SYSTEM.toString()

    var language: Language = "en"
    var country: Country = "US"
    var hoursMinutesPattern: String = "HH:mm"
    var dayOfWeekPattern: String = "EE"
    var monthPattern: String = "MMMM"
    var yearPattern: String = "YYYY"
    var defaultPattern: String = "dd-MM-YYYY hh:mm"
    var year = Time.getCurrentYear()

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