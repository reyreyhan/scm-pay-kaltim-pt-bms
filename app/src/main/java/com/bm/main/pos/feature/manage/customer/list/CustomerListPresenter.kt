package com.bm.main.pos.feature.manage.customer.list

import android.content.Context
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.customer.CustomerRestModel
import com.bm.main.pos.models.discount.Discount
import kotlin.collections.ArrayList

class CustomerListPresenter(val context: Context, val view: CustomerListContract.View) : BasePresenter<CustomerListContract.View>(),
    CustomerListContract.Presenter, CustomerListContract.InteractorOutput {

    private var interactor = CustomerListInteractor(this)
    private var restModel = CustomerRestModel(context)


    override fun onViewCreated() {
        loadCustomers()
    }

    override fun loadCustomers() {
        interactor.callGetCustomersAPI(context,restModel)
    }

    override fun deleteCustomer(id: String) {
        interactor.callDeleteCustomerAPI(context,restModel,id)
    }

    override fun searchCustomer(search: String) {
        interactor.onRestartDisposable()
        if(search.isNullOrEmpty() || search.isNullOrBlank()){
            interactor.callGetCustomersAPI(context,restModel)
        }
        else{
            interactor.callSearchCustomerrAPI(context,restModel,search)
        }
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessGetCustomers(list: List<Customer>) {
        view.setData(list)
    }

    override fun onSuccessDeleteCustomer(msg: String?) {
        view.showSuccessMessage(msg)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code,msg)
    }
}