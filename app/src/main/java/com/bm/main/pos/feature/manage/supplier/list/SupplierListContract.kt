package com.bm.main.pos.feature.manage.supplier.list

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.discount.Discount
import com.bm.main.pos.models.supplier.Supplier
import com.bm.main.pos.models.supplier.SupplierRestModel


interface SupplierListContract {

    interface View : BaseViewImpl {
        fun setData(list:List<Supplier>)
        fun showErrorMessage(code: Int, msg: String)
        fun showSuccessMessage(msg: String?)
        fun reloadData()
        fun openAddPage()
        fun openEditPage(data:Supplier)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
        fun loadSuppliers()
        fun deleteSupplier(id:String)
        fun searchSupplier(search:String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetSuppliersAPI(context: Context, restModel:SupplierRestModel)
        fun callDeleteSupplierAPI(context: Context, restModel:SupplierRestModel, id:String)
        fun callSearchSupplierAPI(context: Context, restModel:SupplierRestModel, search:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetSuppliers(list:List<Supplier>)
        fun onSuccessDeleteSupplier(msg: String?)
        fun onFailedAPI(code:Int,msg:String)
    }


}