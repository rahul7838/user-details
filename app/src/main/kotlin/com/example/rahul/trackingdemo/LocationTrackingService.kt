package com.example.rahul.trackingdemo

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.firebase.jobdispatcher.*

class LocationTrackingService : JobService() {

    lateinit var notificationManager: NotificationManager

    private val TAG = LocationTrackingService::class.qualifiedName

    override fun onStopJob(job: JobParameters?): Boolean {
        Log.i(TAG, "onStopJob")
        return true
    }

    override fun onStartJob(job: JobParameters?): Boolean {

        Log.i(TAG, "onStartJob")
        val intent = Intent(this, LocationUpdateReceiver::class.java)
        intent.action = ConstantUtils.ACTION_LOCATION_UPDATES
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val location = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val minDis = 12.toFloat()
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1200, minDis, pendingIntent)
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    companion object {
        fun jobInfoBuilder(context: Context) {
            val firebaseJobDispatcher = FirebaseJobDispatcher(GooglePlayDriver(context))
            val job = firebaseJobDispatcher.newJobBuilder()
                    .setLifetime(Lifetime.FOREVER)
                    .setConstraints(Constraint.ON_ANY_NETWORK)
                    .setTag("LocationJob")
                    .setService(LocationTrackingService::class.java)
                    .build()
            firebaseJobDispatcher.schedule(job)
        }
    }


}