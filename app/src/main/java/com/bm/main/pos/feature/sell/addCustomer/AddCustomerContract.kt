package com.bm.main.pos.feature.sell.addCustomer

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.customer.CustomerRestModel

interface AddCustomerContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onSuccess(data:Customer)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
        fun onCheck(name:String,email:String,phone:String,address:String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callAddCustomerAPI(context: Context,model:CustomerRestModel,name:String,email:String,phone:String,address:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessAdd(data:List<Customer>)
        fun onFailedAPI(code:Int,msg:String)
    }


}