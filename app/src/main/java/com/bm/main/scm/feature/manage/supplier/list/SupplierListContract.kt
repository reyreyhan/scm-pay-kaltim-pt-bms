package com.bm.main.scm.feature.manage.supplier.list

import android.content.Context
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.supplier.Supplier
import com.bm.main.scm.models.supplier.SupplierRestModel


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