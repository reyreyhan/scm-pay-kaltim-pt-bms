package com.bm.main.pos.feature.report.labarugi.cashflow

import android.content.Context
import com.bm.main.pos.utils.Helper
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.report.ReportLabaRugi

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