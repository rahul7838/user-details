package com.example.rahul.trackingdemo.data

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.rahul.trackingdemo.data.model.Result
import com.example.rahul.trackingdemo.data.model.UserResponse
import com.example.rahul.trackingdemo.data.remote.UserRetrofitApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDataSource(val userRetrofitApiService: UserRetrofitApiService) : PageKeyedDataSource<Int, Result>() {

    private val TAG = UserDataSource::class.qualifiedName

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Result>) {
        userRetrofitApiService.getResponse(20).enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.i(TAG, t.message)
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.i(TAG, "success")
                if (response.isSuccessful) {
                    callback.onResult(response.body()?.results as MutableList<Result>, null, 1)
                }
            }

        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        userRetrofitApiService.getResponse(5).enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.isSuccessful){
                    val nextKey = if(params.key == 1000)  null else params.key+1
                    callback.onResult(response.body()?.results as MutableList<Result>, nextKey)
                }
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}