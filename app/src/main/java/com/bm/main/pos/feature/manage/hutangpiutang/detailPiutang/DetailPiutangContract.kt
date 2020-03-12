package com.bm.main.pos.feature.manage.hutangpiutang.detailPiutang

import android.content.Context
import android.content.Intent
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.customer.CustomerRestModel
import com.bm.main.pos.models.hutangpiutang.DetailPiutang
import com.bm.main.pos.models.hutangpiutang.HutangPiutangRestModel

interface DetailPiutangContract {

    interface View : BaseViewImpl {
        fun onClose(status:Int)
        fun setCustomer(name: String?,email:String?,phone:String?,address:String?,url:String?)
        fun showMessage(code: Int, msg: String?)
        fun setPiutang(tagihan:String,piutang:String,total:String,jatuhTempo:String)
        fun setList(list:List<DetailPiutang.Data>)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun onDestroy()
        fun getTitleName():String
        fun loadDetailCustomer()
        fun loadHutang()


    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetDetailCustomer(context: Context, restModel:CustomerRestModel, id:String)
        fun callGetHutang(context: Context, restModel: HutangPiutangRestModel, id:String)

    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetDetailCustomer(data:Customer)
        fun onFailedAPI(code:Int,msg:String)
        fun onSuccessGetHutang(data:DetailPiutang)

    }


}