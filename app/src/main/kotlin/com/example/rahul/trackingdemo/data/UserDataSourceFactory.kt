package com.example.rahul.trackingdemo.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.rahul.trackingdemo.data.model.Result
import com.example.rahul.trackingdemo.data.remote.UserRetrofitApiService


class UserDataSourceFactory(val userRetrofitApiService: UserRetrofitApiService) : DataSource.Factory<Int, Result>() {

    private val liveDataSource: MutableLiveData<UserDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, Result> {
        val userDataSource = UserDataSource(userRetrofitApiService)
        liveDataSource.postValue(userDataSource)
        return userDataSource
    }

    fun getLiveDataSource() : MutableLiveData<UserDataSource> {
        return liveDataSource
    }
}