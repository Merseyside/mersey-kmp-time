package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.units.Years

fun Years.isLeap(): Boolean {
    return value % 4 == 0
}