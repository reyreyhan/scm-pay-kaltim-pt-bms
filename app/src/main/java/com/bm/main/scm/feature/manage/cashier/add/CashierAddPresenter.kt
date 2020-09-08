package com.bm.main.scm.feature.manage.cashier.add

import android.content.Context
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.cashier.AddCashierResponse
import com.bm.main.scm.models.cashier.CashierRestModel

class CashierAddPresenter(val context: Context, val view: CashierAddContract.View) : BasePresenter<CashierAddContract.View>(),
    CashierAddContract.Presenter, CashierAddContract.InteractorOutput {

    private var interactor: CashierAddInteractor = CashierAddInteractor(this)
    private var cashierRestModel = CashierRestModel(context)

    override fun onViewCreated() {
    }

    override fun addCashier(telp: String, name: String, password: String) {
        interactor.callMerchantLoginAPI(context, cashierRestModel, telp, name, password)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    fun checkForm(telp:String, name:String, password:String){
        if (telp.isEmpty()){
            view.enableAddButton(false)
            return
        }
        if (name.isEmpty()){
            view.enableAddButton(false)
            return
        }
        if (password.isEmpty()){
            view.enableAddButton(false)
            return
        }
        view.enableAddButton(true)
    }

    override fun onSuccessAdd(list: AddCashierResponse) {
        view.dialogSuccess("Selamat Pendaftaran Cashier Berhasil!",
            "Cashier Anda telah ditambahkan!")
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.toastError(msg)
    }


}