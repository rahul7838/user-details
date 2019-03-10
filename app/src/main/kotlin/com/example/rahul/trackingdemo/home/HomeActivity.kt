package com.example.rahul.trackingdemo.home

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crashlytics.android.Crashlytics
import com.example.rahul.trackingdemo.ConstantUtils
import com.example.rahul.trackingdemo.ConstantUtils.Companion.PERMISSION_REQUEST_CODE
import com.example.rahul.trackingdemo.LocationTrackingService
import com.example.rahul.trackingdemo.R
import com.example.rahul.trackingdemo.TrackingApplication
import com.example.rahul.trackingdemo.data.model.NetworkState
import com.example.rahul.trackingdemo.data.model.Result
import com.example.rahul.trackingdemo.ui.home.UserListAdapter
import com.facebook.drawee.backends.pipeline.Fresco
import io.fabric.sdk.android.Fabric
import javax.inject.Inject

class HomeActivity : AppCompatActivity(){

    lateinit var progressbar: ProgressBar

    lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var homeViewModel: HomeViewModel

//    @Inject
//    lateinit var homeContractPresenter: HomeContract.Presenter

    var listArray: ArrayList<Result> = ArrayList()

    private lateinit var context: Context

    private lateinit var userListAdapter: UserListAdapter

companion object {
    val diff = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.email == newItem.email
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.phone==newItem.phone
        }

    }
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_main)
        TrackingApplication.appComponent.inject(this)
//        progressbar = findViewById(R.id.progress_bar_id)
        recyclerView = findViewById(R.id.user_list_recycler_view_id)
        Fresco.initialize(this)
//        displayPermission(this)
        fetchData()
//        PreferenceManager.getDefaultSharedPreferences(this).apply {
//            if (getBoolean(ConstantUtils.IS_JOB_FRIST_RUN, true)) {
//                edit().putBoolean(ConstantUtils.IS_JOB_FRIST_RUN, false).apply()
//                LocationTrackingService.jobInfoBuilder(context)
//            }
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        homeContractPresenter.detachView()
    }


    private fun fetchData() {
        userListAdapter = UserListAdapter(this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = userListAdapter
        }
        homeViewModel.livePagedListBuilder.observe(this, object : Observer<PagedList<Result>> {
            override fun onChanged(t: PagedList<Result>?) {
                userListAdapter.submitList(t)
            }
        })
        homeViewModel.liveNetworkState.observe(this,
                Observer<NetworkState> {networkState -> userListAdapter.setNetwork(networkState)})

        homeViewModel.initialFetch.observe(this,
                Observer<NetworkState> {networkState -> userListAdapter.setNetwork(networkState)})
    }

    private fun displayPermission(context: Context) {
        val permissionArray: Array<String> = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissionArray, PERMISSION_REQUEST_CODE)
        } else {
            fetchData()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE ->
                if (grantResults.size >= 0) {
                    fetchData()
                } else {
                    Toast.makeText(this, "Location tracking will not work until permission is not granted", Toast.LENGTH_LONG).show()
                }
        }
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
//            R.id.sort_by_name -> homeContractPresenter.sortByName()
//            R.id.sort_by_mobile -> homeContractPresenter.sortByMobile()
//            R.id.sort_by_email -> homeContractPresenter.sortByEmail()
//            R.id.sort_by_dob -> homeContractPresenter.sortByDOB()
        }
        return true
    }
}