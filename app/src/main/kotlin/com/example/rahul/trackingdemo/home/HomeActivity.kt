package com.example.rahul.trackingdemo.home

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.example.rahul.trackingdemo.ConstantUtils
import com.example.rahul.trackingdemo.R
import com.example.rahul.trackingdemo.TrackingApplication
import com.example.rahul.trackingdemo.data.model.Result
import com.example.rahul.trackingdemo.LocationTrackingService
import com.facebook.drawee.backends.pipeline.Fresco
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HomeContract.View {

    lateinit var progressbar: ProgressBar

    lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var homeContractPresenter: HomeContract.Presenter

    var listArray: ArrayList<Result> = ArrayList()

    private lateinit var context: Context

    private lateinit var userListAdapter: UserListAdapter
//
    override fun showLoading() {
        progressbar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressbar.visibility = View.GONE
    }

    override fun updateUi(list: ArrayList<Result>) {
//        this.listArray.clear()
//        this.listArray.addAll(list)
        userListAdapter.prepareNewsList(list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContentView(R.layout.activity_main)
        TrackingApplication.appComponent.inject(this)
        progressbar = findViewById(R.id.progress_bar_id)
        recyclerView = findViewById(R.id.user_list_recycler_view_id)
        Fresco.initialize(this)
        homeContractPresenter.attachView(this)
        homeContractPresenter.getUser()
        userListAdapter = UserListAdapter(this)
        displayPermission(this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
            adapter = userListAdapter
        }
        PreferenceManager.getDefaultSharedPreferences(this).apply {
            if (getBoolean(ConstantUtils.IS_JOB_FRIST_RUN, true)) {
                edit().putBoolean(ConstantUtils.IS_JOB_FRIST_RUN, false).apply()
                LocationTrackingService.jobInfoBuilder(context)
            }
        }
    }

    private fun displayPermission(context: Context) {
        val permissionArray: Array<String> = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        if(ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, permissionArray, 1)
        }else {
            startLocationTrackingService()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    private fun startLocationTrackingService() {

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)
        val id = item?.itemId
        when (id) {
            R.id.sort_by_name -> homeContractPresenter.sortByName()
            R.id.sort_by_mobile -> homeContractPresenter.sortByMobile()
            R.id.sort_by_email -> homeContractPresenter.sortByEmail()
            R.id.sort_by_dob -> homeContractPresenter.sortByDOB()
        }
        return true
    }
}