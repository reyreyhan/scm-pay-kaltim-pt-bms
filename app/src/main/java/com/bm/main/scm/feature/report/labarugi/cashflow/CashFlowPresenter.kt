package com.bm.main.scm.feature.report.labarugi.cashflow

import android.content.Context
import com.bm.main.scm.base.BasePresenter

class CashFlowPresenter(val context: Context, val view: CashFlowContract.View) : BasePresenter<CashFlowContract.View>(),
    CashFlowContract.Presenter,
    CashFlowContract.InteractorOutput {


    private var interactor = CashFlowInteractor(this)

    override fun onViewCreated() {
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

}