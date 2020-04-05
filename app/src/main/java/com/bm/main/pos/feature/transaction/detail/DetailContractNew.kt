package com.bm.main.pos.feature.transaction.detail

import android.content.Context
import android.content.Intent
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.transaction.DetailTransaction
import com.bm.main.pos.models.transaction.TransactionRestModel

interface DetailContractNew {

    interface View : BaseViewImpl {
        fun reloadData()
        fun onClose(status: Int)
        fun showMessage(code: Int, msg: String?)
        fun setInfo(id: String,
                    total: String?,
                    date: String?,
                    payment: String,
                    bayar: String?,
                    kembalian: String?,
                    hutang: String?)
        fun setProducts(list: List<DetailTransaction.Data>)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun onDestroy()
        fun loadDetail()
        fun getDataStruk(): DetailTransaction
        fun getTypeTRX(): Int
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetDetailAPI(context: Context, restModel: TransactionRestModel, id: String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetDetail(detail: DetailTransaction?)
        fun onFailedAPI(code: Int, msg: String)
    }
}