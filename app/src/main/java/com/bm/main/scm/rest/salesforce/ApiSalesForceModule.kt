package com.bm.main.scm.rest.salesforce

import android.content.Context
import androidx.annotation.Keep
import com.bm.main.scm.rest.ApiRequestModule
import com.bm.sc.bebasbayar.social.di.UserScope
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

@Keep
@Module
object ApiSalesForceModule {

    @UserScope
    @JvmStatic
    @Provides
    fun provideApiSalesForce(context: Context): ApiSalesForce = Retrofit.Builder()
        .baseUrl(
            "http://api-salesforce.fastpay.co.id/index.php/"
        )
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .client(
            OkHttpClient.Builder()
                .sslSocketFactory(
                    SSLContext.getInstance("SSL").apply {
                        init(null,
                            ApiRequestModule.trustAllCerts, java.security.SecureRandom())
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
                .cache(Cache(context.cacheDir, 10 * 1024 * 1024))
                .build()
        )
        .build()
        .create(ApiSalesForce::class.java)
}