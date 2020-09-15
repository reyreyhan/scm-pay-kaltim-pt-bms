package com.bm.main.scm.feature.sell.chooseProduct;

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.models.product.ProductRestModel
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class ChooseProductInteractor(var output: ChooseProductContract.InteractorOutput?) : ChooseProductContract.Interactor {

    private var disposable = CompositeDisposable()
    val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callGetProductsAPI(context: Context, restModel: ProductRestModel) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.gets(key!!).subscribeWith(object : DisposableObserver<List<Product>>() {

            override fun onNext(@NonNull response: List<Product>) {
                output?.onSuccessGetProducts(response)
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

    override fun callSearchProductAPI(context: Context, restModel: ProductRestModel, search: String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.search(key!!,search).subscribeWith(object : DisposableObserver<List<Product>>() {

            override fun onNext(@NonNull response: List<Product>) {
                output?.onSuccessGetProducts(response)
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