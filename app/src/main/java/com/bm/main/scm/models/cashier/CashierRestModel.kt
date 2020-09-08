package com.bm.main.scm.models.cashier

import android.content.Context
import androidx.annotation.Keep
import com.bm.main.scm.rest.RestClient
import com.bm.main.scm.rest.RestModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Keep
class CashierRestModel(context: Context) : RestModel<CashierRestInterface>(context) {

    override fun createRestInterface(): CashierRestInterface {
        return RestClient.getInstance()!!.createInterface(CashierRestInterface::class.java)
    }

    fun add(
        noTelp: String,
        nama: String,
        password: String,
        noTelpOwner: String
    ): Observable<AddCashierResponse> {
        return restInterface.addCashier(
            noTelp, nama, password, noTelpOwner
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun login(user: String, password: String): Observable<LoginCashier> {
        return restInterface.loginCashier(
            user, password
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun listCashier(telpOwner: String): Single<List<Cashier>> {
        return restInterface.listCashier(telpOwner)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun blockCashier(no_telp_owner:String, id_kasir:String, is_block:String): Observable<CashierSuccessManage> {
        return restInterface.blockCashier(no_telp_owner, id_kasir, is_block)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteCashier(no_telp_owner:String, id_kasir:String, is_deleted:String): Observable<CashierSuccessManage> {
        return restInterface.deleteCashier(no_telp_owner, id_kasir, is_deleted)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun editCashier(no_telp_owner:String, id_kasir:String, nama:String, no_hp:String): Observable<CashierSuccessManage> {
        return restInterface.editCashier(no_telp_owner, id_kasir, nama, no_hp)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}