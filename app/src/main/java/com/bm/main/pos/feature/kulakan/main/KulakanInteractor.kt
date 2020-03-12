package com.bm.main.pos.feature.kulakan.main;

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.models.Message
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.models.product.ProductRestModel
import com.bm.main.pos.models.transaction.Order
import com.bm.main.pos.models.transaction.RequestKulakan
import com.bm.main.pos.models.transaction.RequestTransaction
import com.bm.main.pos.models.transaction.TransactionRestModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class KulakanInteractor(var output: KulakanContract.InteractorOutput?) :
    KulakanContract.Interactor {

    private var disposable = CompositeDisposable()
    private val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callSearchByBarcodeAPI(context: Context, restModel: ProductRestModel, search: String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.searchByBarcode(key!!,search).subscribeWith(object : DisposableObserver<List<Product>>() {

            override fun onNext(@NonNull response: List<Product>) {
                output?.onSuccessByBarcode(response)
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
                output?.onFailedBarcode(errorCode,errorMessage)
            }

            override fun onComplete() {

            }
        }))
    }

    override fun callOrderAPI(context: Context, restModel: TransactionRestModel, req: RequestKulakan) {
        val key = PreferenceClass.getTokenPos()
        req.key = key
        disposable.add(restModel.kulakan(req).subscribeWith(object : DisposableObserver<Order>() {

            override fun onNext(@NonNull response: Order) {
                output?.onSuccessOrder(response)
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