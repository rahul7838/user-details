package com.example.rahul.trackingdemo

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.example.rahul.trackingdemo.ConstantUtils.Companion.CHANNEL_ID

class LocationUpdateReceiver : BroadcastReceiver() {
   private val TAG = LocationUpdateReceiver::class.qualifiedName

    lateinit var notificationManager: NotificationManager


    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i(TAG, "locationUpdated")
        createNotificationChannel(context)
        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
        builder
                .setAutoCancel(true)
                .setContentTitle("Location Updated")
                .setContentText("You have moved by ${100}")
                .setCategory(Notification.CATEGORY_NAVIGATION)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setChannelId(CHANNEL_ID)
        notificationManager.notify(21, builder.build())
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context?) {
        notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            val notificationChannel = NotificationChannel(CHANNEL_ID, "Location update", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}