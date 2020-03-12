package com.bm.main.pos.feature.transaction.historyTransaction

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.models.hutangpiutang.Hutang
import com.bm.main.pos.models.hutangpiutang.HutangPiutangRestModel
import com.bm.main.pos.models.hutangpiutang.Piutang
import com.bm.main.pos.models.transaction.HistoryTransaction
import com.bm.main.pos.models.transaction.Transaction
import com.bm.main.pos.models.transaction.TransactionRestModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class TransactionInteractor(var output: TransactionContract.InteractorOutput?) : TransactionContract.Interactor {

    private var disposable = CompositeDisposable()
    private val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callGetHistoryAPI(context: Context, restModel: TransactionRestModel, awal: String, akhir: String, status: String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.historyTransaction(key!!, awal, akhir, status).subscribeWith(object : DisposableObserver<List<HistoryTransaction>>() {

            override fun onNext(@NonNull response: List<HistoryTransaction>) {
                output?.onSuccessGetHistory(response)
            }

            override fun onError(@NonNull e: Throwable) {
                e.printStackTrace()
                var errorCode = 999
                var errorMessage = "Terjadi kesalahan"
                if (e is RestException) {
                    errorCode = e.errorCode
                    errorMessage = e.message ?: "Terjadi kesalahan"
                } else {
                    errorMessage = e.message.toString()
                }
                output?.onFailedAPI(errorCode, errorMessage)
            }

            override fun onComplete() {

            }
        }))
    }

    override fun callGetSearchAPI(context: Context, restModel: TransactionRestModel, id: String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.searchTransaction(key!!, id).subscribeWith(object : DisposableObserver<List<Transaction>>() {

            override fun onNext(@NonNull response: List<Transaction>) {
                output?.onSuccessGetSearch(response)
            }

            override fun onError(@NonNull e: Throwable) {
                e.printStackTrace()
                var errorCode = 999
                var errorMessage = "Terjadi kesalahan"
                if (e is RestException) {
                    errorCode = e.errorCode
                    errorMessage = e.message ?: "Terjadi kesalahan"
                } else {
                    errorMessage = e.message.toString()
                }
                output?.onFailedAPI(errorCode, errorMessage)
            }

            override fun onComplete() {

            }
        }))
    }
}