package com.bm.main.pos.models.transaction

import android.content.Context
import com.bm.main.pos.models.Message
import com.bm.main.pos.rest.RestClient
import com.bm.main.pos.rest.RestModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TransactionRestModel(context: Context) : RestModel<TransactionRestInterface>(context) {

    override fun createRestInterface(): TransactionRestInterface {
        return RestClient.getInstance()!!.createInterface(TransactionRestInterface::class.java)
    }

    fun getCustomerTransactions(key:String,id:String): Observable<List<Transaction>> {
        return restInterface.getCustomerTransactions(key,id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getCustomerDebts(key:String,id:String): Observable<List<Transaction>> {
        return restInterface.getCustomerDebts(key,id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getDetailTransaction(key:String,id:String): Observable<DetailTransaction> {
        return restInterface.getDetailTransaction(key,id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getDetailTransactionSupplier(key:String,id:String): Observable<DetailTransaction> {
        return restInterface.getDetailTransactionSupplier(key,id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteDetailTransaction(key:String,id:String): Observable<Message> {
        return restInterface.deleteDetailTransaction(key,id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun order(req:RequestTransaction): Observable<Order> {
        return restInterface.order(req)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun kulakan(req:RequestKulakan): Observable<Order> {
        return restInterface.kulakan(req)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun historyTransaction(key:String,awal:String,akhir:String,status:String): Observable<List<HistoryTransaction>> {
        return restInterface.historyTransaction(key,awal,akhir,status)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun payPiutang(key:String,id:String,total:String): Observable<Message> {
        return restInterface.payPiutang(key,id,total)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun payHutang(key:String,id:String,total:String): Observable<Message> {
        return restInterface.payHutang(key,id,total)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun searchTransaction(key:String,id:String): Observable<List<Transaction>> {
        return restInterface.searchTransaction(key,id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteSupplierDetailTransaction(key:String,id:String): Observable<Message> {
        return restInterface.deleteSupplierDetailTransaction(key,id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun payPiutangIdCustomer(key:String,id:String,pay:String): Observable<Message>{
        return restInterface.payPiutangIdCustomer(id, pay, key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun sendStruk(key:String, invoice:String, email:String): Observable<Message>{
        return restInterface.sendStruk(key, invoice, email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}