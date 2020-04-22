package com.bm.main.pos.rabbit

import androidx.annotation.Keep
import com.bm.main.pos.rest.ApiRequestModule
import com.bm.sc.bebasbayar.social.di.UserScope
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

@Keep
@Module
object QrisServiceModule {

    @JvmStatic
    @Provides
    @UserScope
    fun provideServiceModule(): QrisService =
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
                            init(null, ApiRequestModule.trustAllCerts, java.security.SecureRandom())
                        }.socketFactory,
                        ApiRequestModule.trustAllCerts[0] as X509TrustManager
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