package com.example.rahul.trackingdemo.home

import android.util.Log
import com.example.rahul.trackingdemo.data.AppDataManager
import com.example.rahul.trackingdemo.data.model.Result
import com.example.rahul.trackingdemo.ui.home.HomeContract
import com.example.rahul.trackingdemo.utils.FormatUtility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class HomePresenter(val appDataManager: AppDataManager) : HomeContract.Presenter {

    private val TAG = HomePresenter::class.qualifiedName

    var view: HomeContract.View? = null

    lateinit var listArray: ArrayList<Result>

    override fun attachView(view: HomeContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun getUser() {
        appDataManager.getResponse()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view?.showLoading() }
                .subscribe({ response ->
                    view?.hideLoading()
                    listArray = response.results as ArrayList<Result>
                    view?.updateUi(response.results as ArrayList<Result>)
                    Log.i(TAG, "onSuccess")
                },
                        { Log.i(TAG, "${it.message}") })
    }

    override fun sortByName() {
        listArray.sortBy{ it.name }
        view?.updateUi(listArray)
    }

    override fun sortByMobile() {
        val formattedList = FormatUtility.formatNumber(listArray)
        formattedList.sortBy { it.phone }
        view?.updateUi(formattedList)
    }

    override fun sortByDOB() {
        listArray.sortBy { it.dob.date }
        view?.updateUi(listArray)
    }

    override fun sortByEmail() {
        listArray.sortBy { it.email }
        view?.updateUi(listArray)
    }

}