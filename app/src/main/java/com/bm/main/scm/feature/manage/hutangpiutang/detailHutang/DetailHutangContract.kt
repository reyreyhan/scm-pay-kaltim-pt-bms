package com.bm.main.scm.feature.manage.hutangpiutang.detailHutang

import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.hutangpiutang.DetailHutang
import com.bm.main.scm.models.hutangpiutang.HutangPiutangRestModel
import com.bm.main.scm.models.supplier.Supplier
import com.bm.main.scm.models.supplier.SupplierRestModel

interface DetailHutangContract {

    interface View : BaseViewImpl {
        fun onClose(status:Int)
        fun setSupplier(name: String?,email:String?,phone:String?,address:String?,url:String?)
        fun showMessage(code: Int, msg: String?)
        fun setPiutang(tagihan:String,piutang:String,total:String,jatuhTempo:String)
        fun setList(list:List<DetailHutang.Data>)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun onDestroy()
        fun getTitleName():String
        fun loadDetailSupplier()
        fun loadHutang()

    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetDetailSupplier(context: Context, restModel:SupplierRestModel,id:String)
        fun callGetHutang(context: Context, restModel:HutangPiutangRestModel,id:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetDetailSupplier(data:Supplier)
        fun onSuccessGetHutang(data:DetailHutang)
        fun onFailedAPI(code:Int,msg:String)
    }


}