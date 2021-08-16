package com.merseyside.merseyLib.time

actual fun getCurrentTimeMillis(): Millis {
    return Millis(0)
}

actual fun getDayOfMonth(timeUnit: TimeUnit, timeZone: String): Days {
    return Days(0)
}

actual fun getDayOfWeek(timeUnit: TimeUnit, timeZone: String): DayOfWeek {
    return DayOfWeek.MONDAY
}

actual fun getDayOfWeekHuman(
    timeUnit: TimeUnit,
    language: Language,
    pattern: String,
    timeZone: String
): FormattedDate {
    return FormattedDate("")
}

actual fun getFormattedDate(timeUnit: TimeUnit, pattern: String, timeZone: String): FormattedDate {
    return FormattedDate("")
}

actual fun getSecondsOfDay(timeUnit: TimeUnit, timeZone: String): Seconds {
    return Seconds(0)
}

actual fun getMinutesOfHour(timeUnit: TimeUnit, timeZone: String): Minutes {
    return Minutes(0)
}

actual fun getHoursOfDay(timeUnit: TimeUnit, timeZone: String): Hours {
    return Hours(0)
}

actual fun getMonth(timeUnit: TimeUnit, timeZone: String): Month {
    return Month.APRIL
}

actual fun getYear(timeUnit: TimeUnit, timeZone: String): Years {
    return Years(0)
}
