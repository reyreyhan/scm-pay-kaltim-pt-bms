package com.bm.main.scm.models.cashier

import androidx.annotation.Keep
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query


@Keep
interface CashierRestInterface {
    @FormUrlEncoded
    @POST("kasir/register.php")
    fun addCashier(
        @Field("no_telp") noTelp: String,
        @Field("nama") nama: String,
        @Field("password") password: String,
        @Field("no_telp_owner") noTelpOwner: String
    ): Observable<AddCashierResponse>

    @FormUrlEncoded
    @POST("kasir/login.php")
    fun loginCashier(
        @Field("user") user: String,
        @Field("password") password: String
    ): Observable<LoginCashier>

    @POST("kasir/list")
    fun listCashier(
        @Query("no_telp_owner") no_telp_owner:String
    ): Single< List<Cashier>>

    @FormUrlEncoded
    @POST("kasir/edit")
    fun blockCashier(
        @Field("no_telp_owner") no_telp_owner:String,
        @Field("id_kasir") id_kasir:String,
        @Field("is_block") is_block:String
    ): Observable<CashierSuccessManage>

    @FormUrlEncoded
    @POST("kasir/edit")
    fun deleteCashier(
        @Field("no_telp_owner") no_telp_owner:String,
        @Field("id_kasir") id_kasir:String,
        @Field("is_deleted") is_deleted:String
    ): Observable<CashierSuccessManage>

    @FormUrlEncoded
    @POST("kasir/edit")
    fun editCashier(
        @Field("no_telp_owner") no_telp_owner:String,
        @Field("id_kasir") id_kasir:String,
        @Field("nama") nama:String,
        @Field("no_hp") no_hp:String
    ): Observable<CashierSuccessManage>
}