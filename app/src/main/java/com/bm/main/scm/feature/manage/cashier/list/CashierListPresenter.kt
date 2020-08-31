package com.bm.main.scm.feature.manage.cashier.list

import android.content.Context
import com.bm.main.scm.base.BasePresenter

class CashierListPresenter(val context: Context, val view: CashierListContract.View) : BasePresenter<CashierListContract.View>(),
    CashierListContract.Presenter, CashierListContract.InteractorOutput {

//    private var interactor: LoginInteractor = LoginInteractor(this)
//    private var userRestModel = UserRestModel(context)


    override fun onViewCreated() {
//        interactor.clearSession()
    }

    override fun onDestroy() {
//        interactor.onDestroy()
    }


}