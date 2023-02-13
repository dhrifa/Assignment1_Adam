package com.example.adamassignment1

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class BatteryReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BATTERY_CHANGED) {
            // show notification when battery level is low
            val notificationBuilder = NotificationCompat.Builder(context, "battery_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Battery Changed")
                .setContentText("Battery level is changed, please check your device.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(0, notificationBuilder.build())
        }


    }
}