package com.bm.main.scm.di

import android.content.Context
import com.bm.main.scm.rest.ResultInterceptor
import com.bm.main.scm.rest.util.RequestInterceptor
import com.bm.main.scm.rest.util.ResponseInterceptor
import com.bm.main.scm.utils.SSLUtil
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

@InstallIn(ApplicationComponent::class)
@Module
class ApiRequestModule {

    @Singleton
    @Provides
    fun provideRetrofit(@ApplicationContext context: Context, client:OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(
//                if (BuildConfig.DEBUG) {
//                    PreferenceClass.getString(RConfig.API_URL_POS_DEVEL, "").orEmpty()
//                } else {
//                    PreferenceClass.getString(RConfig.API_URL_POS, "").orEmpty()
//                }
            "https://api-dev-profit.fastpay.co.id/"
            )
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(client)
            .build()

    @Singleton
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
            .addInterceptor(requestInterceptor)
            .addInterceptor(resultInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                // level = if (BuildConfig.BUILD_TYPE != "release") HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                level = HttpLoggingInterceptor.Level.BODY
            })
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .cache(Cache(context.cacheDir, 10 * 1024 * 1024))
            .build()

    @Singleton
    @Provides
    fun provideRequestInterceptor(): RequestInterceptor =
        RequestInterceptor()

    @Singleton
    @Provides
    fun provideResponseInterceptor(): ResponseInterceptor =
        ResponseInterceptor()
}