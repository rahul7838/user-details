package com.example.rahul.trackingdemo.data

import com.example.rahul.trackingdemo.data.model.UserResponse
import io.reactivex.Observable

interface AppDataManager {

    fun getResponse() : Observable<UserResponse>
}