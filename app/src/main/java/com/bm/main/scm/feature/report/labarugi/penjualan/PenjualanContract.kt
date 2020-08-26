package com.bm.main.scm.feature.report.labarugi.penjualan

import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.FilterDialogDate
import com.bm.main.scm.models.report.ReportLabaRugi
import com.bm.main.scm.models.report.ReportRestModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.LocalDate

interface PenjualanContract {

    interface View : BaseViewImpl {
        fun setData(list:List<ReportLabaRugi.Penjualan>?)
        fun setYesterdayData(data:ReportLabaRugi)
        fun setReportUIData()
        fun setList(list:List<ReportLabaRugi.Penjualan>)
        fun showErrorMessage(isToday:Boolean?, code: Int, msg: String)
        fun resetData(isToday:Boolean?)
        fun setData(data:ReportLabaRugi)
        fun setDate(firstDate:String,lastDate:String)
        fun reloadData()
        fun openFilter(data: FilterDialogDate?)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun setDate(date:CalendarDay?, now: LocalDate?)
        fun onDestroy()
        fun loadYesterdayData()
        fun loadData()
        fun getToday(): CalendarDay?
        fun setFilterDateSelected(data:FilterDialogDate?)
        fun getFilterDateSelected():FilterDialogDate?
        fun onCheck(list: List<ReportLabaRugi.Penjualan>?)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetReportsAPI(context: Context, restModel: ReportRestModel, awal:String, akhir:String)
        fun callGetReportsYesterdayAPI(context: Context, restModel: ReportRestModel, awal:String, akhir:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetReports(data:ReportLabaRugi)
        fun onSuccessGetReportsYesterday(data:ReportLabaRugi)
        fun onFailedAPI(isToday:Boolean?,code:Int,msg:String)
    }


}