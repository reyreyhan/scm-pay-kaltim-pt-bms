package com.bm.main.pos.feature.manage.hutangpiutang.piutangCustomer

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.hutangpiutang.DetailPiutangNew
import com.bm.main.pos.models.hutangpiutang.HutangPiutangRestModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class PiutangCustomerInteractor(var output: PiutangCustomerContract.InteractorOutput?) :
    PiutangCustomerContract.Interactor {

    private var disposable = CompositeDisposable()
    private val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callGetHutangAPI(context: Context, restModel: HutangPiutangRestModel) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.getListPiutangCustomer(key!!).subscribeWith(object : DisposableObserver<List<Customer>>() {

            override fun onNext(@NonNull response: List<Customer>) {
                output?.onSuccessGetHutang(response)
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

    override fun callSearchHutangAPI(context: Context, restModel: HutangPiutangRestModel, search: String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.getSearchPiutangCustomer(key!!,search).subscribeWith(object : DisposableObserver<List<Customer>>() {

            override fun onNext(@NonNull response: List<Customer>) {
                output?.onSuccessGetHutang(response)
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

    override fun callGetDetailHutangAPI(context: Context, restModel: HutangPiutangRestModel,id:String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.getDetailPiutangCustomerNew(key!!,id).subscribeWith(object : DisposableObserver<DetailPiutangNew>() {

            override fun onNext(@NonNull response: DetailPiutangNew) {
                output?.onSuccessGetDetailHutang(response)
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