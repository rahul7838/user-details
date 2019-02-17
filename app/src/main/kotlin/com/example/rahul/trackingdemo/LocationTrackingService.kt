package com.example.rahul.trackingdemo

import android.content.Context
import android.content.Intent
import android.util.Log
import com.firebase.jobdispatcher.*

class LocationTrackingService : JobService() {

    private val TAG = LocationTrackingService::class.qualifiedName

    override fun onStopJob(job: JobParameters?): Boolean {
        Log.i(TAG, "onStopJob")
        return true
    }

    override fun onStartJob(job: JobParameters?): Boolean {
        Log.i(TAG, "onStartJob")
        val intent = Intent(this, UpdateLocationService::class.java)
        startService(intent)
        return false
    }

    companion object {
        fun jobInfoBuilder(context: Context) {
            val firebaseJobDispatcher = FirebaseJobDispatcher(GooglePlayDriver(context))
            val job = firebaseJobDispatcher.newJobBuilder()
                    .setLifetime(Lifetime.FOREVER)
                    .setConstraints(Constraint.ON_ANY_NETWORK)
                    .setTag("LocationJob")
                    .setRecurring(true)
                    .setService(LocationTrackingService::class.java)
                    .build()
            firebaseJobDispatcher.schedule(job)
        }
    }


}