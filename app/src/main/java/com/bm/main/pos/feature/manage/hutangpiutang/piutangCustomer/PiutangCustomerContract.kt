package com.bm.main.pos.feature.manage.hutangpiutang.piutangCustomer

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.customer.CustomerNew
import com.bm.main.pos.models.hutangpiutang.HutangPiutangRestModel


interface PiutangCustomerContract {

    interface View : BaseViewImpl {
        fun setList(list:List<CustomerNew>)
        fun addItemToAdapter(item:CustomerNew)
        fun showErrorMessage(code: Int, msg: String)
        fun reloadData()
        fun openDetailPiutangPage(data: CustomerNew)
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
        fun onSuccessGetHutang(list:List<CustomerNew>)
        fun onFailedAPI(code:Int,msg:String)
    }


}