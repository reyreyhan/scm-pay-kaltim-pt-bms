package com.bm.main.scm.feature.setting.store

import android.content.Context
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.store.Store
import com.bm.main.scm.models.store.StoreRestModel

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