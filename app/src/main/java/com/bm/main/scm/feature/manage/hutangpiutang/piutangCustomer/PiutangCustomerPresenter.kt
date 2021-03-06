package com.bm.main.scm.feature.manage.hutangpiutang.piutangCustomer

import android.content.Context
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.customer.CustomerNew
import com.bm.main.scm.models.hutangpiutang.HutangPiutangRestModel

class PiutangCustomerPresenter(val context: Context, val view: PiutangCustomerContract.View) : BasePresenter<PiutangCustomerContract.View>(),
    PiutangCustomerContract.Presenter, PiutangCustomerContract.InteractorOutput {

    private var interactor = PiutangCustomerInteractor(this)
    private var restModel = HutangPiutangRestModel(context)

    override fun onViewCreated() {
    }

    override fun loadHutang() {
        interactor.callGetHutangAPI(context,restModel)
    }

    override fun searchHutang(search: String) {
        interactor.onRestartDisposable()
        if(search.isNullOrEmpty() || search.isNullOrBlank()){
            interactor.callGetHutangAPI(context,restModel)
        }
        else{
            interactor.callSearchHutangAPI(context,restModel,search)
        }
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessGetHutang(list: List<CustomerNew>) {
        view.setList(list)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code,msg)
    }

}