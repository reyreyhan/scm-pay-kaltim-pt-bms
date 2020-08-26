package com.bm.main.scm.feature.report.labarugi.penjualan

import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.FilterDialogDate
import com.bm.main.scm.models.report.ReportLabaRugi
import com.bm.main.scm.models.report.ReportRestModel
import com.bm.main.scm.utils.AppConstant
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.LocalDate

class PenjualanPresenter(val context: Context, val view: PenjualanContract.View) : BasePresenter<PenjualanContract.View>(),
    PenjualanContract.Presenter,
    PenjualanContract.InteractorOutput {

    private var interactor = PenjualanInteractor(this)
    private var restModel = ReportRestModel(context)
    private var firstDate: CalendarDay?= null
    private var lastDate: CalendarDay?= null
    private var today: CalendarDay?= null
    private var selectedDate: FilterDialogDate?=null

    override fun onViewCreated(intent: Intent) {
        val now = org.threeten.bp.LocalDate.now()
        setDate(null, now!!)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onCheck(list: List<ReportLabaRugi.Penjualan>?) {
        if(list == null){
            view.showErrorMessage(null, 999,"Tidak ada data")
            return
        }
        if(list.isEmpty()){
            view.showErrorMessage(null, 999,"Tidak ada data")
        }
        else{
            view.setList(list)
        }
    }

    override fun setDate(date:CalendarDay?, now:LocalDate?){
        if (date!=null){
            today = date
        }else if (now!=null){
            today = CalendarDay.from(now)
        }
        val yesterday = today?.date!!.minusDays(1)
        firstDate =  CalendarDay.from(yesterday)
        lastDate = today
        selectedDate = FilterDialogDate()
        selectedDate?.id = AppConstant.FilterDate.DAILY
        selectedDate?.firstDate = firstDate
        selectedDate?.lastDate = lastDate
        view.setDate(firstDate?.date.toString(),lastDate?.date.toString())
    }

    override fun getToday(): CalendarDay? {
        return today
    }

    override fun setFilterDateSelected(data: FilterDialogDate?) {
        selectedDate = data
        loadData()
    }

    override fun getFilterDateSelected(): FilterDialogDate? {
        return selectedDate
    }

    override fun loadYesterdayData() {
        interactor.callGetReportsYesterdayAPI(context,restModel,selectedDate?.firstDate?.date.toString(),selectedDate?.firstDate?.date.toString())
    }

    override fun loadData() {
        interactor.callGetReportsAPI(context,restModel,selectedDate?.lastDate?.date.toString(),selectedDate?.lastDate?.date.toString())
    }

    override fun onSuccessGetReports(data: ReportLabaRugi) {
        view.setData(data)
    }

    override fun onSuccessGetReportsYesterday(data: ReportLabaRugi) {
        view.setYesterdayData(data)
    }

    override fun onFailedAPI(isToday:Boolean?, code: Int, msg: String) {
        view.showErrorMessage(isToday,code,msg)
        view.resetData(isToday)
    }
}