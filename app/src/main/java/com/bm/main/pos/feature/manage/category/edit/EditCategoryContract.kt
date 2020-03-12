package com.bm.main.pos.feature.manage.category.edit

import android.content.Context
import android.content.Intent
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.category.CategoryRestModel

interface EditCategoryContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onClose(msg: String?,status:Int)
        fun setCategoryName(name: String?)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent:Intent)
        fun onDestroy()
        fun onCheck(name:String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callEditCategoryAPI(context: Context, model:CategoryRestModel, id:String, name:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessEditCategory(msg: String?)
        fun onFailedEditCategory(code:Int,msg:String)
    }


}