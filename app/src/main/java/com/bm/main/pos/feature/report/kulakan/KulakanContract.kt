package com.bm.main.pos.feature.report.kulakan

import android.content.Context
import android.content.Intent
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.models.FilterDialogDate
import com.bm.main.pos.models.report.ReportRestModel
import com.bm.main.pos.models.transaction.HistoryTransaction
import com.bm.main.pos.models.transaction.Transaction
import org.threeten.bp.LocalDate

interface KulakanContract {

    interface View : BaseViewImpl {
        fun reloadData()
        fun setList(list:List<Transaction>)
        fun showErrorMessage(code: Int, msg: String)
        fun openDetail(id:String)
        fun openFilterByStatusDialog(title: String, list: List<DialogModel>, selected: DialogModel?, type: Int)
        fun openFilter(data: FilterDialogDate?)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun onDestroy()
        fun loadTransaction()
        fun onSearch(id:String)
        fun getFilterSelected():DialogModel
        fun getFilters():ArrayList<DialogModel>
        fun onChangeStatus(selected: DialogModel?)
        fun getCode():Int
        fun setFilterDateSelected(data:FilterDialogDate?)
        fun getFilterDateSelected():FilterDialogDate?
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetHistoryAPI(context: Context, restModel: ReportRestModel, awal:String, akhir:String, status:String)
        fun callGetSearchAPI(context: Context, restModel: ReportRestModel,id:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetHistory(list:List<HistoryTransaction>?)
        fun onSuccessGetSearch(list:List<Transaction>?)
        fun onFailedAPI(code:Int,msg:String)
    }


}