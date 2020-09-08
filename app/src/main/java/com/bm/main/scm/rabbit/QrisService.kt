package com.bm.main.scm.rabbit

import androidx.annotation.Keep
import com.bm.main.scm.di.UserScope
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

@Keep
@UserScope
interface QrisService {

    @FormUrlEncoded
    @POST("qris/check")
    fun check(@Field("id_outlet") id_outlet: String): Single<CheckResponse>

    @FormUrlEncoded
    @POST("qris/get_transaksi_range")
    fun getTransaksiRange(@Field("id_sc") id_sc: String,
                     @Field("start") start: String,
                     @Field("end") end: String): Single<TransactionResponse>

    @FormUrlEncoded
    @POST("qris/get_mutasi_range")
    fun getMutasiRange(@Field("id_sc") id_sc: String,
                       @Field("start") start: String,
                       @Field("end") end: String): Single<MutationResponse>

}