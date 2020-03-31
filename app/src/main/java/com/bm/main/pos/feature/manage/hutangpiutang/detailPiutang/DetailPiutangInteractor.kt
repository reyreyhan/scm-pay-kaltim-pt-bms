package com.bm.main.pos.feature.manage.hutangpiutang.detailPiutang

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.customer.CustomerRestModel
import com.bm.main.pos.models.hutangpiutang.DetailPiutangNew
import com.bm.main.pos.models.hutangpiutang.HutangPiutangRestModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class DetailPiutangInteractor(var output: DetailPiutangContract.InteractorOutput?) : DetailPiutangContract.Interactor {

    private var disposable = CompositeDisposable()
    private val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callGetDetailCustomer(context: Context, restModel: CustomerRestModel, id:String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.detail(key!!,id).subscribeWith(object : DisposableObserver<Customer>() {

            override fun onNext(@NonNull response: Customer) {
                output?.onSuccessGetDetailCustomer(response)
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

    override fun callGetHutang(context: Context, restModel: HutangPiutangRestModel, id:String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.getDetailPiutangCustomerNew(key!!,id).subscribeWith(object : DisposableObserver<DetailPiutangNew>() {

            override fun onNext(@NonNull response: DetailPiutangNew) {
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



}