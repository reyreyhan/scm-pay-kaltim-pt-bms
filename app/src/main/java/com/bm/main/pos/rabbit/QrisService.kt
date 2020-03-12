package com.bm.main.pos.rabbit

import com.bm.sc.bebasbayar.social.di.UserScope
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

@UserScope
interface QrisService {

    @FormUrlEncoded
    @POST("qris/check")
    fun check(@Field("id_outlet") id_outlet: String): Single<CheckResponse>

    @FormUrlEncoded
    @POST("qris/get_transaksi")
    fun getTransaksi(@Field("id_sc") id_sc: String, @Field("date") date: String): Single<TransactionResponse>

    @FormUrlEncoded
    @POST("qris/get_mutasi")
    fun getMutasiSaldo(@Field("id_sc") id_sc: String, @Field("date") date: String): Single<MutationResponse>

}