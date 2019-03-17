package com.example.rahul.trackingdemo.home

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import com.example.rahul.trackingdemo.data.UserDataSource
import com.example.rahul.trackingdemo.data.UserDataSourceFactory
import com.example.rahul.trackingdemo.data.model.NetworkState


class HomeViewModel(val dataSourceFactory: UserDataSourceFactory) : ViewModel() {

    private val TAG = HomeViewModel::class.qualifiedName

    val livePagedListBuilder = LivePagedListBuilder(dataSourceFactory, 1)
            .setInitialLoadKey(1)
            .build()

     val liveNetworkState = Transformations.switchMap(dataSourceFactory.getLiveDataSource()
            , object : Function<UserDataSource, LiveData<NetworkState>> {
        override fun apply(input: UserDataSource?): MutableLiveData<NetworkState> {
           return input?.getNetworkState()!!
        }
     })

    val initialFetch = Transformations.switchMap(dataSourceFactory.getLiveDataSource(),
            Function<UserDataSource, LiveData<NetworkState>> { userDataSource -> userDataSource.getInitialLoading() })
}