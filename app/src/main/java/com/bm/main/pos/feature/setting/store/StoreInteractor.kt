package com.bm.main.pos.feature.setting.store

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.google.gson.Gson
import com.bm.main.pos.models.Message
import com.bm.main.pos.models.store.Store
import com.bm.main.pos.models.store.StoreRestModel
import com.bm.main.pos.models.user.User
import com.bm.main.pos.models.user.UserRestModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class StoreInteractor(var output: StoreContract.InteractorOutput?) : StoreContract.Interactor {

    private var disposable = CompositeDisposable()
    private val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callGetStoreAPI(context: Context, model: StoreRestModel) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(model.getStore(key!!).subscribeWith(object : DisposableObserver<List<Store>>() {

            override fun onNext(@NonNull response: List<Store>) {
                output?.onSuccessGetStore(response)
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


    override fun callUpdateAPI(context: Context, model: StoreRestModel, name:String,email:String,phone:String,address:String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(model.updateStore(key!!,name,email,phone,address).subscribeWith(object : DisposableObserver<Message>() {

            override fun onNext(@NonNull response: Message) {
                output?.onSuccessUpdate(response.message)
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