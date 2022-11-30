package com.merseyside.time.activity.view

import android.os.Bundle
import com.merseyside.archy.presentation.activity.BaseActivity
import com.merseyside.merseyLib.kotlin.logger.log
import com.merseyside.merseyLib.time.Time
import com.merseyside.merseyLib.time.ext.toMonthRanges
import com.merseyside.merseyLib.time.ranges.YearsRange
import com.merseyside.time.R

class MainActivity: BaseActivity() {

    override fun getLayoutId() = R.layout.activity_main
    override fun getToolbar() = null
    override fun performInjection(bundle: Bundle?, vararg params: Any) {}
    override fun getFragmentContainer() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val year = Time.getCurrentYear().log(prefix = "year =")
        val monthRanges = year.toMonthRanges()

        val yearsRanges = YearsRange.getYearsRanges(2022, 2024)
        yearsRanges.flatMap { range -> range.toMonthRanges() }

    }
}