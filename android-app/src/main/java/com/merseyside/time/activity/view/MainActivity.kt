package com.merseyside.time.activity.view

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.merseyside.archy.presentation.activity.BaseActivity
import com.merseyside.merseyLib.kotlin.logger.log
import com.merseyside.merseyLib.time.Time
import com.merseyside.merseyLib.time.calendar.Calendar
import com.merseyside.merseyLib.time.calendar.ext.toTimeUnit
import com.merseyside.merseyLib.time.ext.logHuman
import com.merseyside.merseyLib.time.ext.toMonthRanges
import com.merseyside.merseyLib.time.ranges.ext.logHuman
import com.merseyside.merseyLib.time.ranges.ext.shiftBack
import com.merseyside.merseyLib.time.ranges.ext.splitToMonthRanges
import com.merseyside.merseyLib.time.ranges.ext.toHumanString
import com.merseyside.merseyLib.time.ranges.undefined.UndefinedTimeRange
import com.merseyside.merseyLib.time.ranges.years.CalendarYearsRange
import com.merseyside.merseyLib.time.units.*
import com.merseyside.time.R

class MainActivity: BaseActivity() {

    override fun getLayoutId() = R.layout.activity_main
    override fun getMainToolbar(): Toolbar? {
        return null
    }

    override fun performInjection(bundle: Bundle?, vararg params: Any) {}
    override fun getFragmentContainer() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Time.now.log("test", "now =")

        val year = Time.getCurrentYear().log(prefix = "year =")
        val monthRanges = year.toMonthRanges()

        val yearsRanges = CalendarYearsRange.getYearsRanges(2022, 2024)
        yearsRanges.flatMap { range -> range.splitToMonthRanges() }

        (Years(1) > Days(364)).log()

        val calendarDate = Calendar.build {
            setYear(CalendarYears(2024))
            setMonth(Month.JUNE)
            setDay(15)
        }

        calendarDate.toTimeUnit().logHuman()

        val undefinedTimeRange = UndefinedTimeRange.createWithStart(Time.now.localTimeUnit).logHuman()
        undefinedTimeRange.shiftBack(Day).logHuman()

        undefinedTimeRange.toHumanString(undefinedDate = "undefined end").log()
    }
}