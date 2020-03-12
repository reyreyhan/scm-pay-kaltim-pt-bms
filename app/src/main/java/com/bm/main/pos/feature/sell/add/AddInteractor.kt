package com.bm.main.pos.feature.sell.add;

import android.content.Context
import android.util.Log
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.models.cart.CartRestModel
import com.bm.main.pos.models.category.Category
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.models.product.ProductRestModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import timber.log.Timber

class AddInteractor(var output: AddContract.InteractorOutput?) : AddContract.Interactor {

    private var disposable = CompositeDisposable()
    private val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callAddAPI(
            context: Context, restModel: CartRestModel,
            name: String, kodebarang: String, buy: String, sell: String, stock: String, gbr: String, desc: String, photoUrl: String
    ) {
        val key = PreferenceClass.getTokenPos()
        val request =
            if (photoUrl.isEmpty())
                restModel.add(key!!, name, kodebarang, buy, sell, stock, gbr, desc)
            else
                restModel.addWithoutImg(key!!, name, kodebarang, buy, sell, stock, photoUrl, desc)

        disposable.add(request.subscribeWith(object : DisposableObserver<List<Product>>() {

            override fun onNext(@NonNull response: List<Product>) {
                output?.onSuccessAdd(response)
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

    override fun callAddWithBarodeAPI(context: Context, restModel: CartRestModel, name: String, barcode: String, buy: String, sell: String, stock: String, gbr: String, desc: String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.addWithBarcode(key!!, name, barcode, buy, sell, stock).subscribeWith(object : DisposableObserver<List<Product>>() {

            override fun onNext(@NonNull response: List<Product>) {
                output?.onSuccessAdd(response)
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

    override fun callGetCategoriesAPI(context: Context, restModel: CategoryRestModel) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.chooseCategories(key!!).subscribeWith(object :
            DisposableObserver<List<Category>>() {

            override fun onNext(@NonNull response: List<Category>) {
                output?.onSuccessGetCategories(response)
            }

            override fun onError(@NonNull e: Throwable) {
                e.printStackTrace()
                var errorCode = 999
                val errorMessage: String
                if (e is RestException) {
                    errorCode = e.errorCode
                    errorMessage = e.message ?: "Terjadi kesalahan"
                } else {
                    errorMessage = e.message.toString()
                }

                if (errorCode == RestException.CODE_RESPONSE_ERROR) {
                    output?.onSuccessGetCategories(emptyList())
                } else {
                    output?.onFailedAPI(errorCode, errorMessage)
                }
            }

            override fun onComplete() {

            }
        }))
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
                Timber.d("onNext %s", response.toString())
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
                output?.onFailedByBarcode(errorCode, errorMessage)
            }

            override fun onComplete() {

            }
        }))
    }
}