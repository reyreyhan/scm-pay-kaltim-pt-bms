package com.bm.main.pos.feature.kulakan.editProduct;

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.models.cart.CartRestModel
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.models.product.ProductRestModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class EditInteractor(var output: EditContract.InteractorOutput?) : EditContract.Interactor {

    private var disposable = CompositeDisposable()
    private val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callEditAPI(context: Context,restModel:CartRestModel,id:String,name:String,barcode:String,buy:String,sell:String,stock:String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.update(key!!,id,name,barcode,buy,sell,stock).subscribeWith(object : DisposableObserver<List<Product>>() {

            override fun onNext(@NonNull response: List<Product>) {
                output?.onSuccessEdit(response)
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