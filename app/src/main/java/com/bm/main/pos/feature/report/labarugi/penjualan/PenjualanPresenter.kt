package com.bm.main.pos.feature.report.labarugi.penjualan

import android.content.Context
import com.bm.main.pos.utils.Helper
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.report.ReportLabaRugi

class PenjualanPresenter(val context: Context, val view: PenjualanContract.View) : BasePresenter<PenjualanContract.View>(),
    PenjualanContract.Presenter,
    PenjualanContract.InteractorOutput {


    private var interactor = PenjualanInteractor(this)

    override fun onViewCreated() {
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onCheck(list: List<ReportLabaRugi.Penjualan>?) {
        if(list == null){
            view.showErrorMessage(999,"Tidak ada data")
            return
        }
        if(list.isEmpty()){
            view.showErrorMessage(999,"Tidak ada data")
        }
        else{
            view.setList(list)
        }
    }
}