package com.bm.main.scm.feature.loginscm;

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.scm.models.cashier.CashierRestModel
import com.bm.main.scm.models.cashier.LoginCashier
import com.bm.main.scm.models.user.merchant.MerchantUser
import com.bm.main.scm.models.user.merchant.MerchantUserRestModel
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.utils.AppConstant
import com.bm.main.scm.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class LoginInteractor(var output: LoginContract.InteractorOutput?) : LoginContract.Interactor {

    private val appSession = AppSession()
    private var disposable = CompositeDisposable()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun clearSession() {
        appSession.clearSession()
    }

    override fun saveSession(user: MerchantUser) {
        val token = user.id_session
        PreferenceClass.setTokenPos(token)
        appSession.setSharedPreferenceString(AppConstant.TOKEN, token)
        appSession.setSharedPreferenceString("FASTPAY_ID", user.fastpay_id)
        appSession.setSharedPreferenceString("OWNER_HP", user.no_telp)
    }

    override fun saveSessionCashier(user: LoginCashier) {
        appSession.setSharedPreferenceString("NO_HP", user.no_hp)
        appSession.setSharedPreferenceString("FASTPAY_ID", user.fastpay_id)
        appSession.setSharedPreferenceString("OWNER_HP", user.no_telp_owner)
    }

    override fun saveSessionLogin(isMerchant: Boolean) {
        appSession.setSharedPreferenceBoolean("IS_LOGGED_IN", true)
        appSession.setSharedPreferenceBoolean("IS_MERCHANT", isMerchant)
    }

    override fun savePin(pin: String) {
        appSession.setSharedPreferenceString("PIN", pin)
    }

    override fun callMerchantLoginAPI(context: Context, restModel: MerchantUserRestModel, phone: String, password: String) {
        disposable.add(restModel.login(phone,password).subscribeWith(object : DisposableObserver<MerchantUser>() {

            override fun onNext(@NonNull response: MerchantUser) {
                output?.onSuccessLogin(response)
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

    override fun callCashierLoginAPI(context: Context, restModel: CashierRestModel, phone: String, password: String) {
        disposable.add(restModel
            .login(phone,password)
            .subscribeWith(object : DisposableObserver<LoginCashier>() {

            override fun onNext(@NonNull response: LoginCashier) {
                output?.onSuccessCashierLogin(response)
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