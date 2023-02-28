package com.merseyside.merseyLib.time.calendar

import com.merseyside.merseyLib.time.units.CalendarYears
import com.merseyside.merseyLib.time.units.Month

data class CalendarDate(
    val years: CalendarYears,
    val month: Month,
    val days: Int
)
