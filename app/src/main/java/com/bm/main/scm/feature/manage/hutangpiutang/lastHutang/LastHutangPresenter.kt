package com.bm.main.scm.feature.manage.hutangpiutang.lastHutang

import android.content.Context
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.hutangpiutang.Hutang
import com.bm.main.scm.models.hutangpiutang.HutangPiutangRestModel

class LastHutangPresenter(val context: Context, val view: LastHutangContract.View) : BasePresenter<LastHutangContract.View>(),
    LastHutangContract.Presenter, LastHutangContract.InteractorOutput {

    private var interactor: LastHutangInteractor = LastHutangInteractor(this)
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

    override fun onSuccessGetHutang(list: List<Hutang.Data>) {
        view.setData(list)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code,msg)
    }


}