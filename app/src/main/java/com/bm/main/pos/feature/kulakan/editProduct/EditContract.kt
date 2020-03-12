package com.bm.main.pos.feature.kulakan.editProduct

import android.content.Context
import android.content.Intent
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.cart.CartRestModel
import com.bm.main.pos.models.product.Product

interface EditContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onClose()
        fun onSuccess(data:Product)
        fun setName(value:String)
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