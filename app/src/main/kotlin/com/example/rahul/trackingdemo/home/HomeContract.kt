package com.example.rahul.trackingdemo.ui.home

import com.example.rahul.trackingdemo.data.model.Result

interface HomeContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun updateUi(list: ArrayList<Result>)
        fun handleError()
    }

    interface Presenter {
        fun getUser()
        fun attachView(view: View)
        fun detachView()
        fun sortByName()
        fun sortByMobile()
        fun sortByDOB()
        fun sortByEmail()
    }
}