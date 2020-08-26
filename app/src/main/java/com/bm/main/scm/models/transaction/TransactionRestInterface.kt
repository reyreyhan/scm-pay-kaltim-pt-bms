package com.bm.main.scm.models.transaction

import androidx.annotation.Keep
import com.bm.main.scm.models.Message
import io.reactivex.Observable
import retrofit2.http.*

@Keep
interface TransactionRestInterface {

    @GET("pelanggan/transaksi.php")
    fun getCustomerTransactions(
        @Query("key") key:String,
        @Query("id_pelanggan") id:String): Observable<List<Transaction>>

    @GET("pelanggan/hutang.php")
    fun getCustomerDebts(
        @Query("key") key:String,
        @Query("id_pelanggan") id:String): Observable<List<Transaction>>

    @GET("transaksi/struk.php")
    fun getDetailTransaction(
        @Query("key") key:String,
        @Query("no_invoice") id:String): Observable<DetailTransaction>

    @GET("transaksi/struksupplier.php")
    fun getDetailTransactionSupplier(
        @Query("key") key:String,
        @Query("no_invoice") id:String): Observable<DetailTransaction>

    @GET("transaksi/batalorder.php")
    fun deleteDetailTransaction(
        @Query("key") key:String,
        @Query("no_invoice") id:String): Observable<Message>

    @POST("penjualan/order.php")
    fun order(@Body request: RequestTransaction) : Observable<Order>

    @POST("pembelian/order.php")
    fun kulakan(@Body request: RequestKulakan) : Observable<Order>

    @GET("histori/daftartransaksilengkap.php")
    fun historyTransaction(
        @Query("key") key:String,
        @Query("tanggal_awal") awal:String,
        @Query("tanggal_akhir") akhir:String,
        @Query("status") status:String): Observable<List<HistoryTransaction>>


    @FormUrlEncoded
    @POST("transaksi/bayarhutangpelanggan.php")
    fun payPiutang(
        @Field("key") key:String,
        @Field("no_invoice") id:String,
        @Field("totalbayar") bayar:String): Observable<Message>

    @FormUrlEncoded
    @POST("transaksi/bayarhutangsupplier.php")
    fun payHutang(
        @Field("key") key:String,
        @Field("no_invoice") id:String,
        @Field("totalbayar") bayar:String): Observable<Message>

    @GET("histori/caritransaksi.php")
    fun searchTransaction(
        @Query("key") key:String,
        @Query("cari") id:String): Observable<List<Transaction>>

    @GET("transaksi/batalordersupplier.php")
    fun deleteSupplierDetailTransaction(
        @Query("key") key:String,
        @Query("no_invoice") id:String): Observable<Message>

    @FormUrlEncoded
    @POST("transaksi/bayarhutangidpelanggan.php")
    fun payPiutangIdCustomer(
        @Field("id_pelanggan") id:String,
        @Field("total_bayar") pay:String,
        @Field("key") key:String): Observable<Message>

    @POST("transaksi/struk_send.php")
    fun sendStruk(
        @Query("key") key:String,
        @Query("no_invoice") invoice:String,
        @Query("email") email:String): Observable<Message>
}