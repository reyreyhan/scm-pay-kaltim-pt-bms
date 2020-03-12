package com.bm.main.pos.feature.manage.hutangpiutang.hutang

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.hutangpiutang.Hutang
import com.bm.main.pos.models.hutangpiutang.HutangPiutangRestModel
import com.bm.main.pos.models.transaction.Transaction
import com.bm.main.pos.models.transaction.TransactionRestModel

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