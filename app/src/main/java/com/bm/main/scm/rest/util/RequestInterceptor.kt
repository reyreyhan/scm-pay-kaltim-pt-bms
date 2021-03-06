package com.bm.main.scm.rest.util

import androidx.annotation.Keep
import com.bm.main.scm.utils.Helper
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


@Keep
class RequestInterceptor @Inject constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (!Helper.isNetworkAvailable()) {
            val maxStale = 60 * 60 * 24 * 1
            request = request
                .newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .build()
        }
        return chain.proceed(request)
    }
}