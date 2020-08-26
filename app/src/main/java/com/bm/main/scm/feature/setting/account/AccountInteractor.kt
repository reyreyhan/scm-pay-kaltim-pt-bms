package com.bm.main.scm.feature.setting.account

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.scm.models.Message
import com.bm.main.scm.models.user.User
import com.bm.main.scm.models.user.UserRestModel
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.utils.AppConstant
import com.bm.main.scm.utils.AppSession
import com.google.gson.Gson
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class AccountInteractor(var output: AccountContract.InteractorOutput?) : AccountContract.Interactor {

    private var disposable = CompositeDisposable()
    private val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun loadProfile(context: Context): User? {
        val json = appSession.getSharedPreferenceString(context,AppConstant.USER) ?: return null
        return Gson().fromJson(json,User::class.java)
    }

    override fun callUpdateAPI(context: Context, model: UserRestModel, name:String,email:String,phone:String,address:String,gbr:String?) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(model.updateProfile(key!!,name,email,phone,address,gbr).subscribeWith(object : DisposableObserver<Message>() {

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