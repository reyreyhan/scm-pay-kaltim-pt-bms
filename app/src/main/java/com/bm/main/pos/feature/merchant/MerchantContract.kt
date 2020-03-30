package com.bm.main.pos.feature.merchant

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.transaction.HistoryTransaction
import com.bm.main.pos.models.transaction.Transaction
import com.bm.main.pos.models.transaction.TransactionRestModel
import com.bm.main.pos.models.user.User
import com.bm.main.pos.models.user.UserRestModel

interface MerchantContract {
    interface View : BaseViewImpl{
        fun reloadData()
        fun setList(list:List<Transaction>)
        fun showErrorMessage(code: Int, msg: String)
        fun openDetail(id:String)
        fun setProfile(name:String,address:String,city:String,phone:String,url:String,omset:String)
    }

    interface Presenter : BasePresenterImpl<View>{
        fun onViewCreated()
        fun onDestroy()
        fun loadProfile()
        fun loadTransaction()
    }

    interface Interactor : BaseInteractorImpl{
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetProfileAPI(context: Context, restModel: UserRestModel)
        fun callGetHistoryAPI(context: Context, restModel: TransactionRestModel, awal:String, akhir:String, status:String)
        fun saveUser(user: User)
    }

    interface InteractorOutput : BaseInteractorOutputImpl{
        fun onSuccessGetProfile(list:List<User>)
        fun onSuccessGetHistory(list:List<HistoryTransaction>?)
        fun onFailedAPI(code:Int,msg:String)
    }
}