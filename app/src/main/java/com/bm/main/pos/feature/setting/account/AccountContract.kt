package com.bm.main.pos.feature.setting.account

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.customer.CustomerRestModel
import com.bm.main.pos.models.user.User
import com.bm.main.pos.models.user.UserRestModel

interface AccountContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onClose(msg: String?,status:Int)
        fun openImageChooser()
        fun loadPhoto(path:String)
        fun setInfo(user: User)
        fun openPasswordPage()
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
        fun loadProfile(context: Context):User?
        fun callUpdateAPI(context: Context,model:UserRestModel,name:String,email:String,phone:String,address:String,gbr:String?)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessUpdate(msg: String?)
        fun onFailedAPI(code:Int,msg:String)
    }


}