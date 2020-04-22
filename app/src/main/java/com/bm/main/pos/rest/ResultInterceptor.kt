package com.bm.main.pos.rest

import androidx.annotation.Keep
import com.bm.main.pos.rest.entity.BaseResponse
import com.bm.main.pos.rest.entity.RestException
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import timber.log.Timber
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.charset.Charset
import javax.inject.Inject
import javax.net.ssl.SSLException

@Keep
class ResultInterceptor @Inject constructor(val moshi: Moshi) : Interceptor {

    @Keep
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val responseCode = response.code()
        val responseBody = response.body()

        return try {
            when (responseCode) {
                HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_CREATED -> {
                    moshi.adapter(BaseResponse::class.java).fromJson(responseBody?.string().orEmpty())?.let {
                        responseBody?.close()
                        when (it.errCode) {
                            RestException.RESPONSE_SUCCESS        -> {
                                response.newBuilder()
                                    .body(ResponseBody.create(MediaType.parse("application/json; charset=utf-8"), moshi.adapter(Any::class.java).toJson(it.data)))
                                    .build()
                            }
                            RestException.RESPONSE_USER_NOT_FOUND -> throw RestException(it.msg, RestException.CODE_USER_NOT_FOUND)
                            else                                  -> throw RestException(it.msg, RestException.CODE_ERROR_UNKNOWN)
                        }
                    } ?: throw RestException("", RestException.CODE_ERROR_UNKNOWN)
                }
                HttpURLConnection.HTTP_GATEWAY_TIMEOUT                    -> throw RestException("Mohon cek koneksi internet anda", responseCode)
                else                                                      -> {
//                    val source = responseBody!!.source()
//                    source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
//                    val content = source.buffer.clone().readString(Charset.forName("UTF-8")) ?: "Terjadi kesalahan"
                    val content = "Terjadi kesalahan, mohon ulangi beberapa saat lagi"
                    throw RestException(content, responseCode)
                }
            }
        } catch (connectException: ConnectException) {
            connectException.printStackTrace()
            throw RestException(
                "Terjadi kesalahan, mohon ulangi beberapa saat lagi", //connectException.message,
                HttpURLConnection.HTTP_CLIENT_TIMEOUT
            )
        } catch (connectException: SocketTimeoutException) {
            connectException.printStackTrace()
            throw RestException(
                "Terjadi kesalahan, mohon ulangi beberapa saat lagi", //connectException.message,
                HttpURLConnection.HTTP_CLIENT_TIMEOUT
            )
        } catch (connectException: UnknownHostException) {
            connectException.printStackTrace()
            throw RestException(
                "Terjadi kesalahan, mohon ulangi beberapa saat lagi", //connectException.message,
                HttpURLConnection.HTTP_CLIENT_TIMEOUT
            )
        } catch (connectException: SSLException) {
            connectException.printStackTrace()
            throw RestException(
                "Terjadi kesalahan, mohon ulangi beberapa saat lagi", //connectException.message,
                HttpURLConnection.HTTP_CLIENT_TIMEOUT
            )
        } catch (e: Exception) {
            Timber.e(e)
            if (e !is RestException)
                throw RestException(
                    e.message,
                    RestException.CODE_ERROR_UNKNOWN
                )
            else
                throw e
        }
    }
}