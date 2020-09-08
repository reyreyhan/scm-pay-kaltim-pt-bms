package com.bm.main.scm.feature.manage.cashier.list

import android.content.Context
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.cashier.Cashier
import com.bm.main.scm.models.cashier.CashierRestModel
import com.bm.main.scm.models.cashier.CashierSuccessManage

interface CashierListContract {

    interface View : BaseViewImpl {
        fun setListAdapter(list:List<CashierObject>)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onDestroy()
        fun onViewCreated()
        fun loadList()
        fun blockCashier(idCashier: Int, isBlock: Int)
        fun deleteCashier(idCashier: Int, isDeleted: Int)
        fun editCashier(idCashier: Int, name: String, telp: String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callApiListCashier(context: Context, cashierRestModel: CashierRestModel)
        fun callApiBlockCashier(context: Context, cashierRestModel: CashierRestModel, idCashier:Int, idBlock:Int)
        fun callApiDeleteCashier(context: Context, cashierRestModel: CashierRestModel, idCashier:Int, isDeleted:Int)
        fun callApiEditCashier(context: Context, cashierRestModel: CashierRestModel, idCashier:Int, name:String, telp:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessListCashier(list:List<Cashier>)
        fun onSuccessBlockCashier(list:CashierSuccessManage)
        fun onSuccessDeleteCashier(list: CashierSuccessManage)
        fun onSuccessEditCashier(list:CashierSuccessManage)
        fun onFailure(code:Int, msg:String)
    }
}