package com.bm.main.pos.feature.manage.product.add

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.models.Message
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

class AddProductInteractor(var output: AddProductContract.InteractorOutput?) :
    AddProductContract.Interactor {

    private var disposable = CompositeDisposable()
    private val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callAddProductAPI(context: Context, model: ProductRestModel, name: String, kode: String, idkategori: String, jual: String, beli: String, stok: String, minstok: String, gbr: String?, desk: String, photoUrl: String) {
        val key = PreferenceClass.getTokenPos()
        val request = if (photoUrl.isEmpty()) model.add(key!!, name, kode, idkategori, jual, beli, stok, minstok, gbr, desk) else model.add(key!!, name, kode, idkategori, jual, beli, stok, minstok, photoUrl, desk)
        disposable.add(
            request.subscribeWith(object : DisposableObserver<Message>() {
                override fun onNext(@NonNull response: Message) {
                    output?.onSuccessAddProduct(response.message)
                }

                override fun onError(@NonNull e: Throwable) {
                    e.printStackTrace()
                    var errorCode = 999
                    var errorMessage: String
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
            })
        )
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

                if (errorCode in arrayOf(RestException.CODE_RESPONSE_ERROR, RestException.CODE_DATA_NOT_FOUND)) {
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
                output?.onFailedByBarcode(errorCode, errorMessage)
            }

            override fun onComplete() {

            }
        }))
    }
}