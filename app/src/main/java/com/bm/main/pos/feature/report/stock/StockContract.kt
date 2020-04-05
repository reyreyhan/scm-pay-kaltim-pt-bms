package com.bm.main.pos.feature.report.stock

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.models.FilterDialogDate
import com.bm.main.pos.models.Message
import com.bm.main.pos.models.product.ProductRestModel
import com.bm.main.pos.models.report.ReportRestModel
import com.bm.main.pos.models.report.ReportStock
import com.prolificinteractive.materialcalendarview.CalendarDay


interface StockContract {

    interface View : BaseViewImpl {
        fun setData(list:List<ReportStock>)
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
        fun sort(data:DialogModel)
        fun sortByName()
        fun sortByStock()
        fun setFilterDateSelected(data:FilterDialogDate?)
        fun getFilterDateSelected():FilterDialogDate?
        fun generateSortList()
        fun getSortList():ArrayList<DialogModel>
        fun getSelectedSort():DialogModel?
        fun updateProduct(id:String, stok:String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetReportsAPI(context: Context, restModel:ReportRestModel,awal:String,akhir:String)
        fun callSearchAPI(context: Context, restModel:ReportRestModel,cari:String,awal:String,akhir:String)
        fun callSortByStockAPI(context: Context, restModel:ReportRestModel,awal:String,akhir:String)
        fun callSortByNameAPI(context: Context, restModel:ReportRestModel,awal:String,akhir:String)
        fun callUpdateStockAPI(context: Context, model: ProductRestModel, id:String, stok:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetReports(list:List<ReportStock>)
        fun onSuccessUpdateUpdateStock(msg: Message)
        fun onFailedAPI(code:Int,msg:String)
    }


}