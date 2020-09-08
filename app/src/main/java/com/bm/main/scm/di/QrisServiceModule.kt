package com.bm.main.scm.di

import com.bm.main.scm.rabbit.QrisService
import com.bm.main.scm.utils.SSLUtil
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.schedulers.Schedulers
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
class QrisServiceModule {

    @Singleton
    @Provides
    fun provideQrisServiceModule(): QrisService =
        Retrofit.Builder()
            .baseUrl(
                "https://mpdesktop.fastpay.co.id/mobile_android_3_0_X/index.php/"
//                if (BuildConfig.DEBUG) {
//                    PreferenceClass.getString(RConfig.API_URL_POS_DEVEL, "").orEmpty()
//                } else {
//                    PreferenceClass.getString(RConfig.API_URL_POS, "").orEmpty()
//                }
            )
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(
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
                    .build()
            )
            .build()
            .create(QrisService::class.java)
}