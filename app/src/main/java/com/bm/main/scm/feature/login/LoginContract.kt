package com.bm.main.scm.feature.login

import android.content.Context
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.cashier.CashierRestModel
import com.bm.main.scm.models.cashier.LoginCashier
import com.bm.main.scm.models.user.merchant.MerchantUser
import com.bm.main.scm.models.user.merchant.MerchantUserRestModel

interface LoginContract {

    interface View : BaseViewImpl {
        fun enableLoginBtn(isLogin:Boolean)
//        fun showLoginSuccess()
        fun getPin():String
        fun openRegisterPage()
        fun navigateMerchant()
        fun navigateCashier()
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onDestroy()
        fun onViewCreated()
        fun onBtnLoginCheck(phone:String,password:String)
        fun onLogin(phone:String,password:String)
        fun changeLogin(isMerchant:Boolean)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun saveSession(user: MerchantUser)
        fun saveSessionCashier(user: LoginCashier)
        fun savePin(pin:String)
        fun clearSession()
        fun callMerchantLoginAPI(context: Context, restModel: MerchantUserRestModel, phone:String, password:String)
        fun callCashierLoginAPI(context: Context, restModel: CashierRestModel, phone:String, password:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessLogin(list:MerchantUser)
        fun onSuccessCashierLogin(list:LoginCashier)
        fun onFailedAPI(code:Int,msg:String)
    }


}