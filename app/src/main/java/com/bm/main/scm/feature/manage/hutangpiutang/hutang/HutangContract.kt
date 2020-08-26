package com.bm.main.scm.feature.manage.hutangpiutang.hutang

import android.content.Context
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.hutangpiutang.Hutang
import com.bm.main.scm.models.hutangpiutang.HutangPiutangRestModel

interface HutangContract {

    interface View : BaseViewImpl {
        fun setInfo(sum:String,sumRupiah:String,jatuhTempo:String,belumLunas:String)
        fun setList(list:List<Hutang.Data>)
        fun showErrorMessage(code: Int, msg: String)
        fun openLastHutangPage()
        fun openHutangPage()
        fun openDetail(id: String)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
        fun loadHutang()

    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetHutangAPI(context: Context, restModel: HutangPiutangRestModel)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetHutang(data:Hutang)
        fun onFailedAPI(code:Int,msg:String)
    }


}