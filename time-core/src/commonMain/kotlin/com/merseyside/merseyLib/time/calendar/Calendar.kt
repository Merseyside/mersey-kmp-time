package com.merseyside.merseyLib.time.calendar

import com.merseyside.merseyLib.time.units.CalendarYears
import com.merseyside.merseyLib.time.units.Month

class Calendar {

    class Builder {
        var years = CalendarYears()
            private set
        var month = Month.JANUARY
            private set
        var day: Int = 1
            private set

        fun setYear(calendarYears: CalendarYears): Builder {
            years = calendarYears
            return this
        }

        @Throws(IllegalArgumentException::class)
        fun setMonth(month: Month): Builder {
            if (month.days < day) throw IllegalArgumentException("Month $month doesn't have $day")
            this.month = month
            return this
        }

        @Throws(IllegalArgumentException::class)
        fun setDay(day: Int): Builder {
            if (month.days < day) throw IllegalArgumentException("Month $month doesn't have $day")
            this.day = day

            return this
        }

        fun build(): CalendarDate {
            return CalendarDate(years, month, day)
        }
    }

    companion object {
        fun build(block: Builder.() -> Unit): CalendarDate {
            return Builder().apply(block).build()
        }
    }
}