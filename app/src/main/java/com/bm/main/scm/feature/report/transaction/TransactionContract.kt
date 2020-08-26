package com.bm.main.scm.feature.report.transaction

import android.content.Context
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.DialogModel
import com.bm.main.scm.models.FilterDialogDate
import com.bm.main.scm.models.report.ReportRestModel
import com.bm.main.scm.models.report.ReportTransaksi


interface TransactionContract {

    interface View : BaseViewImpl {
        fun setData(list:List<ReportTransaksi>)
        fun showErrorMessage(code: Int, msg: String)
        fun showSuccessMessage(msg: String?)
        fun reloadData()
        fun openFilter(data: FilterDialogDate?)
        fun openSortDialog(title: String, list: List<DialogModel>, selected: DialogModel?, type: Int)
        fun setDate(firstDate:String,lastDate:String)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
        fun loadData()
        fun setDate(date:CalendarDay?, date2:CalendarDay?)
        fun search(search:String)
        fun sort(data: DialogModel)
        fun sortByLast()
        fun sortByName()
        fun sortByPrice()
        fun generateSortList()
        fun setFilterDateSelected(data: FilterDialogDate?)
        fun getFilterDateSelected(): FilterDialogDate?
        fun getSortList():ArrayList<DialogModel>
        fun getSelectedSort():DialogModel?
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetReportsAPI(context: Context, restModel:ReportRestModel,awal:String,akhir:String)
        fun callSearchAPI(context: Context, restModel:ReportRestModel, search:String)
        //fun callSortAPI(context: Context, restModel:ReportRestModel,awal:String,akhir:String)
        fun callSortByNameAPI(context: Context, restModel:ReportRestModel,awal:String,akhir:String)
        fun callSortByPriceAPI(context: Context, restModel:ReportRestModel,awal:String,akhir:String)


    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetReports(list:List<ReportTransaksi>)
        fun onFailedAPI(code:Int,msg:String)
    }


}