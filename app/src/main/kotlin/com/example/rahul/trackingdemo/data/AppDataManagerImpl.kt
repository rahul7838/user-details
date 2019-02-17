package com.example.rahul.trackingdemo.data

import com.example.rahul.trackingdemo.data.model.UserResponse
import com.example.rahul.trackingdemo.data.remote.UserRetrofitApiService
import io.reactivex.Observable


class AppDataManagerImpl(val userRetrofitApiService: UserRetrofitApiService) : AppDataManager {

    override fun getResponse(): Observable<UserResponse> {
        return userRetrofitApiService.getResponse()
    }

}