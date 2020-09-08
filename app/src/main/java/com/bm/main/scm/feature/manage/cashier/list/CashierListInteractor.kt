package com.bm.main.scm.feature.manage.cashier.list;

import android.content.Context
import com.bm.main.scm.models.cashier.CashierRestModel
import com.bm.main.scm.models.cashier.CashierSuccessManage
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class CashierListInteractor(var output: CashierListContract.InteractorOutput?) : CashierListContract.Interactor {

    private val appSession = AppSession()
    private var disposable = CompositeDisposable()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callApiListCashier(
        context: Context,
        cashierRestModel: CashierRestModel
    ) {
        disposable.add(cashierRestModel
            .listCashier(appSession.getSharedPreferenceString("OWNER_HP")!!)
            .subscribe({result->
                output?.onSuccessListCashier(result)
            },{e->
                e.printStackTrace()
                var errorCode = 999
                var errorMessage = "Terjadi kesalahan"
                if (e is RestException) {
                    errorCode = e.errorCode
                    errorMessage = e.message ?: "Terjadi kesalahan"
                }
                else{
                    errorMessage = e.message.toString()
                }
                output?.onFailure(errorCode,errorMessage)
            }))
    }

    override fun callApiBlockCashier(
        context: Context,
        cashierRestModel: CashierRestModel,
        idCashier: Int,
        idBlock: Int
    ) {
        disposable.add(cashierRestModel.blockCashier(appSession.getSharedPreferenceString("OWNER_HP")!!, idCashier.toString(), idBlock.toString())
            .subscribeWith(object : DisposableObserver<CashierSuccessManage>() {
                override fun onNext(@NonNull response: CashierSuccessManage) {
                    output?.onSuccessBlockCashier(response)
                }

                override fun onError(@NonNull e: Throwable) {
                    e.printStackTrace()
                    var errorCode = 999
                    var errorMessage = "Terjadi kesalahan"
                    if (e is RestException) {
                        errorCode = e.errorCode
                        errorMessage = e.message ?: "Terjadi kesalahan"
                    }
                    else{
                        errorMessage = e.message.toString()
                    }
                    output?.onFailure(errorCode,errorMessage)
                }

                override fun onComplete() {

                }
            }))
    }

    override fun callApiDeleteCashier(
        context: Context,
        cashierRestModel: CashierRestModel,
        idCashier: Int,
        isDeleted: Int
    ) {
        disposable.add(cashierRestModel.deleteCashier(appSession.getSharedPreferenceString("OWNER_HP")!!, idCashier.toString(), isDeleted.toString())
            .subscribeWith(object : DisposableObserver<CashierSuccessManage>() {
                override fun onNext(@NonNull response: CashierSuccessManage) {
                    output?.onSuccessDeleteCashier(response)
                }

                override fun onError(@NonNull e: Throwable) {
                    e.printStackTrace()
                    var errorCode = 999
                    var errorMessage = "Terjadi kesalahan"
                    if (e is RestException) {
                        errorCode = e.errorCode
                        errorMessage = e.message ?: "Terjadi kesalahan"
                    }
                    else{
                        errorMessage = e.message.toString()
                    }
                    output?.onFailure(errorCode,errorMessage)
                }

                override fun onComplete() {

                }
            }))
    }

    override fun callApiEditCashier(
        context: Context,
        cashierRestModel: CashierRestModel,
        idCashier: Int,
        name: String,
        telp: String
    ) {
        disposable.add(cashierRestModel.editCashier(appSession.getSharedPreferenceString("OWNER_HP")!!, idCashier.toString(), name, telp)
            .subscribeWith(object : DisposableObserver<CashierSuccessManage>() {
                override fun onNext(@NonNull response: CashierSuccessManage) {
                    output?.onSuccessDeleteCashier(response)
                }

                override fun onError(@NonNull e: Throwable) {
                    e.printStackTrace()
                    var errorCode = 999
                    var errorMessage = "Terjadi kesalahan"
                    if (e is RestException) {
                        errorCode = e.errorCode
                        errorMessage = e.message ?: "Terjadi kesalahan"
                    }
                    else{
                        errorMessage = e.message.toString()
                    }
                    output?.onFailure(errorCode,errorMessage)
                }

                override fun onComplete() {

                }
            }))
    }
}