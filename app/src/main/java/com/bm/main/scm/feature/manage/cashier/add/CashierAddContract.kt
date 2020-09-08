package com.bm.main.scm.feature.manage.cashier.add

import android.content.Context
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.cashier.AddCashierResponse
import com.bm.main.scm.models.cashier.CashierRestModel

interface CashierAddContract {

    interface View : BaseViewImpl {
        fun toastError(msg:String)
        fun dialogSuccess(title:String, msg:String)
        fun enableAddButton(enable:Boolean)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onDestroy()
        fun onViewCreated()
        fun addCashier(telp:String, name:String, password:String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callMerchantLoginAPI(context: Context, restModel: CashierRestModel, telp:String, name:String, password:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessAdd(list:AddCashierResponse)
        fun onFailedAPI(code:Int,msg:String)
    }
}