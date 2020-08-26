package com.bm.main.scm.feature.report.kulakan

import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.DialogModel
import com.bm.main.scm.models.FilterDialogDate
import com.bm.main.scm.models.report.ReportRestModel
import com.bm.main.scm.models.transaction.HistoryTransaction
import com.bm.main.scm.models.transaction.Transaction

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