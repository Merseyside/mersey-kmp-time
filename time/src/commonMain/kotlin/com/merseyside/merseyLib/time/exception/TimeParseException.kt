package com.merseyside.merseyLib.time.exception

class TimeParseException(
    msg: String = "Can not parse time!",
    cause: Throwable? = null
): Exception(msg, cause)