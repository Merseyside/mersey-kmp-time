package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.Country
import com.merseyside.merseyLib.time.Language
import com.merseyside.merseyLib.time.TimeUnit

actual fun String.toTimeUnit(
    dateFormat: String,
    country: Country,
    language: Language,
    timeZone: String
): TimeUnit? {
    return TimeUnit.getEmpty()
}