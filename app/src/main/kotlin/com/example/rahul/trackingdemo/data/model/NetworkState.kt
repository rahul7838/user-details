package com.example.rahul.trackingdemo.data.model

data class NetworkState(var status: Status, var msg: String? = null) {
    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.RUNNING)
        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
    }
}

enum class Status {
    FAILED,
    RUNNING,
    SUCCESS
}