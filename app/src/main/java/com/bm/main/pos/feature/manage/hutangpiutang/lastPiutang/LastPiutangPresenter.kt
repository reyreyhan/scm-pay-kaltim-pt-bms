package com.bm.main.pos.feature.manage.hutangpiutang.lastPiutang

import android.content.Context
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.hutangpiutang.Hutang
import com.bm.main.pos.models.hutangpiutang.HutangPiutangRestModel
import com.bm.main.pos.models.hutangpiutang.Piutang

class LastPiutangPresenter(val context: Context, val view: LastPiutangContract.View) : BasePresenter<LastPiutangContract.View>(),
    LastPiutangContract.Presenter, LastPiutangContract.InteractorOutput {

    private var interactor = LastPiutangInteractor(this)
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

    override fun onSuccessGetHutang(list: List<Piutang.Data>) {
        view.setData(list)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code,msg)
    }


}