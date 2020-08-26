package com.bm.main.scm.feature.login

import android.content.Context
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.user.User
import com.bm.main.scm.models.user.UserRestModel

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