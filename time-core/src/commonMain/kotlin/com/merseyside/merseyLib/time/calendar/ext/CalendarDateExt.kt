package com.merseyside.merseyLib.time.calendar.ext

import com.merseyside.merseyLib.time.calendar.CalendarDate
import com.merseyside.merseyLib.time.parseByCalendarUnits
import com.merseyside.merseyLib.time.units.TimeUnit

fun CalendarDate.toTimeUnit(): TimeUnit {
    return parseByCalendarUnits(year = years.value, month = month.index, days = days)
}