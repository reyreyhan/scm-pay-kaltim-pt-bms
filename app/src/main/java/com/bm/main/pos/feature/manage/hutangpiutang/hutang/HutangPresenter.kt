package com.bm.main.pos.feature.manage.hutangpiutang.hutang

import android.content.Context
import com.bm.main.pos.utils.Helper
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.hutangpiutang.Hutang
import com.bm.main.pos.models.hutangpiutang.HutangPiutangRestModel
import com.bm.main.pos.models.transaction.Transaction
import com.bm.main.pos.models.transaction.TransactionRestModel

class HutangPresenter(val context: Context, val view: HutangContract.View) : BasePresenter<HutangContract.View>(),
    HutangContract.Presenter,
    HutangContract.InteractorOutput {

    private var interactor = HutangInteractor(this)
    private var restModel = HutangPiutangRestModel(context)

    override fun onViewCreated() {
        loadHutang()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun loadHutang() {
        interactor.callGetHutangAPI(context,restModel)
    }

    override fun onSuccessGetHutang(data: Hutang) {
        if(data == null){
            onFailedAPI(999,"Data tidak ditemukan")
            return
        }
        val info = data.datapiutang
        val list = data.list

        info?.let {
            view.setInfo(Helper.convertToCurrency(it.jumlah_hutang!!),"Rp "+Helper.convertToCurrency(it.nominal_hutang!!),
                Helper.convertToCurrency(it.jatuh_tempo!!),Helper.convertToCurrency(it.belum_lunas!!))
        }

        if(list == null){
            onFailedAPI(999,"Tidak ada data")
            return
        }
        if(list.isEmpty()){
            onFailedAPI(999,"Tidak ada data")
        }
        else{
            view.setList(list!!)
        }


    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code,msg)
    }
}