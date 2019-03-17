//package com.example.rahul.trackingdemo
//
//import android.annotation.TargetApi
//import android.app.*
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.location.Location
//import android.location.LocationListener
//import android.location.LocationManager
//import android.os.Build
//import android.os.Bundle
//import android.os.IBinder
//import android.support.v4.app.NotificationCompat
//import android.support.v4.content.ContextCompat
//import android.util.Log
//
//class UpdateLocationService : Service(), LocationListener {
//
//    lateinit var notificationManager: NotificationManager
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        Log.i(TAG, "onCreate")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.i(TAG, "onDestroy")
//    }
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        Log.i(TAG, "onStartCommand")
//        super.onStartCommand(intent, flags, startId)
//        val location = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        val minDis = 12.toFloat()
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
//            location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1200, minDis, this)
//        location.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1200, minDis, this)
//        return START_STICKY
//    }
//
//    private val TAG: String? = UpdateLocationService::class.qualifiedName
//
//    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
//        Log.i(TAG, "onStatusChanged")
//
//    }
//
//
//    override fun onProviderEnabled(provider: String?) {
//        Log.i(TAG, "onProviderEnabled")
//    }
//
//    override fun onProviderDisabled(provider: String?) {
//        Log.i(TAG, "onProviderDisabled")
//    }
//
//    override fun onLocationChanged(location: Location?) {
//        Log.i(TAG, "onLocationChanged")
//
////        val latitude = location?.latitude
////        val longitude = location?.longitude
////        val distanceChanged = location?.distanceTo()
////        Intent().apply {
////            action = "updateLocation"
////            putExtra("distanceChanged", longitude)
////            sendBroadcast(this)
////        }
//
//
//        if (Build.VERSION.SDK_INT >= 26) {
//            createNotificationChannel(this)
//        }
//        notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        val builder = NotificationCompat.Builder(this, ConstantUtils.CHANNEL_ID)
//        builder
//                .setAutoCancel(true)
//                .setContentTitle("Location Updated")
//                .setContentText("You have moved by ${100}")
//                .setCategory(Notification.CATEGORY_NAVIGATION)
//                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setChannelId(ConstantUtils.CHANNEL_ID)
//        notificationManager.notify(21, builder.build())
//    }
//
//    @TargetApi(Build.VERSION_CODES.O)
//    private fun createNotificationChannel(context: Context?) {
//        notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if (notificationManager.getNotificationChannel(ConstantUtils.CHANNEL_ID) == null) {
//            val notificationChannel = NotificationChannel(ConstantUtils.CHANNEL_ID, "Location update", NotificationManager.IMPORTANCE_HIGH)
//            notificationManager.createNotificationChannel(notificationChannel)
//        }
//    }
//}
//
//
//
