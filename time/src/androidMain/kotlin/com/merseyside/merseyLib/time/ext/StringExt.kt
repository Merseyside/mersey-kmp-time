@file:JvmName("AndroidStringExt")
package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.Millis
import com.merseyside.merseyLib.time.TimeUnit
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Throws(ParseException::class)
actual fun String.toTimeUnit(dateFormat: String): TimeUnit? {
    return try {
        val date = SimpleDateFormat(dateFormat, Locale.US).apply {
            isLenient = false
            timeZone = TimeZone.getTimeZone("GMT")
        }.parse(this)

        if (date != null) {
            Millis(date.time)
        } else {
            throw NullPointerException("Date can not be parse within following format")
        }
    } catch (e: ParseException) {
        null
    }
}