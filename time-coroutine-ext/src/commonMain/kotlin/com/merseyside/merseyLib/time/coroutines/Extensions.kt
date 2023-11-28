package com.merseyside.merseyLib.time.coroutines

import com.merseyside.merseyLib.kotlin.coroutines.utils.repeatUntilCancel
import com.merseyside.merseyLib.time.units.TimeUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce

suspend fun delay(timeUnit: TimeUnit) = kotlinx.coroutines.delay(timeUnit.millis)

fun <T> Flow<T>.debounce(timeUnit: TimeUnit): Flow<T> {
    return debounce(timeUnit.millis)
}

suspend fun repeatInfinite(
    delay: TimeUnit,
    repeatBlock: suspend () -> Unit
) {
    repeatUntilCancel(
        delay.millis,
        repeatBlock
    )
}