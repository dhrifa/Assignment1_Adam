package com.example.adamassignment1

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat

private const val TAG = "BatteryReceiver"
class BatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive: ")
            if (intent.action == Intent.ACTION_BATTERY_CHANGED) {
                // show notification when battery level is low
                val notificationBuilder = NotificationCompat.Builder(context, "battery_channel")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Battery Changed")
                    .setContentText("Battery level is changed, please check your device.")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)

                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(0, notificationBuilder.build())
            }
    }


}