package com.example.rahul.trackingdemo.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.rahul.trackingdemo.data.model.NetworkState
import com.example.rahul.trackingdemo.data.model.Result
import com.example.rahul.trackingdemo.data.model.Status
import com.example.rahul.trackingdemo.data.model.UserResponse
import com.example.rahul.trackingdemo.data.remote.UserRetrofitApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDataSource(private val userRetrofitApiService: UserRetrofitApiService) : PageKeyedDataSource<Int, Result>() {

    private val TAG = UserDataSource::class.qualifiedName

    var liveNetworkState: MutableLiveData<NetworkState> = MutableLiveData()

    var initialfetch: MutableLiveData<NetworkState> = MutableLiveData()


    fun getNetworkState() : MutableLiveData<NetworkState> {
        return liveNetworkState
    }

    fun getInitialLoading() : MutableLiveData<NetworkState> {
        return initialfetch
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Result>) {

        initialfetch.postValue(NetworkState.LOADING)

        userRetrofitApiService.getResponse(6).enqueue(object : Callback<UserResponse> {

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.i(TAG, t.message)
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.i(TAG, "success")
                if (response.isSuccessful) {
                    initialfetch.postValue(NetworkState.LOADED)
                    callback.onResult(response.body()?.results as MutableList<Result>, null, 1)
                }
            }

        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        liveNetworkState.postValue(NetworkState.LOADING)
        userRetrofitApiService.getResponse(20).enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.isSuccessful){
                    val nextKey = if(params.key == 1000)  null else params.key+1
                    callback.onResult(response.body()?.results as MutableList<Result>, nextKey)
                    liveNetworkState.postValue(NetworkState.LOADED)
                }
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}