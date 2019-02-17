package com.example.rahul.trackingdemo.data.remote


import com.example.rahul.trackingdemo.data.model.UserResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface UserRetrofitApiService {

    @GET("api/?inc=name,email,dob,phone,picture&results=50")
    fun getResponse(): Observable<UserResponse>
}