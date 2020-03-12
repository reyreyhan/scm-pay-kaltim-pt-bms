package com.bm.main.pos.feature.manage.customer.credit

import android.content.Context
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.transaction.Transaction
import com.bm.main.pos.models.transaction.TransactionRestModel

class CustomerCreditPresenter(val context: Context, val view: CustomerCreditContract.View) : BasePresenter<CustomerCreditContract.View>(),
    CustomerCreditContract.Presenter,
    CustomerCreditContract.InteractorOutput {


    private var interactor = CustomerCreditInteractor(this)
    private var restModel = TransactionRestModel(context)
    private var data: Customer? = null

    override fun onViewCreated(customer: Customer?) {
        this.data = customer
        loadTransactions()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun loadTransactions() {
        interactor.callGetTransactionsAPI(context,restModel,data?.id_pelanggan!!)
    }


    override fun onSuccessGetTransactions(list: List<Transaction>) {
        if(list.isEmpty()){
            view.showErrorMessage(999,"Belum ada transaksi")
            return
        }
        view.setData(list)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code,msg)
    }
}