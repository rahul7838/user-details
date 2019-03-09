package com.example.rahul.trackingdemo.data

import androidx.paging.DataSource
import com.example.rahul.trackingdemo.data.model.Result
import com.example.rahul.trackingdemo.data.remote.UserRetrofitApiService


class UserDataSourceFactory(val userRetrofitApiService: UserRetrofitApiService) : DataSource.Factory<Int, Result>() {

    override fun create(): DataSource<Int, Result> {
        return UserDataSource(userRetrofitApiService)
    }

}