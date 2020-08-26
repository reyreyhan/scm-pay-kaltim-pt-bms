package com.bm.main.scm.models.hutangpiutang

import androidx.annotation.Keep
import com.bm.main.scm.models.customer.CustomerNew
import com.bm.main.scm.models.supplier.Supplier
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

@Keep
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
        @Query("key") key:String): Observable<List<CustomerNew>>

    @GET("supplier/searchhutang.php")
    fun getSearchHutangSupplier(
        @Query("key") key:String,
        @Query("search") search:String): Observable<List<Supplier>>

    @GET("pelanggan/searchpiutang.php")
    fun getSearchPiutangCustomer(
        @Query("key") key:String,
        @Query("search") search:String): Observable<List<CustomerNew>>

    @GET("supplier/detailpiutangpersupplier.php")
    fun getDetailHutangSupplier(
        @Query("key") key:String,
        @Query("id_supplier") id:String): Observable<DetailHutang>

    @GET("pelanggan/detailpiutangperpelanggan.php")
    fun getDetailPiutangCustomer(
        @Query("key") key:String,
        @Query("id_pelanggan") id:String): Observable<DetailPiutang>

    @GET("pelanggan/detail_utang_pelanggan.php ")
    fun getDetailPiutangCustomerNew(
        @Query("key") key:String,
        @Query("id_pelanggan") id:String): Observable<DetailPiutangNew>
}