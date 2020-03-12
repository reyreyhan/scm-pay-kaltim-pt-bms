package com.bm.main.pos.feature.manage.hutangpiutang.detailHutang

import android.content.Context
import android.os.Handler
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.models.hutangpiutang.DetailHutang
import com.bm.main.pos.models.hutangpiutang.HutangPiutangRestModel
import com.bm.main.pos.models.supplier.Supplier
import com.bm.main.pos.models.supplier.SupplierRestModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class DetailHutangInteractor(var output: DetailHutangContract.InteractorOutput?) : DetailHutangContract.Interactor {

    private var disposable = CompositeDisposable()
    private val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callGetDetailSupplier(context: Context, restModel: SupplierRestModel, id:String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.detail(key!!,id).subscribeWith(object : DisposableObserver<Supplier>() {

            override fun onNext(@NonNull response: Supplier) {
                output?.onSuccessGetDetailSupplier(response)
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

    override fun callGetHutang(context: Context, restModel: HutangPiutangRestModel,id:String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.getDetailHutangSupplier(key!!,id).subscribeWith(object : DisposableObserver<DetailHutang>() {

            override fun onNext(@NonNull response: DetailHutang) {
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