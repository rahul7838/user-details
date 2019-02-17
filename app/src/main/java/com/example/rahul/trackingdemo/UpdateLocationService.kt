package com.example.rahul.trackingdemo

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.content.ContextCompat

class UpdateLocationService : IntentService("updateLocationService"), LocationListener {
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocationChanged(location: Location?) {

        val latitude = location?.latitude
        val longitude = location?.longitude
        Intent().apply {
            action = "updateLocation"
            putExtra("distanceChanged", longitude)
            sendBroadcast(this)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        val location = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val minDis = 12.toFloat()
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 12000, minDis, this)

    }
}