package com.bm.main.pos.feature.manage.customer.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.utils.AppConstant

class CustomerDetailPresenter(val context: Context, val view: CustomerDetailContract.View) : BasePresenter<CustomerDetailContract.View>(),
    CustomerDetailContract.Presenter,
    CustomerDetailContract.InteractorOutput {

    private var interactor = CustomerDetailInteractor(this)
    private var title = ""
    private var data : Customer?= null


    override fun onViewCreated(intent: Intent) {
        data = intent.getSerializableExtra(AppConstant.DATA) as Customer
        if(data == null){
            view.onClose(Activity.RESULT_CANCELED)
            return
        }

        data?.let {
            title = it.nama_pelanggan!!
            view.setCustomer(it.nama_pelanggan,it.email,it.telpon,it.alamat,it.gbr)
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

    override fun onDestroy() {
        interactor.onDestroy()
    }
}