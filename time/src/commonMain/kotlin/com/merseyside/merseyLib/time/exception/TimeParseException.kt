package com.merseyside.merseyLib.time.exception

import com.merseyside.merseyLib.time.units.TimeUnit
import com.merseyside.merseyLib.time.utils.Pattern

class TimeParseException(msg: String? = null, cause: Throwable? = null
) : Exception(msg, cause) {

    constructor(
        timeUnit: TimeUnit,
        pattern: String,
        cause: Throwable? = null
    ) : this("Can not parse $timeUnit with $pattern pattern!", cause)

    constructor(
        timeUnit: TimeUnit,
        pattern: Pattern,
        cause: Throwable? = null
    ) : this(timeUnit, pattern.toString(), cause)
}