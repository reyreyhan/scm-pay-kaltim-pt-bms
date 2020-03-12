package com.bm.main.pos.feature.manage.category.add

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.category.CategoryRestModel

interface AddCategoryContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onClose(msg: String?,status:Int)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
        fun onCheck(name:String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callAddCategoryAPI(context: Context, model:CategoryRestModel,name:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessAddCategory(msg: String?)
        fun onFailedAddCategory(code:Int,msg:String)
    }


}