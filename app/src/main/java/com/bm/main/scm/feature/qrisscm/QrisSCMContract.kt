package com.bm.main.scm.feature.qrisscm

import android.content.Context
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.models.product.ProductRestModel

interface QrisSCMContract {

    interface View : BaseViewImpl {
        fun setProducts(list:List<Product>)
        fun reloadData()
        fun showErrorMessage(code: Int, msg: String)
        fun showSuccessMessage(msg: String?)
    }


    interface Presenter : BasePresenterImpl<View> {
        fun onDestroy()
        fun onViewCreated()
        fun searchProduct(search: String)
        fun loadProducts()
    }

    interface Interactor : BaseInteractorImpl {
        fun callGetProductsAPI(context: Context, restModel: ProductRestModel)
        fun callSearchProductAPI(context: Context, restModel: ProductRestModel, search: String)
        fun onDestroy()
        fun onRestartDisposable()
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetProducts(list: List<Product>)
        fun onFailedAPI(code: Int, msg: String)
    }
}