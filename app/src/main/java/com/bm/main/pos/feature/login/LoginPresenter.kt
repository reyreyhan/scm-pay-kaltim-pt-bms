package com.bm.main.pos.feature.login

import android.content.Context
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.user.User
import com.bm.main.pos.models.user.UserRestModel
import com.bm.main.pos.utils.Helper

class LoginPresenter(val context: Context, val view: LoginContract.View) : BasePresenter<LoginContract.View>(),
    LoginContract.Presenter, LoginContract.InteractorOutput {

    private var interactor: LoginInteractor = LoginInteractor(this)
    private var userRestModel = UserRestModel(context)


    override fun onViewCreated() {
        interactor.clearSession()
    }

    override fun onBtnLoginCheck(phone: String, password: String) {
        if(phone.isEmpty()){
            view.enableLoginBtn(false)
            return
        }
        if(!Helper.isPhoneValid(phone)){
            view.enableLoginBtn(false)
            return
        }
        if(password.isEmpty()){
            view.enableLoginBtn(false)
            return
        }
        if(password.length > 5){
            view.enableLoginBtn(true)
            return
        }
        view.enableLoginBtn(false)
    }

    override fun onLogin(phone: String, password: String) {
        view.showLoadingDialog()
        interactor.callLoginAPI(context,userRestModel,phone,password)
    }

    override fun onSuccessLogin(list: List<User>) {
        view.hideLoadingDialog()
        if(list.isEmpty()){
            onFailedAPI(999,"User tidak ditemukan")
            return
        }

        val user = list[0]
        interactor.saveSession(user)
        view.showLoginSuccess()
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.hideLoadingDialog()
        view.showToast(msg)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }


}