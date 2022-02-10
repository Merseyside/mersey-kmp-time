package com.merseyside.merseyLib.time.utils

import com.merseyside.merseyLib.time.utils.Pattern.*
import com.merseyside.merseyLib.time.utils.Pattern.Offset.*
import com.merseyside.merseyLib.time.getLocale
import java.time.format.DateTimeFormatter

internal fun patternToDateTimeFormatter(pattern: Pattern): DateTimeFormatter {
    return when(pattern) {
        is CUSTOM -> DateTimeFormatter.ofPattern(pattern.value, getLocale())
        is ISO_DATE_TIME -> DateTimeFormatter.ISO_DATE_TIME
        is ISO_INSTANT -> DateTimeFormatter.ISO_INSTANT

        is ISO_OFFSET_DATE_TIME -> DateTimeFormatter.ISO_OFFSET_DATE_TIME
        is ISO_OFFSET_DATE -> DateTimeFormatter.ISO_OFFSET_DATE
        is ISO_OFFSET_TIME -> DateTimeFormatter.ISO_OFFSET_TIME
        is ISO_OFFSET_FULL_TIME -> DateTimeFormatter.ISO_OFFSET_TIME

        is ISO_LOCAL_DATE -> DateTimeFormatter.ISO_LOCAL_DATE
        is ISO_LOCAL_TIME -> DateTimeFormatter.ISO_LOCAL_TIME
        is ISO_LOCAL_FULL_TIME -> DateTimeFormatter.ISO_LOCAL_TIME
    }
}