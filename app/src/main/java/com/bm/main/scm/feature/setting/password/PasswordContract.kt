package com.bm.main.scm.feature.setting.password

import android.content.Context
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.user.UserRestModel

interface PasswordContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onClose(msg: String?,status:Int)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
        fun onCheck(lama:String,baru:String,konfirmasi:String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callUpdateAPI(context: Context, model:UserRestModel,lama:String,baru:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessAPI(msg: String?)
        fun onFailedAPI(code:Int,msg:String)
    }


}