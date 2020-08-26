package com.bm.main.scm.feature.report.stock

import android.content.Context
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.DialogModel
import com.bm.main.scm.models.FilterDialogDate
import com.bm.main.scm.models.Message
import com.bm.main.scm.models.product.ProductRestModel
import com.bm.main.scm.models.report.ReportRestModel
import com.bm.main.scm.models.report.ReportStock
import com.bm.main.scm.utils.AppConstant
import com.prolificinteractive.materialcalendarview.CalendarDay

class StockPresenter(val context: Context, val view: StockContract.View) : BasePresenter<StockContract.View>(),
    StockContract.Presenter, StockContract.InteractorOutput {

    private var interactor = StockInteractor(this)
    private var restModel = ReportRestModel(context)
    private var restModelProduct = ProductRestModel(context)
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
        setDate(today, null)
        generateSortList()
        loadData()
    }

    override fun loadData() {
//        interactor.callGetReportsAPI(context,restModel,selectedDate?.firstDate?.date.toString(),selectedDate?.lastDate?.date.toString())
        sort(sortSelected!!)
    }

    override fun setDate(date: CalendarDay?, date2: CalendarDay?) {
        if (date2==null){
            today = date
            val yesterday = today?.date!!.minusDays(1)
            firstDate =  CalendarDay.from(yesterday)
            lastDate = today
            selectedDate = FilterDialogDate()
            selectedDate?.id = AppConstant.FilterDate.DAILY
            selectedDate?.firstDate = firstDate
            selectedDate?.lastDate = lastDate
            view.setDate(firstDate?.date.toString(), lastDate?.date.toString())
        }else{
            firstDate = date
            lastDate = date2
            selectedDate = FilterDialogDate()
            selectedDate?.id = AppConstant.FilterDate.DAILY
            selectedDate?.firstDate = date
            selectedDate?.lastDate = date2
            view.setDate(selectedDate?.firstDate?.date.toString(), selectedDate?.lastDate?.date.toString())
        }
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

    override fun onSuccessUpdateUpdateStock(msg: Message) {
        view.showSuccessMessage(msg.message)
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

    override fun updateProduct(id: String, stok: String) {
        interactor.callUpdateStockAPI(context, restModelProduct, id, stok)
    }
}