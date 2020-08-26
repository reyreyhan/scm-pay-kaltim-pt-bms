package com.bm.main.scm.feature.sell.chooseCustomer

import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.customer.Customer
import com.bm.main.scm.models.customer.CustomerRestModel

class ChooseCustomerPresenter(val context: Context, val view: ChooseCustomerContract.View) : BasePresenter<ChooseCustomerContract.View>(),
    ChooseCustomerContract.Presenter, ChooseCustomerContract.InteractorOutput {

    var isTransaction = false
    private var interactor  = ChooseCustomertInteractor(this)
    private var restModel = CustomerRestModel(context)

    override fun onViewCreated(intent: Intent) {
        isTransaction = intent.getBooleanExtra("isTransaction", false)
        if (intent.getBooleanExtra("isTransaction", false)){
            view.setBackgroundButtonAddCustomer()
        }
        loadData()
    }

    override fun loadData() {
        interactor.callGetDataAPI(context,restModel)
    }

    override fun search(search: String) {
        interactor.onRestartDisposable()
        if(search.isNullOrEmpty() || search.isNullOrBlank()){
            interactor.callGetDataAPI(context,restModel)
        }
        else{
            interactor.callSearchAPI(context,restModel,search)
        }
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessGetData(list: List<Customer>) {
        view.setData(list)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code,msg)
    }
}