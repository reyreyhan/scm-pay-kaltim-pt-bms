package com.bm.main.scm.feature.cashout

import android.content.Context
import com.bm.main.scm.base.BasePresenter

class CashoutPresenter(val context: Context, val view: CashoutContract.View) : BasePresenter<CashoutContract.View>(),
    CashoutContract.Presenter, CashoutContract.InteractorOutput {

//    private var interactor: LoginInteractor = LoginInteractor(this)
//    private var userRestModel = UserRestModel(context)


    override fun onViewCreated() {
//        interactor.clearSession()
    }

    override fun onDestroy() {
//        interactor.onDestroy()
    }


}