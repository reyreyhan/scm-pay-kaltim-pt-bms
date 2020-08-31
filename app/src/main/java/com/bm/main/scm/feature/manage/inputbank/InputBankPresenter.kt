package com.bm.main.scm.feature.manage.inputbank

import android.content.Context
import com.bm.main.scm.base.BasePresenter

class InputBankPresenter(val context: Context, val view: InputBankContract.View) : BasePresenter<InputBankContract.View>(),
    InputBankContract.Presenter, InputBankContract.InteractorOutput {

//    private var interactor: LoginInteractor = LoginInteractor(this)
//    private var userRestModel = UserRestModel(context)


    override fun onViewCreated() {
//        interactor.clearSession()
    }

    override fun onDestroy() {
//        interactor.onDestroy()
    }


}