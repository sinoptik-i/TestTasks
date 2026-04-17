package com.sinoptik_.empracticelibrary.domain.workmanager.charging

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object ChargingWorkScheduler {

    private const val WORK_NAME = "ChargingCheckWork"

    fun schedule(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        //test ok
     /*      val testWorkRequest = OneTimeWorkRequestBuilder<ChargingWorker>()
               .setConstraints(constraints)
               .build()

           WorkManager.getInstance(context).enqueue(
               testWorkRequest
           )*/

        val chargingWorkRequest = PeriodicWorkRequestBuilder<ChargingWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            chargingWorkRequest
        )
    }


    fun stop(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
    }
}
