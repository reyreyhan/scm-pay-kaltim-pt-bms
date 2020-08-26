package com.bm.main.scm.feature.manage.customer.detail

import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.customer.Customer
import com.bm.main.scm.models.customer.CustomerRestModel

interface CustomerDetailContract {

    interface View : BaseViewImpl {
        fun onClose(status:Int)
        fun setCustomer(name: String?,email:String?,phone:String?,address:String?,url:String?)
//        fun hideShowToolbar(isShow:Boolean)
        fun openEditPage()
        fun showButtonTransaction()
        fun showMessage(code: Int, msg: String?)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onDestroy()
        fun onViewCreated(intent: Intent)
        fun getTitleName():String
        fun setCustomerData(data:Customer)
        fun getCustomerData():Customer?
        fun loadData()
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetDetailCustomer(context: Context, restModel: CustomerRestModel, id:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetDetail(data:Customer)
        fun onFailedAPI(code:Int,msg:String)
    }
}