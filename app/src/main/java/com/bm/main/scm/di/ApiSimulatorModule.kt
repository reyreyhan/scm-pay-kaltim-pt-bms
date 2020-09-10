package com.bm.main.scm.di

import android.content.Context
import com.bm.main.scm.models.support.QRISMpRestInterface
import com.bm.main.scm.models.support.SupportRestInterface
import com.bm.main.scm.rest.ResultInterceptor
import com.bm.main.scm.rest.util.RequestInterceptor
import com.bm.main.scm.utils.SSLUtil
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

@Qualifier
annotation class Simulator

@InstallIn(ActivityComponent::class)
@Module
class ApiSimulatorModule {

    @Simulator
    @ActivityScoped
    @Provides
    fun provideApiSimulator(@Simulator client:OkHttpClient): SupportRestInterface =
        Retrofit.Builder()
            .baseUrl("https://dev.apimobilesbf.fastpay.co.id/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(client)
            .build()
            .create(SupportRestInterface::class.java)

    @ActivityScoped
    @Provides
    fun provideApiMp(@Simulator client:OkHttpClient): QRISMpRestInterface =
        Retrofit.Builder()
            .baseUrl("http://mp.fastpay.co.id/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(client)
            .build()
            .create(QRISMpRestInterface::class.java)

    @Simulator
    @ActivityScoped
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context, requestInterceptor: RequestInterceptor, resultInterceptor: ResultInterceptor):OkHttpClient =
        OkHttpClient.Builder()
            .sslSocketFactory(
                SSLContext.getInstance("SSL").apply {
                    init(null, SSLUtil.trustAllCerts, java.security.SecureRandom())
                }.socketFactory,
                SSLUtil.trustAllCerts[0] as X509TrustManager
            )
            .hostnameVerifier({ hostname, sslSession -> true })
            .addInterceptor(HttpLoggingInterceptor().apply {
                // level = if (BuildConfig.BUILD_TYPE != "release") HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                level = HttpLoggingInterceptor.Level.BODY
            })
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .cache(Cache(context.cacheDir, 10 * 1024 * 1024))
            .build()
}