package com.example.rahul.trackingdemo.dependencyInjection

import com.example.rahul.trackingdemo.ui.home.HomeActivity
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {

  fun inject(homeActivity: HomeActivity)
}