package com.bm.main.scm.feature.manage.customer.detail;

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.scm.models.customer.Customer
import com.bm.main.scm.models.customer.CustomerRestModel
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.utils.AppSession
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