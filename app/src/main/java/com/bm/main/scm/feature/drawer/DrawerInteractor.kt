package com.bm.main.scm.feature.drawer


import android.content.Context
import com.bm.main.scm.models.user.merchant.MerchantToko
import com.bm.main.scm.models.user.merchant.MerchantUser
import com.bm.main.scm.models.user.merchant.MerchantUserRestModel
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.utils.AppConstant
import com.bm.main.scm.utils.AppSession
import com.google.gson.Gson
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import timber.log.Timber

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

    override fun saveUser(user: MerchantUser) {
        appSession.setSharedPreferenceString(AppConstant.USER, user.json())
    }

    override fun saveToko(toko: MerchantToko) {
        appSession.setSharedPreferenceString(AppConstant.TOKO, toko.json())
    }

    override fun checkProfileToko() {
        val user = appSession.getSharedPreferenceString(AppConstant.USER)
        val toko = appSession.getSharedPreferenceString(AppConstant.TOKO)
        if ((user != null && toko != null) && (user != "" && toko != "")){
            output?.onProfileTokoExisting(true)
        }else{
            output?.onProfileTokoExisting(false)
        }
    }

    override fun getLocalProfileToko() {
        val userJson = appSession.getSharedPreferenceString(AppConstant.USER)
        val tokoJson = appSession.getSharedPreferenceString(AppConstant.TOKO)
        val user = Gson().fromJson(userJson, MerchantUser::class.java)
        val toko = Gson().fromJson(tokoJson, MerchantToko::class.java)
        output?.onSuccessGetProfileTokoLocal(user, toko)
    }

    override fun resetAppSession() {
        appSession.clearSession()
    }

    override fun callGetProfileAPI(context: Context, restModel: MerchantUserRestModel) {
        val key = appSession.getToken()
        Timber.d("Token: %s", key)
        disposable.add(restModel.getDetail(key!!).subscribeWith(object : DisposableObserver<List<MerchantUser>>() {

            override fun onNext(@NonNull response: List<MerchantUser>) {
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

    override fun callGetTokoAPI(context: Context, restModel: MerchantUserRestModel) {
        val key = appSession.getToken()
        Timber.d("Token: %s", key)
        disposable.add(restModel.getToko(key!!).subscribeWith(object : DisposableObserver<List<MerchantToko>>() {

            override fun onNext(@NonNull response: List<MerchantToko>) {
                output?.onSuccessGetToko(response)
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