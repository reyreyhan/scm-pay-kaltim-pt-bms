package com.bm.main.pos.feature.manage.customer.edit

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.models.Message
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.customer.CustomerRestModel
import com.bm.main.pos.models.supplier.SupplierRestModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class EditCustomerInteractor(var output: EditCustomerContract.InteractorOutput?) : EditCustomerContract.Interactor {

    private var disposable = CompositeDisposable()
    private val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callEditCustomerAPI(context: Context, model: CustomerRestModel, id:String, name: String,email:String,phone:String,address:String,gbr:String?) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(model.update(key!!,id,name,email,phone,address,gbr).subscribeWith(object : DisposableObserver<Message>() {

            override fun onNext(@NonNull response: Message) {
                output?.onSuccessEditCustomer(response.message)
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
                output?.onFailedEditCustomer(errorCode,errorMessage)
            }

            override fun onComplete() {

            }
        }))
    }
}