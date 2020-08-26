package com.bm.main.scm.feature.report.labarugi.cashflow

import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.report.ReportLabaRugi

interface CashFlowContract {

    interface View : BaseViewImpl {
        fun setData(data:ReportLabaRugi.Keuangan?)
        fun showPenjualan()
        fun hidePenjualan()
        fun showLabaRugi()
        fun hideLabaRugi()
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()

    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
    }


}