package com.bm.main.pos.feature.manage.customer.credit

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.transaction.Transaction
import com.bm.main.pos.models.transaction.TransactionRestModel

interface CustomerCreditContract {

    interface View : BaseViewImpl {
        fun setData(list:List<Transaction>)
        fun showErrorMessage(code: Int, msg: String)
        fun openDetail(id:String)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(customer: Customer?)
        fun onDestroy()
        fun loadTransactions()

    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetTransactionsAPI(context: Context, restModel: TransactionRestModel, id:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetTransactions(list:List<Transaction>)
        fun onFailedAPI(code:Int,msg:String)
    }


}