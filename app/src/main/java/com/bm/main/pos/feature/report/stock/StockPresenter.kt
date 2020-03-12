package com.bm.main.pos.feature.report.stock

import android.content.Context
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.models.FilterDialogDate
import com.bm.main.pos.models.report.ReportRestModel
import com.bm.main.pos.models.report.ReportStock
import com.bm.main.pos.utils.AppConstant

class StockPresenter(val context: Context, val view: StockContract.View) : BasePresenter<StockContract.View>(),
    StockContract.Presenter, StockContract.InteractorOutput {

    private var interactor = StockInteractor(this)
    private var restModel = ReportRestModel(context)
    private var firstDate: CalendarDay?= null
    private var lastDate: CalendarDay?= null
    private var today: CalendarDay?= null
    private var selectedDate:FilterDialogDate?=null


    //private var isSort = false
    private var sorts : ArrayList<DialogModel> ?= null
    private var sortSelected:DialogModel?=null
    override fun onViewCreated() {
        val now = org.threeten.bp.LocalDate.now()
        today = CalendarDay.from(now)
        val yesterday = today?.date!!.minusDays(1)
        firstDate =  CalendarDay.from(yesterday)
        lastDate = today
        selectedDate = FilterDialogDate()
        selectedDate?.id = AppConstant.FilterDate.DAILY
        selectedDate?.firstDate = firstDate
        selectedDate?.lastDate = lastDate
        generateSortList()
        loadData()
    }

    override fun loadData() {
//        interactor.callGetReportsAPI(context,restModel,selectedDate?.firstDate?.date.toString(),selectedDate?.lastDate?.date.toString())
        sort(sortSelected!!)
    }

    override fun search(search: String) {
        interactor.onRestartDisposable()
        if(search.isEmpty()){
//            interactor.callGetReportsAPI(context,restModel,firstDate?.date.toString(),lastDate?.date.toString())
            loadData()
        }
        else{
            interactor.callSearchAPI(context,restModel,search,firstDate?.date.toString(),lastDate?.date.toString())
        }

    }

    override fun sort(data:DialogModel) {
        interactor.onRestartDisposable()
        sortSelected = data
        if(sortSelected?.id == "1"){
            sortByStock()
        }
        else {
            sortByName()
        }

    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessGetReports(list: List<ReportStock>) {
        view.setData(list)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code,msg)
    }

    override fun setFilterDateSelected(data: FilterDialogDate?) {
        selectedDate = data
        loadData()
    }

    override fun getFilterDateSelected(): FilterDialogDate? {
        return selectedDate
    }

    override fun sortByName() {
        interactor.callSortByNameAPI(context,restModel,firstDate?.date.toString(),lastDate?.date.toString())
    }

    override fun sortByStock() {
        interactor.callSortByStockAPI(context,restModel,firstDate?.date.toString(),lastDate?.date.toString())
    }

    override fun generateSortList() {
        sorts = ArrayList()
        val sortName = DialogModel()
        sortName.id = "1"
        sortName.value = "Stok Barang"

        val sortPrice = DialogModel()
        sortPrice.id = "2"
        sortPrice.value = "Nama Barang"

        sortSelected = sortName

        sorts?.add(sortName)
        sorts?.add(sortPrice)
    }

    override fun getSortList(): ArrayList<DialogModel> {
        return sorts!!
    }

    override fun getSelectedSort(): DialogModel? {
        return sortSelected
    }



}