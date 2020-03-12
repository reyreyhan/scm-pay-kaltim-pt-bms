package com.bm.main.pos.feature.drawer


import android.content.Context
import android.os.Handler
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.models.user.User
import com.bm.main.pos.models.user.UserRestModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
class DrawerInteractor(var output: DrawerContract.InteractorOutput?) : DrawerContract.Interactor {
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
        appSession.setSharedPreferenceString(AppConstant.USER,user.json())
    }

    override fun callGetProfileAPI(context: Context, restModel: UserRestModel) {
        val key = PreferenceClass.getTokenPos()
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
}