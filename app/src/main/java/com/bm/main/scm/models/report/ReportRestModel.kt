package com.bm.main.scm.models.report

import android.content.Context
import androidx.annotation.Keep
import com.bm.main.scm.models.transaction.HistoryTransaction
import com.bm.main.scm.models.transaction.Transaction
import com.bm.main.scm.rest.RestClient
import com.bm.main.scm.rest.RestModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Keep
class ReportRestModel(context: Context) : RestModel<ReportRestInterface>(context) {

    override fun createRestInterface(): ReportRestInterface {
        return RestClient.getInstance()!!.createInterface(ReportRestInterface::class.java)
    }

    fun transactions(key:String,awal:String,akhir:String): Observable<List<ReportTransaksi>> {
        return restInterface.transactions(key,awal,akhir)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun searchTransactions(key:String,search:String): Observable<List<ReportTransaksi>> {
        return restInterface.searchTransactions(key,search)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun sortTransactions(key:String,awal:String,akhir:String): Observable<List<ReportTransaksi>> {
        return restInterface.sortTransactions(key,awal,akhir)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun sortPriceTransactions(key:String,awal:String,akhir:String): Observable<List<ReportTransaksi>> {
        return restInterface.sortPriceTransactions(key,awal,akhir)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun labaRugi(key:String,awal:String,akhir:String): Observable<ReportLabaRugi> {
        return restInterface.labaRugi(key,awal,akhir)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun mutasi(key:String,awal:String,akhir:String): Observable<ReportMutasi> {
        return restInterface.mutasi(key,awal,akhir)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun stock(key:String,awal:String,akhir:String): Observable<List<ReportStock>> {
        return restInterface.stock(key,awal,akhir)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun searchStock(key:String,cari:String,awal:String,akhir:String): Observable<List<ReportStock>> {
        return restInterface.searchStock(key,cari,awal,akhir)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun sortStock(key:String,awal:String,akhir:String): Observable<List<ReportStock>> {
        return restInterface.sortStock(key,awal,akhir)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun sortPriceStock(key:String,awal:String,akhir:String): Observable<List<ReportStock>> {
        return restInterface.sortPriceStock(key,awal,akhir)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun kulakan(key:String,awal:String,akhir:String,status:String): Observable<List<HistoryTransaction>> {
        return restInterface.kulakan(key,awal,akhir,status)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun searchKulakan(key:String,id:String): Observable<List<Transaction>> {
        return restInterface.searchKulakan(key,id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }



}