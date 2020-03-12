package com.bm.main.pos.feature.manage.hutangpiutang.hutangSupplier

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.models.Message
import com.bm.main.pos.models.hutangpiutang.Hutang
import com.bm.main.pos.models.hutangpiutang.HutangPiutangRestModel
import com.bm.main.pos.models.supplier.Supplier
import com.bm.main.pos.models.supplier.SupplierRestModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class HutangSupplierInteractor(var output: HutangSupplierContract.InteractorOutput?) :
    HutangSupplierContract.Interactor {

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
        disposable.add(restModel.getListHutangSupplier(key!!).subscribeWith(object : DisposableObserver<List<Supplier>>() {

            override fun onNext(@NonNull response: List<Supplier>) {
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
        disposable.add(restModel.getSearchHutangSupplier(key!!,search).subscribeWith(object : DisposableObserver<List<Supplier>>() {

            override fun onNext(@NonNull response: List<Supplier>) {
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