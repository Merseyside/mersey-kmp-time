@file:JvmName("AndroidStringExt")
package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

actual fun String.toTimeUnit(
    dateFormat: String,
    country: Country,
    language: Language,
    timeZone: String
): TimeUnit? {
    return try {
        val date = SimpleDateFormat(dateFormat, getLocale()).apply {
            isLenient = false
            this.timeZone = TimeZone.getTimeZone(timeZone)
        }.parse(this)

        if (date != null) {
            Millis(date.time)
        } else {
            null
        }
    } catch (e: ParseException) {
        null
    }
}