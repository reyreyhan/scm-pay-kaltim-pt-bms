package com.bm.main.scm.feature.scan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.models.product.ProductRestModel


interface ScanCodeContract {
    interface View : BaseViewImpl {
        fun renderView()
        fun resumeCamera()
        fun showMessage(code: Int, msg: String?)
        fun hideShowSearchHeader(visibility:Int)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewFragmentCreated(bundle: Bundle)
        fun onViewCreated(intent: Intent)
        fun searchByBarcode(search: String)
        fun onDestroy()
    }

    interface Interactor : BaseInteractorImpl {
        fun destroy()
        fun onRestartDisposable()
        fun callSearchByBarcodeAPI(context: Context, restModel: ProductRestModel, search: String)
    }

    interface InteractorOutput :BaseInteractorOutputImpl {
        fun onSuccessByBarcode(list: List<Product>)
        fun onFailedByBarcode(code: Int, msg: String)
    }
}