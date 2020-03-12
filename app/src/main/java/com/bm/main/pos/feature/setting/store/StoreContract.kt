package com.bm.main.pos.feature.setting.store

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.customer.CustomerRestModel
import com.bm.main.pos.models.store.Store
import com.bm.main.pos.models.store.StoreRestModel
import com.bm.main.pos.models.user.User
import com.bm.main.pos.models.user.UserRestModel

interface StoreContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onClose(msg: String?,status:Int)
        fun setInfo(store: Store)
        fun reloadData()

    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
        fun onCheck(name:String,email:String,phone:String,address:String)
        fun loadStore()
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetStoreAPI(context: Context,model:StoreRestModel)
        fun callUpdateAPI(context: Context,model:StoreRestModel,name:String,email:String,phone:String,address:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetStore(list:List<Store>?)
        fun onSuccessUpdate(msg: String?)
        fun onFailedAPI(code:Int,msg:String)
    }


}