package com.bm.main.pos.feature.login

import android.content.Context
import android.content.Intent
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.user.User
import com.bm.main.pos.models.user.UserRestModel

interface LoginContract {

    interface View : BaseViewImpl {
        fun enableLoginBtn(isLogin:Boolean)
        fun showLoginSuccess()
        fun openRegisterPage()
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onDestroy()
        fun onViewCreated()
        fun onBtnLoginCheck(phone:String,password:String)
        fun onLogin(phone:String,password:String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun saveSession(user:User)
        fun clearSession()
        fun callLoginAPI(context: Context, restModel: UserRestModel, phone:String, password:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessLogin(list:List<User>)
        fun onFailedAPI(code:Int,msg:String)
    }


}