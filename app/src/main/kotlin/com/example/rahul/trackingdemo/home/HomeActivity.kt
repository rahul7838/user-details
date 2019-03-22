package com.example.rahul.trackingdemo.home

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crashlytics.android.Crashlytics
import com.example.rahul.trackingdemo.ConstantUtils.Companion.PERMISSION_REQUEST_CODE
import com.example.rahul.trackingdemo.R
import com.example.rahul.trackingdemo.TrackingApplication
import com.example.rahul.trackingdemo.data.model.NetworkState
import com.example.rahul.trackingdemo.data.model.Result
import com.example.rahul.trackingdemo.setting.SettingActivity
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import io.fabric.sdk.android.Fabric
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnBackPressedCallback {


  private val TAG = HomeActivity::class.java.canonicalName

  lateinit var progressbar: ProgressBar
  var listner: (() -> View.OnClickListener)? = null
  private lateinit var recyclerView: RecyclerView
  private lateinit var frame: FrameLayout
  private lateinit var drawerLayout: DrawerLayout
  private lateinit var toggle: ActionBarDrawerToggle
  private lateinit var toolbar: Toolbar
  private lateinit var navigationView: NavigationView



  @Inject

  lateinit var homeViewModel: HomeViewModel

  var listArray: ArrayList<Result> = ArrayList()

  private lateinit var context: Context

  private lateinit var userListAdapter: UserListAdapter

  companion object {
    val diff = object : DiffUtil.ItemCallback<Result>() {
      override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.email == newItem.email
      }

      override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.phone == newItem.phone
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
    drawerLayout = findViewById<DrawerLayout>(R.id.drawer_home)
    recyclerView = findViewById(R.id.user_list_recycler_view_id)
    frame = findViewById<FrameLayout>(R.id.mainActivity_layout_id)
    Fresco.initialize(this)
    listner = { actionOnClick() }
//        displayPermission(this)
    toolbar = findViewById(R.id.toolbar)
    setSupportActionBar(toolbar)
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
      setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
    }
    navigationView = findViewById(R.id.nav_view_id)
    navigationView.setNavigationItemSelectedListener(this)
    toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
    toggle.syncState()
    drawerLayout.addDrawerListener(toggle)
    fetchData()


//        PreferenceManager.getDefaultSharedPreferences(this).apply {
//            if (getBoolean(ConstantUtils.IS_JOB_FRIST_RUN, true)) {
//                edit().putBoolean(ConstantUtils.IS_JOB_FRIST_RUN, false).apply()
//                LocationTrackingService.jobInfoBuilder(context)
//            }
//        }
  }

  override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    drawerLayout.closeDrawer(GravityCompat.END)
  }


  private fun actionOnClick(): View.OnClickListener {
    return object : View.OnClickListener {
      override fun onClick(v: View?) {
        Toast.makeText(context, "on Click", Toast.LENGTH_SHORT).show()
      }
    }
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
    userListAdapter.onItemClick = { adapterPosition -> showSnackBar(adapterPosition, frame) }
    homeViewModel.livePagedListBuilder.observe(this, object : Observer<PagedList<Result>> {
      override fun onChanged(t: PagedList<Result>?) {
        userListAdapter.submitList(t)
      }
    })
    homeViewModel.liveNetworkState.observe(this,
        Observer<NetworkState> { networkState -> userListAdapter.setNetwork(networkState) })

    homeViewModel.initialFetch.observe(this,
        Observer<NetworkState> { networkState -> userListAdapter.setNetwork(networkState) })
  }

  private fun showSnackBar(adapterPosition: Int, view: View) {
//      Toast.makeText(this, "success", Toast.LENGTH_LONG).show()
    Snackbar.make(view, "Item position is $adapterPosition", Snackbar.LENGTH_INDEFINITE)
        .setAction("Open", listner?.invoke())
        .show()
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

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    item.isChecked = true
    val id = item.itemId
    when(id) {
      R.id.setting -> {
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)
      }
    }
    drawerLayout.closeDrawer(GravityCompat.START)
    return true
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

             android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
    }
    return true
  }

  override fun onResume() {
    super.onResume()
    addOnBackPressedCallback(this, this)
  }

  //  override fun onBackPressed() {
//    super.onBackPressed()
//    if(drawerLayout.isDrawerOpen(GravityCompat.END)){
//      drawerLayout.closeDrawer(GravityCompat.END)
//    }
//  }

  override fun handleOnBackPressed(): Boolean {
    if(drawerLayout.isDrawerOpen(GravityCompat.START)){
      drawerLayout.closeDrawer(GravityCompat.START)
      return true
    }
    return false
  }
}