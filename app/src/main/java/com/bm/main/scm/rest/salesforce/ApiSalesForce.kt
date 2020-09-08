package com.bm.main.scm.rest.salesforce

import com.bm.main.scm.di.UserScope
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

@UserScope
interface ApiSalesForce {

    @GET("profit/lengkapinik/{id}/cekstatus")
    fun checkStatus(@Path("id") id: String): Single<SFCheckStatusResult>
}