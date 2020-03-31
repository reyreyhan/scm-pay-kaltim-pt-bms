package com.bm.main.pos.feature.manage.hutangpiutang.piutangCustomer

import android.content.Context
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.hutangpiutang.DetailPiutangNew
import com.bm.main.pos.models.hutangpiutang.HutangPiutangRestModel

class PiutangCustomerPresenter(val context: Context, val view: PiutangCustomerContract.View) : BasePresenter<PiutangCustomerContract.View>(),
    PiutangCustomerContract.Presenter, PiutangCustomerContract.InteractorOutput {

    private var interactor = PiutangCustomerInteractor(this)
    private var restModel = HutangPiutangRestModel(context)

    override fun onViewCreated() {
        loadHutang()
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

    override fun onSuccessGetHutang(list: List<Customer>) {
        for(data in list){
            interactor.callGetDetailHutangAPI(context, restModel, data.id_pelanggan!!)
        }
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code,msg)
    }

    override fun onSuccessGetDetailHutang(data: DetailPiutangNew) {
        if(data == null){
            onFailedAPI(999,"Data tidak ditemukan")
            return
        }
        view.addItemToAdapter(data)
    }


}