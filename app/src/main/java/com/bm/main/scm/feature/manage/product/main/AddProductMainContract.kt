package com.bm.main.scm.feature.manage.product.main

import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.models.product.ProductRestModel

interface AddProductMainContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onClose(msg: String?, status: Int)
        fun changeFragmentPage(page:Int, barcode:String?)
        fun openScanFragment()
        fun openAddProductFragmentFromScan(barode:String)
        fun onResult(data:Product)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun searchByBarcode(search: String)
        fun onDestroy()
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun callSearchByBarcodeAPI(context: Context, restModel: ProductRestModel, search: String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessByBarcode(list: List<Product>)
    }
}