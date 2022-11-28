package com.merseyside.merseyLib.time.coroutines

import com.merseyside.merseyLib.kotlin.coroutines.timer.CountDownTimer
import com.merseyside.merseyLib.kotlin.coroutines.timer.CountDownTimerListener
import com.merseyside.merseyLib.time.units.Millis
import com.merseyside.merseyLib.time.units.Seconds
import com.merseyside.merseyLib.time.units.TimeUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

fun CountDownTimer(
    delay: TimeUnit = Seconds(1),
    scope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Unconfined)
) = CountDownTimer(delay.millis, scope)

fun CountDownTimer.setCountDownTimerListener(listener: CountDownTimeUnitTimerListener) {
    setCountDownTimerListener(object : CountDownTimerListener {
        override fun onTick(timeLeft: Long, error: Exception?) {
            listener.onTick(Millis(timeLeft), error)
        }

        override fun onStop(timeLeft: Long, error: Exception?) {
            listener.onStop(Millis(timeLeft), error)
        }

        override fun onContinue() {
            listener.onContinue()
        }

        override fun onPause(remainingTime: Long) {
            listener.onPause(Millis(remainingTime))
        }

        override fun onDestroy() {
            listener.onDestroy()
        }

    })
}

fun CountDownTimer.startTimer(value: TimeUnit) {
    startTimer(value.millis)
}

interface CountDownTimeUnitTimerListener {
    fun onTick(timeLeft: TimeUnit, error: Exception? = null)
    fun onStop(timeLeft: TimeUnit, error: Exception? = null) {}
    fun onContinue() {}
    fun onPause(remainingTime: TimeUnit) {}
    fun onDestroy() {}
}