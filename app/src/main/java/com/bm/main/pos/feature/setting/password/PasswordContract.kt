package com.bm.main.pos.feature.setting.password

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.user.UserRestModel

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