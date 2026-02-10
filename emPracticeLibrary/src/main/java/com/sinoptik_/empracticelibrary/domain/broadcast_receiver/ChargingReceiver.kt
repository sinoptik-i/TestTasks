package com.sinoptik_.empracticelibrary.domain.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sinoptik_.empracticelibrary.domain.notification_manager.ChargingNotificationManager

class ChargingReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_POWER_CONNECTED) {
            ChargingNotificationManager.sendNotification(context, "Зарядка From Receiver", "Кабель подключен!",2)
        }
    }
}
