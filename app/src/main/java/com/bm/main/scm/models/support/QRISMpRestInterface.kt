package com.bm.main.scm.models.support

import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query

interface QRISMpRestInterface {

    @POST("fastpay/data_merchant")
    fun getStatusQRIS(@Query("id") id: String): Observable<StatusQRISResponseEntity>
}