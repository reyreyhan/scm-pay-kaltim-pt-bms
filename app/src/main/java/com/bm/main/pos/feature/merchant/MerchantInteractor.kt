package com.bm.main.pos.feature.merchant

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.models.transaction.HistoryTransaction
import com.bm.main.pos.models.transaction.TransactionRestModel
import com.bm.main.pos.models.user.User
import com.bm.main.pos.models.user.UserRestModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class MerchantInteractor(val output: MerchantContract.InteractorOutput?) : MerchantContract.Interactor {

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

    override fun callGetProfileAPI(context: Context, restModel: UserRestModel) {
        val key =PreferenceClass.getTokenPos()
        disposable.add(restModel.getProfile(key!!).subscribeWith(object : DisposableObserver<List<User>>() {

            override fun onNext(@NonNull response: List<User>) {
                output?.onSuccessGetProfile(response)
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

    override fun saveUser(user: User) {
        //val token = user.key
        //  appSession.setSharedPreferenceString(AppConstant.TOKEN,token)
        appSession.setSharedPreferenceString(AppConstant.USER,user.json())
    }
}