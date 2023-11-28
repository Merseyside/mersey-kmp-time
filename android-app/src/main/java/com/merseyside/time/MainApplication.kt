package com.merseyside.time

import android.app.Application
import com.merseyside.merseyLib.time.Time
import com.merseyside.merseyLib.time.init

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Time.init(this)
    }
}
