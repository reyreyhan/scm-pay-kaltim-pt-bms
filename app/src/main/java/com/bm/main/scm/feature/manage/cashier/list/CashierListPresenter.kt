package com.bm.main.scm.feature.manage.cashier.list

import android.content.Context
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.cashier.Cashier
import com.bm.main.scm.models.cashier.CashierRestModel
import com.bm.main.scm.models.cashier.CashierSuccessManage

class CashierListPresenter(val context: Context, val view: CashierListContract.View) : BasePresenter<CashierListContract.View>(),
    CashierListContract.Presenter, CashierListContract.InteractorOutput {

    private var interactor = CashierListInteractor(this)
    private var cashierRestModel = CashierRestModel(context)


    override fun onViewCreated() {
        loadList()
    }

    override fun loadList() {
        interactor.callApiListCashier(context, cashierRestModel)
    }

    override fun blockCashier(idCashier: Int, isBlock:Int) {
        interactor.callApiBlockCashier(context, cashierRestModel, idCashier, isBlock)
    }

    override fun deleteCashier(idCashier: Int, isDelete:Int) {
    }

    override fun editCashier(idCashier: Int, name: String, telp: String) {
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessListCashier(list: List<Cashier>) {
        val itemList = mutableListOf<CashierObject>()
        list.forEach {c->
            itemList.add(CashierObject(c.id_kasir!!.toInt(), c.nama, c.no_hp, c.is_block != "1"))
        }
        view.setListAdapter(itemList)
    }

    override fun onSuccessBlockCashier(list: CashierSuccessManage) {
        loadList()
    }

    override fun onSuccessDeleteCashier(list: CashierSuccessManage) {
    }

    override fun onSuccessEditCashier(list: CashierSuccessManage) {
    }

    override fun onFailure(code: Int, msg: String) {
    }


}