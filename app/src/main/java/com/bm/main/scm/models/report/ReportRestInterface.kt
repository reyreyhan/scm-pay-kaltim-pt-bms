package com.bm.main.scm.models.report

import androidx.annotation.Keep
import com.bm.main.scm.models.transaction.HistoryTransaction
import com.bm.main.scm.models.transaction.Transaction
import io.reactivex.Observable
import retrofit2.http.*

@Keep
interface ReportRestInterface {

    @GET("laporan/transaksi.php")
    fun transactions(
        @Query("key") key:String,
        @Query("tanggal_awal") awal:String,
        @Query("tanggal_akhir") akhir:String): Observable<List<ReportTransaksi>>

    @GET("laporan/caritransaksi.php")
    fun searchTransactions(
        @Query("key") key:String,
        @Query("search") search:String): Observable<List<ReportTransaksi>>

    @GET("laporan/sorttransaksi.php")
    fun sortTransactions(
        @Query("key") key:String,
        @Query("tanggal_awal") awal:String,
        @Query("tanggal_akhir") akhir:String): Observable<List<ReportTransaksi>>

    @GET("laporan/sorthargatransaksi.php")
    fun sortPriceTransactions(
        @Query("key") key:String,
        @Query("tanggal_awal") awal:String,
        @Query("tanggal_akhir") akhir:String): Observable<List<ReportTransaksi>>


    @GET("laporan/labarugi.php")
    fun labaRugi(
        @Query("key") key:String,
        @Query("tanggal_awal") awal:String,
        @Query("tanggal_akhir") akhir:String): Observable<ReportLabaRugi>

    @GET("laporan/mutasikas.php")
    fun mutasi(
        @Query("key") key:String,
        @Query("tanggal_awal") awal:String,
        @Query("tanggal_akhir") akhir:String): Observable<ReportMutasi>

    @GET("laporan/stokbarang.php")
    fun stock(
        @Query("key") key:String,
        @Query("tanggal_awal") awal:String,
        @Query("tanggal_akhir") akhir:String): Observable<List<ReportStock>>

    @GET("laporan/caristok.php")
    fun searchStock(
        @Query("key") key:String,
        @Query("search") search:String,
        @Query("tanggal_awal") awal:String,
        @Query("tanggal_akhir") akhir:String): Observable<List<ReportStock>>

    @GET("laporan/sortstokbarang.php")
    fun sortStock(
        @Query("key") key:String,
       @Query("tanggal_awal") awal:String,
        @Query("tanggal_akhir") akhir:String): Observable<List<ReportStock>>

    @GET("laporan/sorthargastok.php")
    fun sortPriceStock(
        @Query("key") key:String,
        @Query("tanggal_awal") awal:String,
        @Query("tanggal_akhir") akhir:String): Observable<List<ReportStock>>

    @GET("laporan/historikulakan.php")
    fun kulakan(
        @Query("key") key:String,
        @Query("tanggal_awal") awal:String,
        @Query("tanggal_akhir") akhir:String,
        @Query("status") status:String): Observable<List<HistoryTransaction>>

    @GET("laporan/carihistorikulakan.php")
    fun searchKulakan(
        @Query("key") key:String,
        @Query("cari") id:String): Observable<List<Transaction>>

}