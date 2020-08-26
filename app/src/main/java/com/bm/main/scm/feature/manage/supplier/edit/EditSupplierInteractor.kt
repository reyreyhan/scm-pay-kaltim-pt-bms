package com.bm.main.scm.feature.manage.supplier.edit

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.scm.models.Message
import com.bm.main.scm.models.supplier.SupplierRestModel
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class EditSupplierInteractor(var output: EditSupplierContract.InteractorOutput?) : EditSupplierContract.Interactor {

    private var disposable = CompositeDisposable()
    private val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callEditSupplierAPI(context: Context, model: SupplierRestModel, id:String, name: String,email:String,phone:String,address:String,province:String,city:String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(model.update(key!!,id,name,email,phone,province,city,address).subscribeWith(object : DisposableObserver<Message>() {

            override fun onNext(@NonNull response: Message) {
                output?.onSuccessEditSupplier(response.message)
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
                output?.onFailedEditSupplier(errorCode,errorMessage)
            }

            override fun onComplete() {

            }
        }))
    }
}