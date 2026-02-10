package com.sinoptik_.empracticelibrary.domain.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.sinoptik_.empracticelibrary.domain.notification_manager.ChargingNotificationManager


class ChargingWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {

        ChargingNotificationManager.sendNotification(
            applicationContext,
            "Зарядка идет From WorkManager",
            "Ваше устройство подключено к сети"
        )
        Log.d("MyWorker", "Work started")
        return Result.success()
    }


}
