package com.bm.main.pos.feature.manage.supplier.add

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.supplier.Supplier
import com.bm.main.pos.models.supplier.SupplierRestModel

interface AddSupplierContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onClose(msg: String?,status:Int)
        fun openPlace(title:String, type:Int, list: List<String>, selected: String)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
        fun onCheck(name:String,email:String,phone:String,address:String,province:String,city:String)
        fun onCheckProvince()
        fun onCheckCity()
        fun setProvince(value:String)
        fun setCity(value:String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callAddSupplierAPI(context: Context, model: SupplierRestModel, name:String, email:String, phone:String, address:String, province:String, city:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessAddSupplier(msg: String?)
        fun onFailedAddSupplier(code:Int,msg:String)
    }


}