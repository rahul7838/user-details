package com.example.rahul.trackingdemo.data.remote

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Provider

class RetrofitProvider(val gson: Gson) : Provider<Retrofit> {
    // https://randomuser.me/api/?inc=name,email,dob,phone,picture&results=50
    override fun get(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        //                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    }
}