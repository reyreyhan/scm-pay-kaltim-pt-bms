package com.bm.main.pos.feature.manage.customer.list

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.customer.CustomerRestModel
import com.bm.main.pos.models.discount.Discount


interface CustomerListContract {

    interface View : BaseViewImpl {
        fun setData(list:List<Customer>)
        fun showErrorMessage(code: Int, msg: String)
        fun showSuccessMessage(msg: String?)
        fun reloadData()
        fun openAddPage()
        fun openEditPage(data:Customer)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
        fun loadCustomers()
        fun deleteCustomer(id:String)
        fun searchCustomer(search:String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetCustomersAPI(context: Context, restModel:CustomerRestModel)
        fun callDeleteCustomerAPI(context: Context, restModel:CustomerRestModel, id:String)
        fun callSearchCustomerrAPI(context: Context, restModel:CustomerRestModel, search:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetCustomers(list:List<Customer>)
        fun onSuccessDeleteCustomer(msg: String?)
        fun onFailedAPI(code:Int,msg:String)
    }


}