package com.bm.main.scm.di

import com.bm.main.scm.models.support.SupportRestInterface
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(ActivityComponent::class)
@Module
class ApiSimulatorModule {

    @ActivityScoped
    @Provides
    fun provideApiSimulator(@NoInterception okHttpClient: OkHttpClient): SupportRestInterface =
        Retrofit.Builder()
            .baseUrl("https://dev.apimobilesbf.fastpay.co.id/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(okHttpClient)
            .build()
            .create(SupportRestInterface::class.java)
}