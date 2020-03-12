package com.bm.main.pos.feature.setting.staff.edit

import android.content.Context
import android.content.Intent
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.customer.CustomerRestModel
import com.bm.main.pos.models.staff.Staff
import com.bm.main.pos.models.staff.StaffRestModel

interface EditStaffContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onClose(msg:String?,status:Int,data: Staff?)
        fun setStaff(name: String?,email:String?,phone:String?,address:String?,level:String?,url:String?)
        fun openImageChooser()
        fun loadPhoto(path:String)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent:Intent)
        fun onDestroy()
        fun onCheck(name:String,email:String,phone:String,address:String)
        fun onCheckPhoto()
        fun setImagePhotoPath(path:String?)
        fun setLevel(value:String?)

    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callEditAPI(context: Context, model: StaffRestModel, id:String, name:String, email:String, phone:String, address:String, level:String, gbr:String?)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessEdit(msg: String?)
        fun onFailedAPI(code:Int,msg:String)
    }


}