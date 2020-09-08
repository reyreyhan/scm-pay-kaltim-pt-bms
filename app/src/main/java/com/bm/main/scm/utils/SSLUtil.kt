package com.bm.main.scm.utils

import android.annotation.SuppressLint
import java.security.cert.CertificateException
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object SSLUtil {
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