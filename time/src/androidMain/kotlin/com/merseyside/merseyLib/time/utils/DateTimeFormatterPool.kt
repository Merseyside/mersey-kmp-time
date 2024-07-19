package com.merseyside.merseyLib.time.utils

import com.merseyside.merseyLib.time.getLocale
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeFormatterPool {

    private val formattersPool = mutableMapOf<String, DateTimeFormatter>()

    fun clearFormatter(){
        formattersPool.clear()
    }

    fun format(pattern: String, localDateTime: LocalDateTime): String {
        var formatter = formattersPool[pattern]

        if (formatter == null) {
            val newFormatter = DateTimeFormatter.ofPattern(pattern, getLocale())
            formattersPool[pattern] = newFormatter
            formatter = newFormatter
        }

        return requireNotNull(formatter).format(localDateTime)
    }
}