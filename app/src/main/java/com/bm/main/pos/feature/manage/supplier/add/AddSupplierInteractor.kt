package com.bm.main.pos.feature.manage.supplier.add

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.models.Message
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.supplier.SupplierRestModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class AddSupplierInteractor(var output: AddSupplierContract.InteractorOutput?) : AddSupplierContract.Interactor {

    private var disposable = CompositeDisposable()
    private val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callAddSupplierAPI(context: Context, model: SupplierRestModel, name: String,email:String,phone:String,address:String,province:String,city:String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(model.add(key!!,name,email,phone,province,city,address).subscribeWith(object : DisposableObserver<Message>() {

            override fun onNext(@NonNull response: Message) {
                output?.onSuccessAddSupplier(response.message)
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
                output?.onFailedAddSupplier(errorCode,errorMessage)
            }

            override fun onComplete() {

            }
        }))
    }
}