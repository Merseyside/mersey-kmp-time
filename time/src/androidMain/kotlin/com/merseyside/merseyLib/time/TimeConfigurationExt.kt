@file:JvmName("AndroidTimeConfigurationExt")
package com.merseyside.merseyLib.time

import java.util.Locale


fun TimeConfiguration.setupWithLocale(locale: Locale) {
    language = locale.language
    country = locale.country
}

fun TimeConfiguration.getLocale(): Locale {
    return Locale(language, country)
}