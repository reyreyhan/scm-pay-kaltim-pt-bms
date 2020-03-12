package com.bm.main.pos.models.hutangpiutang

import com.google.gson.JsonObject
import com.bm.main.pos.models.Message
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.supplier.Supplier
import io.reactivex.Observable
import retrofit2.http.*

interface HutangPiutangRestInterface {

    @GET("supplier/datahutang.php")
    fun getHutang(
        @Query("key") key:String): Observable<Hutang>

    @GET("pelanggan/datapiutang.php")
    fun getPiutang(
        @Query("key") key:String): Observable<Piutang>

    @GET("supplier/lihatsemuahutang.php")
    fun getLastHutang(
        @Query("key") key:String): Observable<List<Hutang.Data>>

    @GET("pelanggan/lihatsemuahutang.php")
    fun getLastPiutang(
        @Query("key") key:String): Observable<List<Piutang.Data>>

    @GET("supplier/searchlihatsemuahutang.php")
    fun getSearchLastHutang(
        @Query("key") key:String,
        @Query("search") search:String): Observable<List<Hutang.Data>>

    @GET("pelanggan/searchlihatsemuahutang.php")
    fun getSearchLastPiutang(
        @Query("key") key:String,
        @Query("search") search:String): Observable<List<Piutang.Data>>

    @GET("supplier/listhutang.php")
    fun getListHutangSupplier(
        @Query("key") key:String): Observable<List<Supplier>>

    @GET("pelanggan/listpiutang.php")
    fun getListPiutangCustomer(
        @Query("key") key:String): Observable<List<Customer>>

    @GET("supplier/searchhutang.php")
    fun getSearchHutangSupplier(
        @Query("key") key:String,
        @Query("search") search:String): Observable<List<Supplier>>

    @GET("pelanggan/searchpiutang.php")
    fun getSearchPiutangCustomer(
        @Query("key") key:String,
        @Query("search") search:String): Observable<List<Customer>>

    @GET("supplier/detailpiutangpersupplier.php")
    fun getDetailHutangSupplier(
        @Query("key") key:String,
        @Query("id_supplier") id:String): Observable<DetailHutang>

    @GET("pelanggan/detailpiutangperpelanggan.php")
    fun getDetailPiutangCustomer(
        @Query("key") key:String,
        @Query("id_pelanggan") id:String): Observable<DetailPiutang>



}