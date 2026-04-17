package com.sinoptik_.empracticelibrary.domain.workmanager.location

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.sinoptik_.empracticelibrary.data.location.LocationStorage
import kotlinx.coroutines.withTimeoutOrNull

class LocationWorkerForeground(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    @RequiresPermission(
        allOf = [Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION]
    )
    override suspend fun doWork(): Result {
        Log.d("MyWorker", "start fun LocationWorker Work started")

        try {
            setForeground(getForegroundInfo())
        } catch (e: Exception) {
            Log.e("MyWorker", "Не удалось запустить Foreground", e)
        }

        val myLocationManager = MyLocationManager(applicationContext)
        try {
            val currentLocation = withTimeoutOrNull(15000) {
                myLocationManager.awaitFreshLocation()
            }

            if (currentLocation != null) {
                LocationStorage.update(applicationContext, currentLocation)
                Log.d("MyWorker", "Локация обновлена: ${currentLocation.latitude}")
            } else {
                Log.d("MyWorker", "Тайм-аут: GPS не ответил вовремя")
            }

        } catch (e: Exception) {
            Log.e("MyWorker", "Ошибка при получении локации: ${e.message}")
            return Result.retry() // Если упало, попробуем позже
        }
        Log.d("MyWorker", "end fun LocationWorker Work started")
        return Result.success()
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val channelId = "location_channel"
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Location Updates",
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("Обновление координат")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setOngoing(true)
            .build()

        return ForegroundInfo(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION)
    }


}
