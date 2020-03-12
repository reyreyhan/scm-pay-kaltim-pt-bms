package com.bm.main.pos.feature.manage.customer.add

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.customer.CustomerRestModel

interface AddCustomerContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onClose(msg: String?,status:Int)
        fun openImageChooser()
        fun loadPhoto(path:String)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
        fun onCheck(name:String,email:String,phone:String,address:String)
        fun onCheckPhoto()
        fun setImagePhotoPath(path:String?)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callAddCustomerAPI(context: Context,model:CustomerRestModel,name:String,email:String,phone:String,address:String,gbr:String?)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessAddCustomer(msg: String?)
        fun onFailedAddCustomer(code:Int,msg:String)
    }


}