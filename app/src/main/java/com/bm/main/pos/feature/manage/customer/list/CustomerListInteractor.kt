package com.bm.main.pos.feature.manage.customer.list

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.models.Message
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.customer.CustomerRestModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class CustomerListInteractor(var output: CustomerListContract.InteractorOutput?) :
    CustomerListContract.Interactor {

    private var disposable = CompositeDisposable()
    private val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callGetCustomersAPI(context: Context, restModel: CustomerRestModel) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.gets(key!!).subscribeWith(object : DisposableObserver<List<Customer>>() {

            override fun onNext(@NonNull response: List<Customer>) {
                output?.onSuccessGetCustomers(response)
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

    override fun callDeleteCustomerAPI(context: Context, restModel: CustomerRestModel, id: String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.delete(key!!,id).subscribeWith(object : DisposableObserver<Message>() {

            override fun onNext(@NonNull response: Message) {
                output?.onSuccessDeleteCustomer(response.message)
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

    override fun callSearchCustomerrAPI(context: Context, restModel: CustomerRestModel, search: String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.search(key!!,search).subscribeWith(object : DisposableObserver<List<Customer>>() {

            override fun onNext(@NonNull response: List<Customer>) {
                output?.onSuccessGetCustomers(response)
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