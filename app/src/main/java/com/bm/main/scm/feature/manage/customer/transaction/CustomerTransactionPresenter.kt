package com.bm.main.scm.feature.manage.customer.transaction

import android.content.Context
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.customer.Customer
import com.bm.main.scm.models.transaction.Transaction
import com.bm.main.scm.models.transaction.TransactionRestModel

class CustomerTransactionPresenter(val context: Context, val view: CustomerTransactionContract.View) : BasePresenter<CustomerTransactionContract.View>(),
    CustomerTransactionContract.Presenter, CustomerTransactionContract.InteractorOutput {

    private var interactor = CustomerTransactionInteractor(this)
    private var restModel = TransactionRestModel(context)
    private var data:Customer? = null

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