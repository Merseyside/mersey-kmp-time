package com.merseyside.merseyLib.time.ext

import com.merseyside.merseyLib.time.TimeUnit

actual fun String.toTimeUnit(dateFormat: String): TimeUnit? {
    return TimeUnit.getEmpty()
}