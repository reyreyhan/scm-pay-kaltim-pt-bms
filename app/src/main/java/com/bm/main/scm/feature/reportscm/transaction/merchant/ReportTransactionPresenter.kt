package com.bm.main.scm.feature.reportscm.transaction.merchant

import android.content.Context
import com.bm.main.scm.base.BasePresenter

class ReportTransactionPresenter(val context: Context, val view: ReportTransactionContract.View) : BasePresenter<ReportTransactionContract.View>(),
    ReportTransactionContract.Presenter, ReportTransactionContract.InteractorOutput {

//    private var interactor: LoginInteractor = LoginInteractor(this)
//    private var userRestModel = UserRestModel(context)


    override fun onViewCreated() {
//        interactor.clearSession()
    }

    override fun onDestroy() {
//        interactor.onDestroy()
    }


}