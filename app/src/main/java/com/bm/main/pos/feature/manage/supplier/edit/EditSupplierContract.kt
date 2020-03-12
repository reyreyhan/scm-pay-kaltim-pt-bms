package com.bm.main.pos.feature.manage.supplier.edit

import android.content.Context
import android.content.Intent
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.supplier.Supplier
import com.bm.main.pos.models.supplier.SupplierRestModel

interface EditSupplierContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onClose(msg: String?,status:Int)
        fun setSupplier(name:String?,email:String?,phone:String?,address:String?,province:String?,city:String?)
        fun openPlace(title:String, type:Int, list: List<String>, selected: String)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
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
        fun callEditSupplierAPI(context: Context, model: SupplierRestModel, id:String, name:String, email:String, phone:String, address:String, province:String, city:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessEditSupplier(msg: String?)
        fun onFailedEditSupplier(code:Int,msg:String)
    }


}