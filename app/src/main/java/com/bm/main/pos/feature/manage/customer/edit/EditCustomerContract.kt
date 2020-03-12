package com.bm.main.pos.feature.manage.customer.edit

import android.content.Context
import android.content.Intent
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.customer.CustomerRestModel

interface EditCustomerContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onClose(msg: String?,status:Int,data:Customer?)
        fun setCustomer(name: String?,email:String?,phone:String?,address:String?,url:String?)
        fun openImageChooser()
        fun loadPhoto(path:String)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent:Intent)
        fun onDestroy()
        fun onCheck(name:String,email:String,phone:String,address:String)
        fun onCheckPhoto()
        fun setImagePhotoPath(path:String?)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callEditCustomerAPI(context: Context, model: CustomerRestModel, id:String, name:String, email:String, phone:String, address:String, gbr:String?)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessEditCustomer(msg: String?)
        fun onFailedEditCustomer(code:Int,msg:String)
    }


}