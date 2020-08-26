package com.bm.main.scm.feature.scan

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.models.product.ProductRestModel
import com.bm.main.scm.rest.entity.RestException
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import timber.log.Timber

class ScanCodeInteractor(val output: ScanCodeContract.InteractorOutput) :

    ScanCodeContract.Interactor {
    private var disposable = CompositeDisposable()

    override fun destroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callSearchByBarcodeAPI(
        context: Context,
        restModel: ProductRestModel,
        search: String
    ) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.searchByBarcode(key!!, search).subscribeWith(object :
            DisposableObserver<List<Product>>() {

            override fun onNext(@NonNull response: List<Product>) {

                Timber.d("onNext $response")
                output?.onSuccessByBarcode(response)
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
                //output?.onFailedByBarcode(errorCode, errorMessage)
            }

            override fun onComplete() {

            }
        }))
    }


}
