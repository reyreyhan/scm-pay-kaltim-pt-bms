package com.bm.main.pos.feature.scan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.models.product.ProductRestModel


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