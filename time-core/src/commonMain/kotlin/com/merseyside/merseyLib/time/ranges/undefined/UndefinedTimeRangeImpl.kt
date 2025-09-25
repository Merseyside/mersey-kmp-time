package com.merseyside.merseyLib.time.ranges.undefined

import com.merseyside.merseyLib.time.units.TimeUnit

internal class UndefinedTimeRangeImpl(
    override val start: TimeUnit,
    override val end: TimeUnit
) : UndefinedTimeRange