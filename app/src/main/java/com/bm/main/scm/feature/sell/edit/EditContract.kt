package com.bm.main.scm.feature.sell.edit

import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.cart.CartRestModel
import com.bm.main.scm.models.product.Product

interface EditContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onClose()
        fun onSuccess(data:Product)
        fun setName(value:String)
        fun loadPhoto(path:String?)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun onDestroy()
        fun onCheck(buy:String,sell:String,stok:String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callEditAPI(context: Context,restModel:CartRestModel,id:String,name:String,barcode:String,buy:String,sell:String,stock:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessEdit(data:List<Product>)
        fun onFailedAPI(code:Int,msg:String)
    }


}