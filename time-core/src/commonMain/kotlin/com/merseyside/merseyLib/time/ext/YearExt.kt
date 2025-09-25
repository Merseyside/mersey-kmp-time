package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.units.Days
import com.merseyside.merseyLib.time.units.Years
import com.merseyside.merseyLib.time.units.times


fun Years.toTimeUnit(): Days {
    return Years.asDays() * value
}