package com.bm.main.pos.feature.sell.chooseProduct

import android.content.Context
import android.content.Intent
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.models.product.ProductRestModel


interface ChooseProductContract {

    interface View : BaseViewImpl {
        fun setProducts(list:List<Product>)
        fun showErrorMessage(code: Int, msg: String)
        fun showSuccessMessage(msg: String?)
        fun reloadData()
        fun onSelected(data:Product)
        fun checkStockProducts(isCheck:Boolean)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun onDestroy()
        fun loadProducts()
        fun searchProduct(search:String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetProductsAPI(context: Context, restModel:ProductRestModel)
        fun callSearchProductAPI(context: Context, restModel:ProductRestModel, search:String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetProducts(list:List<Product>)
        fun onFailedAPI(code:Int,msg:String)
    }


}