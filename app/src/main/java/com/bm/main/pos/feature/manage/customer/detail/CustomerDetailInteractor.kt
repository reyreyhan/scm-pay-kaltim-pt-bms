package com.bm.main.pos.feature.manage.customer.detail;

import android.content.Context
import androidx.annotation.NonNull
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.models.Message
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.customer.CustomerRestModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppSession
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class CustomerDetailInteractor(var output: CustomerDetailContract.InteractorOutput?) :
    CustomerDetailContract.Interactor {

    private var disposable = CompositeDisposable()
    private val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callGetDetailCustomer(context: Context, restModel: CustomerRestModel, id: String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.detail(key!!, id).subscribeWith(object : DisposableObserver<Customer>() {

            override fun onNext(@io.reactivex.annotations.NonNull response: Customer) {
                output?.onSuccessGetDetail(response)
            }

            override fun onError(@io.reactivex.annotations.NonNull e: Throwable) {
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