package com.example.rahul.trackingdemo

import android.app.Application
import com.example.rahul.trackingdemo.dependencyInjection.AppComponent
import com.example.rahul.trackingdemo.dependencyInjection.AppModule
import com.example.rahul.trackingdemo.dependencyInjection.DaggerAppComponent

class TrackingApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule()).build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}