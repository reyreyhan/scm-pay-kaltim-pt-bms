package com.bm.main.pos.feature.manage.customer.credit

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.models.transaction.Transaction
import com.bm.main.pos.models.transaction.TransactionRestModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class CustomerCreditInteractor(var output: CustomerCreditContract.InteractorOutput?) : CustomerCreditContract.Interactor {

    private var disposable = CompositeDisposable()
    private val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callGetTransactionsAPI(context: Context, restModel: TransactionRestModel, id:String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.getCustomerDebts(key!!,id).subscribeWith(object : DisposableObserver<List<Transaction>>() {

            override fun onNext(@NonNull response: List<Transaction>) {
                output?.onSuccessGetTransactions(response)
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