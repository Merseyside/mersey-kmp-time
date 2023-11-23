package com.merseyside.merseyLib.time

import com.merseyside.merseyLib.time.exception.TimeInitializeException
import com.merseyside.merseyLib.time.exception.TimeParseException
import com.merseyside.merseyLib.time.ext.toTimeUnit
import com.merseyside.merseyLib.time.ext.toZonedTimeUnit
import com.merseyside.merseyLib.time.utils.Pattern

class PatternedFormattedDate internal constructor(
    date: String,
    val pattern: Pattern
) : FormattedDate(date) {

    internal constructor(formattedDate: FormattedDate, pattern: Pattern): this(formattedDate.date, pattern)

    companion object {

        @Throws(TimeInitializeException::class)
        fun of(date: String, pattern: Pattern): PatternedFormattedDate {
            return try {
                requireValid(date, pattern)
                PatternedFormattedDate(date, pattern)
            } catch (e: TimeParseException) {
                throw TimeInitializeException("Could not parse date!", e)
            }
        }

        @Throws(TimeInitializeException::class)
        fun of(date: String, pattern: String): PatternedFormattedDate {
            return of(date, Pattern.CUSTOM(pattern))
        }

        @Throws(TimeInitializeException::class)
        fun of(formattedDate: FormattedDate, pattern: Pattern): PatternedFormattedDate {
            return of(formattedDate.date, pattern)
        }

        @Throws(TimeInitializeException::class)
        fun of(formattedDate: FormattedDate, pattern: String): PatternedFormattedDate {
            return of(formattedDate.date, Pattern.CUSTOM(pattern))
        }

        private fun requireValid(date: String, pattern: Pattern) {
            if (Time.configuration.checkPatternedDates) {
                if (pattern is Pattern.EMPTY || pattern is Pattern.CUSTOM && pattern.value.isEmpty())
                    throw TimeInitializeException("Pattern is empty!")

                if (pattern.isOffsetPattern()) {
                    date.toZonedTimeUnit(pattern)
                } else {
                    date.toTimeUnit(pattern)
                }
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PatternedFormattedDate) return false
        if (!super.equals(other)) return false

        if (pattern != other.pattern) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + pattern.hashCode()
        return result
    }

}