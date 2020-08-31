package com.bm.main.scm.feature.manage.cashier.add

import android.content.Context
import com.bm.main.scm.base.BasePresenter

class CashierAddPresenter(val context: Context, val view: CashierAddContract.View) : BasePresenter<CashierAddContract.View>(),
    CashierAddContract.Presenter, CashierAddContract.InteractorOutput {

//    private var interactor: LoginInteractor = LoginInteractor(this)
//    private var userRestModel = UserRestModel(context)


    override fun onViewCreated() {
//        interactor.clearSession()
    }

    override fun onDestroy() {
//        interactor.onDestroy()
    }


}