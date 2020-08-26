package com.bm.main.scm.feature.report.labarugi.main

import android.content.Context
import android.content.Intent
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.FilterDialogDate
import com.bm.main.scm.models.report.ReportLabaRugi
import com.bm.main.scm.models.report.ReportRestModel
import com.bm.main.scm.utils.AppConstant

class MainPresenter(val context: Context, val view: MainContract.View) : BasePresenter<MainContract.View>(),
    MainContract.Presenter,
    MainContract.InteractorOutput {

    private var interactor = MainInteractor(this)
    private var restModel = ReportRestModel(context)
    private var firstDate: CalendarDay?= null
    private var lastDate: CalendarDay?= null
    private var today: CalendarDay?= null
    private var selectedDate:FilterDialogDate?=null

    override fun onViewCreated(intent: Intent) {
        val now = org.threeten.bp.LocalDate.now()
        today = CalendarDay.from(now)
        val yesterday = today?.date!!.minusDays(1)
        firstDate =  CalendarDay.from(yesterday)
        lastDate = today
        selectedDate = FilterDialogDate()
        selectedDate?.id = AppConstant.FilterDate.DAILY
        selectedDate?.firstDate = firstDate
        selectedDate?.lastDate = lastDate
        view.setDate(firstDate?.date.toString(),lastDate?.date.toString())
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun loadData() {
        interactor.callGetReportsAPI(context,restModel,selectedDate?.firstDate?.date.toString(),selectedDate?.lastDate?.date.toString())
    }

    override fun onSuccessGetReports(data: ReportLabaRugi) {
        view.setData(data)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code,msg)
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
}