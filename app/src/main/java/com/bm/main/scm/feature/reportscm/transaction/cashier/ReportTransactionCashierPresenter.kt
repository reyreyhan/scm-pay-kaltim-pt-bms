package com.bm.main.scm.feature.reportscm.transaction.cashier

import android.content.Context
import com.bm.main.scm.base.BasePresenter

class ReportTransactionCashierPresenter(val context: Context, val view: ReportTransactionCashierContract.View) : BasePresenter<ReportTransactionCashierContract.View>(),
    ReportTransactionCashierContract.Presenter, ReportTransactionCashierContract.InteractorOutput {

//    private var interactor: LoginInteractor = LoginInteractor(this)
//    private var userRestModel = UserRestModel(context)


    override fun onViewCreated() {
//        interactor.clearSession()
    }

    override fun onDestroy() {
//        interactor.onDestroy()
    }


}