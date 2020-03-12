package com.bm.main.pos.feature.setting.staff.add

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.customer.CustomerRestModel
import com.bm.main.pos.models.staff.StaffRestModel

interface AddStaffContract {

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
        fun setLevel(value:String?)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callAddAPI(context: Context,model:StaffRestModel,name:String,email:String,phone:String,address:String,posisi:String,gbr:String?)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessAdd(msg: String?)
        fun onFailedAPI(code:Int,msg:String)
    }


}