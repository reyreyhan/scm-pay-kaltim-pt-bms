package com.bm.main.scm.feature.home.cashier;

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.scm.models.user.User
import com.bm.main.scm.models.user.UserRestModel
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.utils.AppConstant
import com.bm.main.scm.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class HomeCashierInteractor(var output: HomeCashierContract.InteractorOutput?) : HomeCashierContract.Interactor {

    private var appSession = AppSession()
    private var disposable = CompositeDisposable()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun saveUser(user: User) {
        //val token = user.key
      //  appSession.setSharedPreferenceString(AppConstant.TOKEN,token)
        appSession.setSharedPreferenceString(AppConstant.USER,user.json())
    }

    override fun callGetProfileAPI(context: Context, restModel: UserRestModel) {
       // val key = PreferenceClass.getTokenPos()
        val key =PreferenceClass.getTokenPos()
        disposable.add(restModel.getProfile(key!!).subscribeWith(object : DisposableObserver<List<User>>() {

            override fun onNext(@NonNull response: List<User>) {
                output?.onSuccessGetProfile(response)
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




//    override fun callLoginAPI(context: Context, restModel: UserRestModel, phone: String, password: String) {
//        disposable.add(restModel.login(phone,password).subscribeWith(object : DisposableObserver<List<User>>() {
//
//            override fun onNext(@NonNull response: List<User>) {
//                output?.onSuccessLogin(response)
//            }
//
//            override fun onError(@NonNull e: Throwable) {
//                e.printStackTrace()
//                var errorCode = 999
//                var errorMessage = "Terjadi kesalahan"
//                if (e is RestException) {
//                    errorCode = e.errorCode
//                    errorMessage = e.message ?: "Terjadi kesalahan"
//                }
//                else{
//                    errorMessage = e.message.toString()
//                }
//                output?.onFailedAPI(errorCode,errorMessage)
//            }
//
//            override fun onComplete() {
//
//            }
//        }))
//    }




}