package com.example.rahul.trackingdemo.dependencyInjection

import com.example.rahul.trackingdemo.data.AppDataManager
import com.example.rahul.trackingdemo.data.AppDataManagerImpl
import com.example.rahul.trackingdemo.data.remote.RetrofitProvider
import com.example.rahul.trackingdemo.data.remote.UserRetrofitApiService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideGson() : Gson {
        return Gson()
    }

    @Provides
    fun provideRetrofitProvider(gson: Gson) : RetrofitProvider {
        return RetrofitProvider(gson)
    }

    @Provides
    fun provideRetrofitProvider(retrofitProvider: RetrofitProvider) : UserRetrofitApiService {
        return retrofitProvider.get().create(UserRetrofitApiService::class.java)
    }

    @Provides
    fun provideAppDataManager(userRetrofitApiService: UserRetrofitApiService) : AppDataManager {
        return AppDataManagerImpl(userRetrofitApiService)
    }
}