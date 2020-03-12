package com.bm.main.pos.feature.transaction.historyTransaction

import android.content.Context
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.models.FilterDialogDate
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.hutangpiutang.Hutang
import com.bm.main.pos.models.hutangpiutang.HutangPiutangRestModel
import com.bm.main.pos.models.hutangpiutang.Piutang
import com.bm.main.pos.models.transaction.HistoryTransaction
import com.bm.main.pos.models.transaction.Transaction
import com.bm.main.pos.models.transaction.TransactionRestModel

interface TransactionContract {

    interface View : BaseViewImpl {
        fun reloadData()
        fun setList(list:List<Transaction>)
        fun showErrorMessage(code: Int, msg: String)
        fun openDetail(id:String)
        fun openFilter(data:FilterDialogDate?)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
        fun loadTransaction()
        fun onChangeDate(firstDate: CalendarDay?, lastDate: CalendarDay?)
//        fun getFirstDate():CalendarDay?
//        fun getLastDate():CalendarDay?
//        fun getTodayDate():CalendarDay
        fun onSearch(id:String)
        fun getFilterSelected():DialogModel
        fun getFilters():ArrayList<DialogModel>
        fun onChangeStatus(selected: DialogModel?)
        fun setFilterDateSelected(data:FilterDialogDate?)
        fun getFilterDateSelected():FilterDialogDate?
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetHistoryAPI(context: Context, restModel: TransactionRestModel,awal:String,akhir:String,status:String)
        fun callGetSearchAPI(context: Context, restModel: TransactionRestModel,id:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetHistory(list:List<HistoryTransaction>?)
        fun onSuccessGetSearch(list:List<Transaction>?)
        fun onFailedAPI(code:Int,msg:String)
    }


}