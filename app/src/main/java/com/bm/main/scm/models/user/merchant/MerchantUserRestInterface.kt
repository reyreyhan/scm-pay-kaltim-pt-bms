package com.bm.main.scm.models.user.merchant

import androidx.annotation.Keep
import io.reactivex.Observable
import retrofit2.http.*

@Keep
interface MerchantUserRestInterface {

    @GET("pengaturan/detailakun.php")
    fun getDetail(
        @Query("key") key:String): Observable<List<MerchantUser>>

    @GET("pengaturan/detailtoko.php")
    fun getToko(
        @Query("key") key:String): Observable<List<MerchantToko>>

    @FormUrlEncoded
    @POST("merchant/login.php")
    fun login(
        @Field("user") key: String,
        @Field("password") lama: String): Observable<MerchantUser>
}