package com.bm.main.scm.feature.setting.account

import android.content.Context
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.user.User
import com.bm.main.scm.models.user.UserRestModel

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