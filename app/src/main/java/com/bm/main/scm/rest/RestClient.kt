package com.bm.main.scm.rest

//import com.bm.main.pos.MyApplication
//import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor
//import com.bm.main.pos.utils.glide.UnsafeOkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
import androidx.annotation.Keep
import com.bm.main.scm.BuildConfig
import com.bm.main.scm.SBFApplication
import com.bm.main.scm.rest.util.RequestInterceptor
import com.bm.main.scm.rest.util.ResponseInterceptor
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Keep
class RestClient {
    //    private var url: String?=""
    private var url: String? = null

    private val retrofit: Retrofit
    val client = OkHttpClient.Builder()
//    companion object {
//        @JvmStatic lateinit var instance: RestClient
//    }
//
//    init {
//        instance = this
//    }

    companion object {

        val TAG = RestClient::class.java.javaClass.simpleName

        private var instance: RestClient? = null


        fun getInstance(): RestClient? {
            if (instance == null) {
                synchronized(RestClient::class.java) {
                    if (instance == null)
                        instance = RestClient()
                }
            }
            return instance
        }

        /**
         * refer to documentation written in build.gradle
         *
         * @param appVer : app versionName from app level build.gradle
         * @return ApiVersion
         */
        private fun getApiVersion(appVer: String): String {
            val separatorCount =
                appVer.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray().size - 1
            var lastIndex = appVer.length
            if (separatorCount > 1) {
                lastIndex = appVer.indexOf(".", appVer.indexOf(".") + 1)
            }
            return appVer.substring(0, lastIndex)
        }
    }

    init {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            val cacheSize: Long = 10 * 1024 * 1024 // 10 MiB
            //  val cache = Cache(MyApplication.applicationContext().cacheDir, cacheSize)
            val cache = Cache(SBFApplication.getInstance()!!.applicationContext.cacheDir, cacheSize)

            val hostnameVerifier = HostnameVerifier { hostname, sslSession -> true }
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(hostnameVerifier)
            builder.addInterceptor(RequestInterceptor())
                .addInterceptor(ResponseInterceptor())
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .cache(cache)


            if (BuildConfig.DEBUG) {
//                val httpLoggingInterceptor = HttpLoggingInterceptor()
//                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//                client.addInterceptor(httpLoggingInterceptor)
//                val logging = HttpLoggingInterceptor()
//                logging.level = HttpLoggingInterceptor.Level.BASIC
//                builder.addInterceptor(OkHttpProfilerInterceptor())
            }

            val client = builder.addInterceptor(HttpLoggingInterceptor().apply {
                // level = if (BuildConfig.BUILD_TYPE != "release") HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                level = HttpLoggingInterceptor.Level.BODY
            }).build()

            retrofit = Retrofit.Builder()
//                .baseUrl(BuildConfig.BASE_URL)
                .baseUrl(getUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()

        } catch (e: Exception) {
            throw RuntimeException(e)
        }


    }

    fun <T> createInterface(A: Class<T>): T {
        return retrofit.create(A)
    }

    fun getUrl(): String {
//        Log.d(TAG, "getUrl: $url")
//        if (BuildConfig.DEBUG) {
//            url = PreferenceClass.getString(RConfig.API_URL_POS_DEVEL, "").toString()
//        } else {
//            url = PreferenceClass.getString(RConfig.API_URL_POS, "").toString()
//        }
        url = "https://api-dev-profit.fastpay.co.id/"

        return url as String
    }

//    fun setUrl(url: String) {
//        Log.d(TAG, "setUrl: $url")
//        this.url = url
//    }

}
