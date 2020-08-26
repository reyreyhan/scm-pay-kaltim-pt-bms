package com.bm.main.scm.rest

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.Keep
import com.bm.main.fpl.constants.RConfig
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.scm.BuildConfig
import com.bm.main.scm.rest.util.RequestInterceptor
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
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Keep
@Module
object ApiRequestModule {

    @UserScope
    @JvmStatic
    @Provides
    fun provideApiService(context: Context, requestInterceptor: RequestInterceptor, resultInterceptor: ResultInterceptor): ApiService =
        Retrofit.Builder()
            .baseUrl(
                if (BuildConfig.DEBUG) {
                    PreferenceClass.getString(RConfig.API_URL_POS_DEVEL, "").orEmpty()
                } else {
                    PreferenceClass.getString(RConfig.API_URL_POS, "").orEmpty()
                }
            )
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(
                OkHttpClient.Builder()
                    .sslSocketFactory(
                        SSLContext.getInstance("SSL").apply {
                            init(null, trustAllCerts, java.security.SecureRandom())
                        }.socketFactory,
                        trustAllCerts[0] as X509TrustManager
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
            )
            .build()
            .create(ApiService::class.java)

    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        @SuppressLint("TrustAllX509TrustManager")
        @Throws(CertificateException::class)
        override fun checkClientTrusted(
            chain: Array<java.security.cert.X509Certificate>,
            authType: String
        ) {
        }

        @SuppressLint("TrustAllX509TrustManager")
        @Throws(CertificateException::class)
        override fun checkServerTrusted(
            chain: Array<java.security.cert.X509Certificate>,
            authType: String
        ) {
        }

        override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = emptyArray()
    })
}