package com.merseyside.merseyLib.time.exception

class TimeInitializeException(
    msg: String = "Can not initialize instance!",
    cause:
    Throwable? = null
) : Exception(msg, cause)