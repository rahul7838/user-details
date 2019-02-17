package com.example.rahul.trackingdemo.home

import com.example.rahul.trackingdemo.data.model.Result

interface HomeContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun updateUi(list: ArrayList<Result>)
    }

    interface Presenter {
        fun getUser()
        fun attachView(view: View)
        fun detachView()
        fun sortByName(list: ArrayList<Result>)
        fun sortByMobile(list: ArrayList<Result>)
        fun sortByDOB()
        fun sortByEmail()
    }
}