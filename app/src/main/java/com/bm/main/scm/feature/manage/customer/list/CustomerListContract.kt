package com.bm.main.scm.feature.manage.customer.list

import android.content.Context
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.customer.Customer
import com.bm.main.scm.models.customer.CustomerRestModel


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