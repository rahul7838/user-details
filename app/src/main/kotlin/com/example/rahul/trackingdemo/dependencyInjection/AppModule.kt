package com.example.rahul.trackingdemo.dependencyInjection

import com.example.rahul.trackingdemo.data.UserDataSource
import com.example.rahul.trackingdemo.data.UserDataSourceFactory
import com.example.rahul.trackingdemo.data.remote.RetrofitProvider
import com.example.rahul.trackingdemo.data.remote.UserRetrofitApiService
import com.example.rahul.trackingdemo.home.HomeViewModel
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
    fun provideUserRetrofitApiService(retrofitProvider: RetrofitProvider) : UserRetrofitApiService {
        return retrofitProvider.get().create(UserRetrofitApiService::class.java)
    }

//    @Provides
//    fun provideAppDataManager(userRetrofitApiService: UserRetrofitApiService) : UserDataSource {
//        return UserDataSource(userRetrofitApiService)
//    }

    @Provides
    fun provideUserDataSourceFactory(userRetrofitApiService: UserRetrofitApiService) : UserDataSourceFactory {
        return UserDataSourceFactory(userRetrofitApiService)
    }

    @Provides
    fun providesHomeViewModel(userDataSourceFactory: UserDataSourceFactory) : HomeViewModel {
        return HomeViewModel(userDataSourceFactory)
    }

//    @Provides
//    fun provideHomePresenter(appDataManager: AppDataManager) : HomeContract.Presenter {
//        return HomePresenter(appDataManager)
//    }
}