package com.example.rahul.trackingdemo.data.remote


import com.example.rahul.trackingdemo.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserRetrofitApiService {

    @GET("api/?inc=name,email,dob,phone,picture")
    fun getResponse(@Query("results")results: Int): Call<UserResponse>
}