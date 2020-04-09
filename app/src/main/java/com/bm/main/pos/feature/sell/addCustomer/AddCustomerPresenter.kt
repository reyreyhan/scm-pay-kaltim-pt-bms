package com.bm.main.pos.feature.sell.addCustomer

import android.content.Context
import com.bm.main.pos.R
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.customer.CustomerRestModel
import com.bm.main.pos.utils.Helper


class AddCustomerPresenter(val context: Context, val view: AddCustomerContract.View) : BasePresenter<AddCustomerContract.View>(),
    AddCustomerContract.Presenter, AddCustomerContract.InteractorOutput {

    private var interactor = AddCustomerInteractor(this)
    private var restModel = CustomerRestModel(context)

    override fun onViewCreated() {

    }

    override fun onCheck(name:String,email:String,phone:String) {
        if(name.isNullOrBlank() || name.isNullOrEmpty()){
            view.showMessage(999,context.getString(R.string.err_empty_name))
            return
        }

//        if(email.isNullOrBlank() || email.isNullOrEmpty()){
//            view.showMessage(999,context.getString(R.string.err_empty_email))
//            return
//        }

//        if(!email.isNullOrEmpty() || !email.isNullOrBlank()){
//            if(!Helper.isEmailValid(email)){
//                view.showMessage(999,context.getString(R.string.err_email_format))
//                return
//            }
//        }

        if(phone.isNullOrBlank() || phone.isNullOrEmpty()){
            view.showMessage(999,context.getString(R.string.err_empty_phone))
            return
        }

        if(!Helper.isPhoneValid(phone)){
            view.showMessage(999,context.getString(R.string.err_phone_format))
            return
        }

        interactor.callAddCustomerAPI(context,restModel,name,email,phone)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessAdd(data:Customer) {
        view.onSuccess(data)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showMessage(code,msg)
    }
}