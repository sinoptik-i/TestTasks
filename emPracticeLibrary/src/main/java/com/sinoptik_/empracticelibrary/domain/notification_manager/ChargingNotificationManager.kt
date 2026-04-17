package com.sinoptik_.empracticelibrary.domain.notification_manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat

object ChargingNotificationManager {

    fun sendNotification(context: Context, title: String, message: String, id: Int = 1) {
        val channelId = "charging_status_channel"
        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val channel = NotificationChannel(
            channelId,
            "Статус зарядки",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Уведомления о подключении зарядного устройства"
        }
        notificationManager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_lock_idle_charging)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        Log.d("MyWorker", "ChargingWorker notify")
        notificationManager.notify(id, builder.build())
    }
}