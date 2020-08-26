package com.bm.main.scm.feature.manage.hutangpiutang.piutang

import android.content.Context
import com.bm.main.scm.utils.Helper
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.hutangpiutang.HutangPiutangRestModel
import com.bm.main.scm.models.hutangpiutang.Piutang

class PiutangPresenter(val context: Context, val view: PiutangContract.View) : BasePresenter<PiutangContract.View>(),
    PiutangContract.Presenter,
    PiutangContract.InteractorOutput {

    private var interactor = PiutangInteractor(this)
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

    override fun onSuccessGetHutang(data: Piutang) {
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