@file:JvmName("AndroidTime.configurationExt")

package com.merseyside.merseyLib.time

import java.util.Locale


fun Configuration.setupWithLocale(locale: Locale) {
    language = locale.language
    country = locale.country
}

internal fun getLocale(
    language: String = Time.configuration.language,
    country: String = Time.configuration.country
): Locale {
    return Locale(language, country)
}