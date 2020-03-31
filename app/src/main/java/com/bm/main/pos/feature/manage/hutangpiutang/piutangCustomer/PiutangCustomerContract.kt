package com.bm.main.pos.feature.manage.hutangpiutang.piutangCustomer

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.hutangpiutang.DetailPiutangNew
import com.bm.main.pos.models.hutangpiutang.HutangPiutangRestModel


interface PiutangCustomerContract {

    interface View : BaseViewImpl {
        fun setList(list:List<DetailPiutangNew>)
        fun addItemToAdapter(item:DetailPiutangNew)
        fun showErrorMessage(code: Int, msg: String)
        fun reloadData()
        fun openDetailPiutangPage(data: DetailPiutangNew.Piutang)
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
        fun callGetDetailHutangAPI(context: Context, restModel:HutangPiutangRestModel,id:String)
        fun callGetHutangAPI(context: Context, restModel:HutangPiutangRestModel)
        fun callSearchHutangAPI(context: Context, restModel:HutangPiutangRestModel, search:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetDetailHutang(data: DetailPiutangNew)
        fun onSuccessGetHutang(list:List<Customer>)
        fun onFailedAPI(code:Int,msg:String)
    }


}