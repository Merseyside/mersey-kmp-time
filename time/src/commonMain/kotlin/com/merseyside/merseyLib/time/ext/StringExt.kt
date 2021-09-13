package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.Country
import com.merseyside.merseyLib.time.Language
import com.merseyside.merseyLib.time.TimeConfiguration
import com.merseyside.merseyLib.time.TimeUnit

expect fun String.toTimeUnit(
    dateFormat: String,
    country: Country = TimeConfiguration.country,
    language: Language = TimeConfiguration.language,
    timeZone: String = TimeConfiguration.timeZone
): TimeUnit?