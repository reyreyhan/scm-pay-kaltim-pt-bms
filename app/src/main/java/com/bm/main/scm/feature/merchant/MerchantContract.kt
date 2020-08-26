package com.bm.main.scm.feature.merchant

import android.content.Context
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.transaction.HistoryTransaction
import com.bm.main.scm.models.transaction.Transaction
import com.bm.main.scm.models.transaction.TransactionRestModel
import com.bm.main.scm.models.user.User
import com.bm.main.scm.models.user.UserRestModel

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