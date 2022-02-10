@file:JvmName("AndroidTimeConfigurationExt")

package com.merseyside.merseyLib.time

import java.util.Locale


fun TimeConfiguration.setupWithLocale(locale: Locale) {
    language = locale.language
    country = locale.country
}

internal fun getLocale(
    language: String = TimeConfiguration.language,
    country: String = TimeConfiguration.country
): Locale {
    return Locale(language, country)
}