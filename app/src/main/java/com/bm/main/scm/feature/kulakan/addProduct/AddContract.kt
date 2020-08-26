package com.bm.main.scm.feature.kulakan.addProduct

import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.cart.CartRestModel
import com.bm.main.scm.models.product.Product

interface AddContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onSuccess(data:Product)

    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun onDestroy()
        fun onCheck(name:String,buy:String,sell:String,stok:String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callAddAPI(context: Context,restModel:CartRestModel,name:String,buy:String,sell:String,stock:String)
        fun callAddWithBarodeAPI(context: Context,restModel:CartRestModel,name:String,barcode:String,buy:String,sell:String,stock:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessAdd(data:List<Product>)
        fun onFailedAPI(code:Int,msg:String)
    }


}