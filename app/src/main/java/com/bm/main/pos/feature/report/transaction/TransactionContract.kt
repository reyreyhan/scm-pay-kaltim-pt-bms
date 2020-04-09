package com.bm.main.pos.feature.report.transaction

import android.content.Context
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.models.FilterDialogDate
import com.bm.main.pos.models.discount.Discount
import com.bm.main.pos.models.report.ReportRestModel
import com.bm.main.pos.models.report.ReportTransaksi
import com.bm.main.pos.models.supplier.Supplier
import com.bm.main.pos.models.supplier.SupplierRestModel
import org.threeten.bp.LocalDate


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