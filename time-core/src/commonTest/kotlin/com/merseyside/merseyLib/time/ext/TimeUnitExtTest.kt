package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.units.Days
import com.merseyside.merseyLib.time.units.Seconds
import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.units.Undefined
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import com.merseyside.merseyLib.time.units.Minutes
import com.merseyside.merseyLib.time.units.Hours
import com.merseyside.merseyLib.time.DayOfWeek
import com.merseyside.merseyLib.time.Time
import com.merseyside.merseyLib.time.month.Month

class TimeUnitExtTest {

    @Test
    fun testCheckUndefined() {
        val undefined = Undefined()
        assertFailsWith<UnsupportedOperationException> { undefined.checkUndefined() }

        val defined = Seconds(5)
        defined.checkUndefined()
    }

    @Test
    fun testIfUndefined() {
        val undefined = Undefined()
        val result = undefined.ifUndefined { "is undefined" }
        assertEquals("is undefined", result)

        val defined = Seconds(5)
        val nullResult = defined.ifUndefined { "is undefined" }
        assertNull(nullResult)
    }

    @Test
    fun testToCalendarDate() {
        val timeUnit = Days(19000) // 2022-01-18
        val calendarDate = timeUnit.toCalendarDate()

        assertEquals(2022, calendarDate.year.value)
        assertEquals(1, calendarDate.month.value)
        assertEquals(18, calendarDate.day.value)
    }

    @Test
    fun testToCalendarBuilder() {
        val timeUnit = Days(19000) // 2022-01-18
        val builder = timeUnit.toCalendarBuilder()
        val calendarDate = builder.build()

        assertEquals(2022, calendarDate.year.value)
        assertEquals(1, calendarDate.month.value)
        assertEquals(18, calendarDate.day.value)
    }

    @Test
    fun testToFormattedDateWithPattern() {
        val timeUnit = Days(19000)
        val formattedDate = timeUnit.toFormattedDate("YYYY-MM-dd")

        assertEquals("2022-01-18", formattedDate.date)
    }

     @Test
    fun testToZonedTimeUnit() {
        // TODO: Need to implement this test
    }

    @Test
    fun testAbs() {
        val negativeSeconds = Seconds(-5)
        val positiveSeconds = negativeSeconds.abs()

        assertEquals(5, positiveSeconds.value)

        val positiveMinutes = Seconds(10)
        val samePositiveMinutes = positiveMinutes.abs()
        assertEquals(10, samePositiveMinutes.value)
    }

    @Test
    fun testMakeNegative() {
        val positiveSeconds = Seconds(5)
        val negativeSeconds = positiveSeconds.makeNegative()

        assertEquals(-5, negativeSeconds.value)

        val negativeMinutes = Seconds(-10)
        val sameNegativeMinutes = negativeMinutes.makeNegative()
        assertEquals(-10, sameNegativeMinutes.value)
    }

    @Test
    fun testToSecondsOfMinute() {
        val timeUnit = Seconds(125)
        val seconds = timeUnit.toSecondsOfMinute()
        assertEquals(5, seconds.value)
    }

    @Test
    fun testToMinutesOfHour() {
        val timeUnit = Minutes(75)
        val minutes = timeUnit.toMinutesOfHour()
        assertEquals(15, minutes.value)
    }

    @Test
    fun testToHoursOfDay() {
        val timeUnit = Hours(26)
        val hours = timeUnit.toHoursOfDay()
        assertEquals(2, hours.value)
    }

    @Test
    fun testGetDate() {
        val timeUnit = Days(19000) // 2022-01-18
        val formattedDate = timeUnit.getDate()
        assertEquals("18.01.2022", formattedDate.date)
    }

    @Test
    fun testGetStartOfDate() {
        val timeUnit = Days(19000) + Hours(10)
        val startOfDate = timeUnit.getStartOfDate()
        assertEquals(Days(19000), startOfDate)
    }

    @Test
    fun testGetEndOfDateTimeUnit() {
        val timeUnit = Days(19000)
        val endOfDay = timeUnit.getEndOfDateTimeUnit()
        assertEquals(Days(19001) - Seconds(1), endOfDay)
    }

    @Test
    fun testGetDateWithTime() {
        val timeUnit = Days(19000) + Hours(15) + Minutes(30)
        val formattedDate = timeUnit.getDateWithTime()
        assertEquals("18.01.2022 15:30", formattedDate.date)
    }

    @Test
    fun testToHoursMinutesOfDay() {
        val timeUnit = Days(1) + Hours(10) + Minutes(25)
        val hoursMinutes = timeUnit.toHoursMinutesOfDay()
        assertEquals(Hours(10) + Minutes(25), hoursMinutes)
    }

    @Test
    fun testToDayOfYear() {
        val timeUnit = Days(19000) // 2022-01-18
        val dayOfYear = timeUnit.toDayOfYear()
        assertEquals(18, dayOfYear)
    }

    @Test
    fun testToYears() {
        val timeUnit = Days(365 * 2)
        val years = timeUnit.toYears()
        assertEquals(2, years.value)
    }

    @Test
    fun testToYearsSince1970() {
        val timeUnit = Days(19000) // 2022-01-18
        val years = timeUnit.toYearsSince1970()
        assertEquals(2022, years.value)
    }

    @Test
    fun testToFormattedHoursMinutesOfDay() {
        val timeUnit = Hours(10) + Minutes(25)
        val formatted = timeUnit.toFormattedHoursMinutesOfDay()
        assertEquals("10:25", formatted.date)
    }

    @Test
    fun testToDayOfWeek() {
        val timeUnit = Days(19000) // Tuesday
        val dayOfWeek = timeUnit.toDayOfWeek()
        assertEquals(DayOfWeek.TUESDAY, dayOfWeek)
    }

    @Test
    fun testToDayOfWeekHuman() {
        val timeUnit = Days(19000) // Tuesday
        val human = timeUnit.toDayOfWeekHuman()
        assertEquals("Tuesday", human)
    }

    @Test
    fun testToDayOfMonth() {
        val timeUnit = Days(19000) // 2022-01-18
        val dayOfMonth = timeUnit.toDayOfMonth()
        assertEquals(18, dayOfMonth)
    }

    @Test
    fun testGetHumanDateWithPattern() {
        val now = Time.now()
        val formattedNow = now.getHumanDate("HH:mm")
        val time = now.toHoursMinutesOfDay().toFormattedDate("HH:mm")
        assertEquals(time.date, formattedNow.date)

        val old = Days(1) * 2
        val formattedOld = old.getHumanDate("dd.MM.YYYY")
        assertEquals(old.toFormattedDate("dd.MM.YYYY").date, formattedOld.date)
    }

    @Test
    fun testIsExpired() {
        val past = Seconds(1)
        assertEquals(true, past.isExpired())

        val future = Time.now() + Days(1)
        assertEquals(false, future.isExpired())
    }

    @Test
    fun testIsMoreThanDay() {
        val more = Days(1) + Seconds(1)
        assertEquals(true, more.isMoreThanDay())

        val less = Hours(12)
        assertEquals(false, less.isMoreThanDay())
    }

    @Test
    fun testToDayTimeRange() {
        val timeUnit = Days(19000)
        val range = timeUnit.toDayTimeRange()
        assertEquals(Days(19000), range.start)
        assertEquals(Days(19001), range.end)
    }

    @Test
    fun testToTimeRangeWithShift() {
        val timeUnit = Days(1)
        val range = timeUnit.toTimeRange(Hours(1))
        assertEquals(Days(1) - Hours(1), range.start)
        assertEquals(Days(1) + Hours(1), range.end)
    }

    @Test
    fun testToTimeRangeWithStartAndBackShift() {
        val timeUnit = Days(1)
        val range = timeUnit.toTimeRange(startShift = Hours(2), backShift = Hours(1))
        assertEquals(Days(1) - Hours(1), range.start)
        assertEquals(Days(1) + Hours(2), range.end)
    }

    @Test
    fun testGetNextDay() {
        val day = Days(1)
        assertEquals(Days(2), day.getNextDay())
    }

    @Test
    fun testGetPrevDay() {
        val day = Days(1)
        assertEquals(Days(0), day.getPrevDay())
    }

    @Test
    fun testToWeekRange() {
        val timeUnit = Days(19000) // Tuesday 2022-01-18
        val weekRange = timeUnit.toWeekRange()

        assertEquals(Days(18999), weekRange.start) // Monday 2022-01-17
        assertEquals(Days(19006) - Seconds(1), weekRange.end) // Sunday 2022-01-23
    }

    @Test
    fun testToMonth() {
        val timeUnit = Days(19000) // January
        val month = timeUnit.toMonth()
        assertEquals(Month.JANUARY, month)
    }

    @Test
    fun testToMonthRange() {
        val timeUnit = Days(19000) // 2022-01-18
        val monthRange = timeUnit.toMonthRange()

        assertEquals(Days(18983), monthRange.start) // 2022-01-01
        assertEquals(Days(19014) - Seconds(1), monthRange.end) // 2022-01-31
    }

    @Test
    fun testFindEdge() {
        val list = listOf(Seconds(1), Seconds(5), Seconds(3))
        val range = list.findEdge()
        assertEquals(Seconds(1), range.start)
        assertEquals(Seconds(5), range.end)
    }

    @Test
    fun testIsTheSameDate() {
        val day1 = Days(1) + Hours(5)
        val day2 = Days(1) + Hours(10)
        assertEquals(true, day1.isTheSameDate(day2))

        val day3 = Days(2)
        assertEquals(false, day1.isTheSameDate(day3))
    }

    @Test
    fun testExcludeMilli() {
        val seconds = Seconds(1)
        assertEquals(Seconds(1) - TimeUnit.milli(1), seconds.excludeMilli())
    }

    @Test
    fun testAddMilli() {
        val seconds = Seconds(1)
        assertEquals(Seconds(1) + TimeUnit.milli(1), seconds.addMilli())
    }

    @Test
    fun testRoundByDivider() {
        val time = Hours(1) + Minutes(20)
        val rounded = time.roundByDivider(Hours(1))
        assertEquals(Hours(1), rounded)

        val time2 = Hours(1) + Minutes(40)
        val rounded2 = time2.roundByDivider(Hours(1))
        assertEquals(Hours(2), rounded2)
    }

    @Test
    fun testIsNotNullAndEmpty() {
        val empty: TimeUnit? = null
        assertEquals(false, empty.isNotNullAndEmpty())

        val zero = Seconds(0)
        assertEquals(false, zero.isNotNullAndEmpty())

        val notEmpty = Seconds(1)
        assertEquals(true, notEmpty.isNotNullAndEmpty())
    }

    @Test
    fun testMoreOrEqualsYear() {
        assertEquals(true, (Days(365)).moreOrEqualsYear())
        assertEquals(false, (Days(364)).moreOrEqualsYear())
    }

    @Test
    fun testMoreOrEqualsMonth() {
        assertEquals(true, (Days(30)).moreOrEqualsMonth())
        assertEquals(false, (Days(29)).moreOrEqualsMonth())
    }

    @Test
    fun testMoreOrEqualsDay() {
        assertEquals(true, (Hours(24)).moreOrEqualsDay())
        assertEquals(false, (Hours(23)).moreOrEqualsDay())
    }

    @Test
    fun testMoreOrEqualsHour() {
        assertEquals(true, (Minutes(60)).moreOrEqualsHour())
        assertEquals(false, (Minutes(59)).moreOrEqualsHour())
    }

    @Test
    fun testMoreOrEqualsMinute() {
        assertEquals(true, (Seconds(60)).moreOrEqualsMinute())
        assertEquals(false, (Seconds(59)).moreOrEqualsMinute())
    }

    @Test
    fun testMoreOrEqualsSecond() {
        assertEquals(true, (TimeUnit.milli(1000)).moreOrEqualsSecond())
        assertEquals(false, (TimeUnit.milli(999)).moreOrEqualsSecond())
    }

    @Test
    fun testTomorrow() {
        val day = Days(1)
        assertEquals(Days(2), day.tomorrow)
    }

    @Test
    fun testYesterday() {
        val day = Days(1)
        assertEquals(Days(0), day.yesterday)
    }
}