package com.merseyside.merseyLib.time.coroutines

import com.merseyside.merseyLib.time.units.TimeUnit

suspend fun delay(timeUnit: TimeUnit) = kotlinx.coroutines.delay(timeUnit.millis)

suspend fun debounce(
    waitMs: TimeUnit,
    destinationFunction: suspend () -> Unit
) = com.merseyside.merseyLib.kotlin.coroutines.utils.debounce(
    waitMs.millis,
    destinationFunction
)

suspend fun repeatInfinite(
    delay: TimeUnit,
    repeatBlock: suspend () -> Unit
) {
    com.merseyside.merseyLib.kotlin.coroutines.utils.repeatInfinite(
        delay.millis,
        repeatBlock
    )
}