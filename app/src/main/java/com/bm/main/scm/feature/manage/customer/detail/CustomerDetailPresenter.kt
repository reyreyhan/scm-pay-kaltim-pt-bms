package com.bm.main.scm.feature.manage.customer.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.customer.Customer
import com.bm.main.scm.models.customer.CustomerRestModel
import com.bm.main.scm.utils.AppConstant

class CustomerDetailPresenter(val context: Context, val view: CustomerDetailContract.View) : BasePresenter<CustomerDetailContract.View>(),
    CustomerDetailContract.Presenter,
    CustomerDetailContract.InteractorOutput {

    private var interactor = CustomerDetailInteractor(this)
    private var title = ""
    private var data : Customer?= null
    private var isTransaction:Boolean = false
    private val restModel = CustomerRestModel(context)


    override fun onViewCreated(intent: Intent) {
        data = intent.getSerializableExtra(AppConstant.DATA) as Customer
        isTransaction = intent.getBooleanExtra("isTransaction", false)
        if(data == null){
            view.onClose(Activity.RESULT_CANCELED)
            return
        }

        data?.let {
            title = it.nama_pelanggan!!
            view.setCustomer(it.nama_pelanggan,it.email,it.telpon,it.alamat,it.gbr)
        }

        if (isTransaction){
            view.showButtonTransaction()
        }
    }

    override fun getTitleName(): String {
        return title
    }

    override fun setCustomerData(dt: Customer) {
        data = dt
        data?.let {
            title = it.nama_pelanggan!!
            view.setCustomer(it.nama_pelanggan,it.email,it.telpon,it.alamat,it.gbr)
        }
    }

    override fun getCustomerData(): Customer? {
        return data
    }

    override fun loadData() {
        data?.id_pelanggan?.let { interactor.callGetDetailCustomer(context, restModel, it) }
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessGetDetail(data: Customer) {
        setCustomerData(data)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showMessage(code, msg)
    }
}