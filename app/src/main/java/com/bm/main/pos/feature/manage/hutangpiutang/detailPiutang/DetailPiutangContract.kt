package com.bm.main.pos.feature.manage.hutangpiutang.detailPiutang

import android.content.Context
import android.content.Intent
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.Message
import com.bm.main.pos.models.hutangpiutang.DetailPiutangNew
import com.bm.main.pos.models.hutangpiutang.HutangPiutangRestModel
import com.bm.main.pos.models.transaction.TransactionRestModel

interface DetailPiutangContract {

    interface View : BaseViewImpl {
        fun onClose(status:Int)
        fun setCustomer(name: String?)
        fun showMessage(code: Int, msg: String?)
        fun showSuccess(msg:String)
        fun setPiutang(piutang:String?, tanggal:String?)
        fun setList(list:List<DetailPiutangNew.Data>)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun onDestroy()
        fun getTitleName():String
        fun loadHutang()
        fun payHutang(pay:String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetHutang(context: Context, restModel: HutangPiutangRestModel, id:String)
        fun callPayHutang(context: Context, restModel: TransactionRestModel, invoice:String, pay:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onFailedAPI(code:Int,msg:String)
        fun onSuccessGetHutang(data: DetailPiutangNew)
        fun onSuccessPayHutang(msg:Message)
    }
}