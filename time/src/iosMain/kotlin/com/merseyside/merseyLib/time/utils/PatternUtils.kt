package com.merseyside.merseyLib.time.utils

import com.merseyside.merseyLib.time.utils.Pattern.*
import com.merseyside.merseyLib.time.exception.TimeParseException
import platform.Foundation.*

internal fun patternToFormattedOptions(pattern: Pattern): ULong {
    return when(pattern) {
        is ISO_DATE_TIME -> {
            NSISO8601DateFormatWithFullDate
                .or(NSISO8601DateFormatWithDashSeparatorInDate)
                .or(patternToFormattedOptions(ISO_LOCAL_FULL_TIME))
        }

        is ISO_INSTANT -> {
            patternToFormattedOptions(ISO_DATE_TIME)
                .or(NSISO8601DateFormatWithTimeZone)
                .or(NSISO8601DateFormatWithColonSeparatorInTimeZone)
        }
        is Offset.ISO_OFFSET_DATE_TIME -> {
            NSISO8601DateFormatWithInternetDateTime
                .or(NSISO8601DateFormatWithTimeZone)
        }
        is Offset.ISO_OFFSET_DATE -> {
            NSISO8601DateFormatWithFullDate
                .or(NSISO8601DateFormatWithDashSeparatorInDate)
                .or(NSISO8601DateFormatWithTimeZone)
                .or(NSISO8601DateFormatWithColonSeparatorInTimeZone)
        }
        is Offset.ISO_OFFSET_TIME -> {
            patternToFormattedOptions(ISO_LOCAL_TIME)
                .or(NSISO8601DateFormatWithTimeZone)
                .or(NSISO8601DateFormatWithColonSeparatorInTimeZone)
        }
        is Offset.ISO_OFFSET_FULL_TIME -> {
            patternToFormattedOptions(ISO_LOCAL_FULL_TIME)
                .or(NSISO8601DateFormatWithTimeZone)
                .or(NSISO8601DateFormatWithColonSeparatorInTimeZone)
        }
        is ISO_LOCAL_DATE -> {
            NSISO8601DateFormatWithFullDate.or(NSISO8601DateFormatWithDashSeparatorInDate)
        }
        is ISO_LOCAL_TIME -> {
            NSISO8601DateFormatWithTime
                .or(NSISO8601DateFormatWithColonSeparatorInTime)
        }
        is ISO_LOCAL_FULL_TIME -> {
            NSISO8601DateFormatWithFullTime
                .or(NSISO8601DateFormatWithColonSeparatorInTime)
        }

        is EMPTY -> throw TimeParseException("Can not obtain formattedOptions with empty pattern!")
        else -> throw TimeParseException() //TODO handle Custom pattern
    }
}