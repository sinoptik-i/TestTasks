package com.sinoptik_.androidsdkpractice.sdk_app

import android.app.Application
import com.sinoptik_.empracticelibrary.data.location.LocationStorage
import com.sinoptik_.empracticelibrary.domain.workmanager.charging.ChargingWorkScheduler
import com.sinoptik_.empracticelibrary.domain.workmanager.location.LocationWorkScheduler

class SdkApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ChargingWorkScheduler.schedule(this)
             LocationWorkScheduler.schedule(this)
        LocationStorage.init(this)
    }
}