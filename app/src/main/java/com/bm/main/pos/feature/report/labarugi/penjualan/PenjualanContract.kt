package com.bm.main.pos.feature.report.labarugi.penjualan

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.hutangpiutang.Hutang
import com.bm.main.pos.models.hutangpiutang.HutangPiutangRestModel
import com.bm.main.pos.models.hutangpiutang.Piutang
import com.bm.main.pos.models.report.ReportLabaRugi
import com.bm.main.pos.models.transaction.Transaction
import com.bm.main.pos.models.transaction.TransactionRestModel

interface PenjualanContract {

    interface View : BaseViewImpl {
        fun setData(list:List<ReportLabaRugi.Penjualan>?)
        fun setList(list:List<ReportLabaRugi.Penjualan>)
        fun showErrorMessage(code: Int, msg: String)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
        fun onCheck(list:List<ReportLabaRugi.Penjualan>?)

    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
    }


}