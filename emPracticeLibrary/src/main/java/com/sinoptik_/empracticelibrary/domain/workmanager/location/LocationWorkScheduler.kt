package com.sinoptik_.empracticelibrary.domain.workmanager.location

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object LocationWorkScheduler {
    private const val WORK_NAME = "location_update_work"

    fun schedule(context: Context) {
        val constraints = Constraints.Builder()
           // .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

 /*       val periodicRequest = PeriodicWorkRequestBuilder<LocationWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()*/
        val periodicRequest = PeriodicWorkRequestBuilder<LocationWorkerForeground>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicRequest
        )
    }
}
