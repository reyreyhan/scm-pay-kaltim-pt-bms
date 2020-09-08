package com.bm.main.scm.feature.manage.cashier.add;

import android.content.Context
import com.bm.main.scm.models.cashier.AddCashierResponse
import com.bm.main.scm.models.cashier.CashierRestModel
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class CashierAddInteractor(var output: CashierAddContract.InteractorOutput?) : CashierAddContract.Interactor {

    private val appSession = AppSession()
    private var disposable = CompositeDisposable()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callMerchantLoginAPI(
        context: Context,
        restModel: CashierRestModel,
        telp: String,
        name: String,
        password: String
    ) {
        disposable.add(restModel.add(telp, name, password, appSession.getSharedPreferenceString("OWNER_HP")!!)
            .subscribeWith(object : DisposableObserver<AddCashierResponse>() {
            override fun onNext(@NonNull response: AddCashierResponse) {
                output?.onSuccessAdd(response)
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
                output?.onFailedAPI(errorCode,errorMessage)
            }

            override fun onComplete() {

            }
        }))
    }
}