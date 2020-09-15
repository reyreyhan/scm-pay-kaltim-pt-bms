package com.bm.main.scm.feature.loginscm

import android.content.Context
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.cashier.CashierRestModel
import com.bm.main.scm.models.cashier.LoginCashier
import com.bm.main.scm.models.user.merchant.MerchantUser
import com.bm.main.scm.models.user.merchant.MerchantUserRestModel
import com.google.gson.Gson
import timber.log.Timber

class LoginPresenter(val context: Context, val view: LoginContract.View) :
    BasePresenter<LoginContract.View>(),
    LoginContract.Presenter, LoginContract.InteractorOutput {

    var interactor: LoginInteractor = LoginInteractor(this)
    private var merchantUserRestModel = MerchantUserRestModel(context)
    private var cashierRestModel = CashierRestModel(context)
    private var isLoginMerchant = true


    override fun onViewCreated() {
        interactor.clearSession()
    }

    override fun onBtnLoginCheck(phone: String, password: String) {
        if (phone.isEmpty()) {
            view.enableLoginBtn(false)
            return
        }
//        if(!Helper.isPhoneValid(phone)){
//            view.enableLoginBtn(false)
//            return
//        }
        if (password.isEmpty()) {
            view.enableLoginBtn(false)
            return
        }
        if (password.length > 5) {
            view.enableLoginBtn(true)
            return
        }
        view.enableLoginBtn(false)
    }

    override fun onLogin(phone: String, password: String) {
        view.showLoadingDialog()
        if (isLoginMerchant) {
            interactor.callMerchantLoginAPI(context, merchantUserRestModel, phone, password)
        } else {
            interactor.callCashierLoginAPI(context, cashierRestModel, phone, password)
        }
    }

    override fun changeLogin(isMerchant: Boolean) {
        isLoginMerchant = isMerchant
    }

    override fun onSuccessLogin(list: MerchantUser) {
        if (isLoginMerchant) {
            view.checkQRISStatus(list)
        }
    }

    override fun onSuccessCashierLogin(list: LoginCashier) {
        view.hideLoadingDialog()
        val gson = Gson()
        Timber.d("LoginCashier %s", gson.toJson(list))
        interactor.saveSessionCashier(list)
        interactor.savePin(view.getPin())
        if (!isLoginMerchant) {
            view.navigateCashier()
        }
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.hideLoadingDialog()
        view.showToast(msg)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }
}