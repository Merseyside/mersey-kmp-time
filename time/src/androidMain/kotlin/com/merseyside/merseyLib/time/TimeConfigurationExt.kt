@file:JvmName("AndroidTimeConfigurationExt")

package com.merseyside.merseyLib.time

import com.merseyside.merseyLib.time.utils.DateTimeFormatterPool
import java.util.Locale


fun Configuration.setupWithLocale(locale: Locale) {
    language = locale.language
    country = locale.country
    DateTimeFormatterPool.clearFormatters()
}

internal fun getLocale(
    language: String = Time.configuration.language,
    country: String = Time.configuration.country
): Locale {
    return Locale(language, country)
}