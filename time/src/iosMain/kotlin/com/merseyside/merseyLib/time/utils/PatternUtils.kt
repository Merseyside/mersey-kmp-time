package com.merseyside.merseyLib.time.utils

import com.merseyside.merseyLib.time.exception.TimeParseException
import platform.Foundation.*

internal fun patternToFormattedOptions(pattern: Pattern): ULong {
    return when(pattern) {
        is Pattern.ISO_DATE -> NSISO8601DateFormatWithFullDate
                .or(NSISO8601DateFormatWithDashSeparatorInDate)

        is Pattern.ISO_DATE_TIME -> {
            patternToFormattedOptions(Pattern.ISO_DATE)
                .or(patternToFormattedOptions(Pattern.ISO_LOCAL_FULL_TIME))
        }

        is Pattern.ISO_INSTANT -> {
            patternToFormattedOptions(Pattern.ISO_DATE_TIME)
                .or(NSISO8601DateFormatWithTimeZone)
                .or(NSISO8601DateFormatWithColonSeparatorInTimeZone)
        }
        is Pattern.ISO_OFFSET_DATE_TIME -> {
            NSISO8601DateFormatWithInternetDateTime
        }
        is Pattern.ISO_OFFSET_DATE -> {
            patternToFormattedOptions(Pattern.ISO_DATE)
                .or(NSISO8601DateFormatWithTimeZone)
                .or(NSISO8601DateFormatWithColonSeparatorInTimeZone)
        }
        is Pattern.ISO_OFFSET_TIME -> {
            patternToFormattedOptions(Pattern.ISO_LOCAL_TIME)
                .or(NSISO8601DateFormatWithTimeZone)
                .or(NSISO8601DateFormatWithColonSeparatorInTimeZone)
        }
        is Pattern.ISO_OFFSET_FULL_TIME -> {
            patternToFormattedOptions(Pattern.ISO_LOCAL_FULL_TIME)
                .or(NSISO8601DateFormatWithTimeZone)
                .or(NSISO8601DateFormatWithColonSeparatorInTimeZone)
        }
        is Pattern.ISO_LOCAL_DATE -> {
            NSISO8601DateFormatWithFullDate.or(NSISO8601DateFormatWithDashSeparatorInDate)
        }
        is Pattern.ISO_LOCAL_TIME -> {
            NSISO8601DateFormatWithTime
                .or(NSISO8601DateFormatWithColonSeparatorInTime)
        }
        is Pattern.ISO_LOCAL_FULL_TIME -> {
            NSISO8601DateFormatWithFullTime
                .or(NSISO8601DateFormatWithColonSeparatorInTime)
        }

        else -> throw TimeParseException()
    }
}