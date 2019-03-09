package com.example.rahul.trackingdemo.home

import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import com.example.rahul.trackingdemo.data.UserDataSourceFactory


class HomeViewModel(val dataSourceFactory: UserDataSourceFactory) : ViewModel() {

    private val TAG = HomeViewModel::class.qualifiedName

    val livePagedListBuilder = LivePagedListBuilder(dataSourceFactory, 1)
            .setInitialLoadKey(1)
            .build()
}