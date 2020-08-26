package com.bm.main.scm.feature.setting.store

import android.app.Activity
import android.content.Context
import com.bm.main.scm.R
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.utils.Helper
import com.bm.main.scm.models.store.Store
import com.bm.main.scm.models.store.StoreRestModel


class StorePresenter(val context: Context, val view: StoreContract.View) : BasePresenter<StoreContract.View>(),
    StoreContract.Presenter, StoreContract.InteractorOutput {

    private var interactor = StoreInteractor(this)
    private var restModel = StoreRestModel(context)

    override fun onViewCreated() {
        loadStore()
    }

    override fun loadStore() {
        interactor.callGetStoreAPI(context,restModel)
    }

    override fun onCheck(name:String,email:String,phone:String,address:String) {
        if(name.isBlank() || name.isEmpty()){
            view.showMessage(999,context.getString(R.string.err_empty_name))
            return
        }

        if(email.isNotBlank() && email.isNotEmpty()){
            if(!Helper.isEmailValid(email)){
                view.showMessage(999,context.getString(R.string.err_email_format))
                return
            }
        }

        if(phone.isBlank() || phone.isEmpty()){
            view.showMessage(999,context.getString(R.string.err_empty_phone))
            return
        }

        if(!Helper.isPhoneValid(phone)){
            view.showMessage(999,context.getString(R.string.err_phone_format))
            return
        }

        if(address.isBlank() || address.isEmpty()){
            view.showMessage(999,context.getString(R.string.err_empty_address))
            return
        }

        interactor.callUpdateAPI(context,restModel,name,email,phone,address)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessUpdate(msg: String?) {
        view.onClose(msg,Activity.RESULT_OK)
    }

    override fun onSuccessGetStore(list: List<Store>?) {
        if(list == null){
            view.showMessage(999,"Toko tidak ditemukan")
        }

        list?.let {
            if(it.isEmpty()){
                view.showMessage(999,"Toko tidak ditemukan")
            }

            val store = it[0]
            view.setInfo(store)
        }

    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showMessage(code,msg)
    }


}