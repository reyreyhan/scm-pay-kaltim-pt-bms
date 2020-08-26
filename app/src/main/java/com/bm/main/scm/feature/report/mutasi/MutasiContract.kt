package com.bm.main.scm.feature.report.mutasi

import android.content.Context
import android.content.Intent
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.FilterDialogDate
import com.bm.main.scm.models.report.ReportMutasi
import com.bm.main.scm.models.report.ReportRestModel

interface MutasiContract {

    interface View : BaseViewImpl {
        fun setData(data:ReportMutasi)
        fun setDate(firstDate:String,lastDate:String)
        fun showErrorMessage(code: Int, msg: String)
        fun reloadData()
        fun openFilter(data: FilterDialogDate?)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onDestroy()
        fun onViewCreated(intent: Intent)
        fun loadData()
        fun getToday(): CalendarDay?
        fun setFilterDateSelected(data: FilterDialogDate?)
        fun getFilterDateSelected(): FilterDialogDate?
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetReportsAPI(context: Context, restModel: ReportRestModel, awal:String, akhir:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetReports(data:ReportMutasi)
        fun onFailedAPI(code:Int,msg:String)
    }


}