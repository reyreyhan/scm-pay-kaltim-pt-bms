package com.bm.main.scm.feature.manage.hutangpiutang.lastHutang

import android.content.Context
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.hutangpiutang.Hutang
import com.bm.main.scm.models.hutangpiutang.HutangPiutangRestModel


interface LastHutangContract {

    interface View : BaseViewImpl {
        fun setData(list:List<Hutang.Data>)
        fun showErrorMessage(code: Int, msg: String)
        fun reloadData()
        fun openDetailStruk(id: String)

    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
        fun loadHutang()
        fun searchHutang(search:String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetHutangAPI(context: Context, restModel:HutangPiutangRestModel)
        fun callSearchHutangAPI(context: Context, restModel:HutangPiutangRestModel, search:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetHutang(list:List<Hutang.Data>)
        fun onFailedAPI(code:Int,msg:String)
    }


}