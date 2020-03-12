package com.bm.main.pos.rest.salesforce

import com.bm.sc.bebasbayar.social.di.UserScope
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

@UserScope
interface ApiSalesForce {

    @GET("profit/lengkapinik/{id}/cekstatus")
    fun checkStatus(@Path("id") id: String): Single<SFCheckStatusResult>
}