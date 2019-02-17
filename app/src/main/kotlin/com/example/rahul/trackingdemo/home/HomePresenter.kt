package com.example.rahul.trackingdemo.home

import android.util.Log
import com.example.rahul.trackingdemo.data.AppDataManager
import com.example.rahul.trackingdemo.data.model.Result
import com.example.rahul.trackingdemo.utils.FormatUtility
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomePresenter(val appDataManager: AppDataManager) : HomeContract.Presenter {

    private val TAG = HomePresenter::class.qualifiedName

    var view: HomeContract.View? = null


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
                    view?.updateUi(response.results as ArrayList<Result>)
                    Log.i(TAG, "onSuccess")
                },
                        { Log.i(TAG, "${it.message}") })
    }

    override fun sortByName(list: ArrayList<Result>) {
        list.sortBy { it.name }
        view?.updateUi(list)
    }

    override fun sortByMobile(list: ArrayList<Result>) {
        val numberList: ArrayList<String> = ArrayList()
        val formattedList = FormatUtility.formatNumber(numberList)

//        formattedList.sortWith(object : Comparator<Int> {
//            override fun compare(o1: Int?, o2: Int?): Int {
//                return when {
//                    o1!! - o2!! > 0 -> 1
//                    o1!! - o2!! < 0 -> -1
//                    else -> 0
//                }
//            }
//        })
//        view?.updateUi(formattedList)
    }

    override fun sortByDOB() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sortByEmail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}