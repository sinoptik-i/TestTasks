package com.sinoptik_.androidsdkpractice.sdk_app

import android.app.Application
import com.sinoptik_.empracticelibrary.domain.workmanager.ChargingWorkManager

class SdkApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ChargingWorkManager.schedule(this)
    }
}