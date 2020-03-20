package com.bm.main.pos.feature.manage.product.main

import android.content.Context
import android.content.Intent
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.models.category.Category
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.models.product.ProductRestModel

interface AddProductMainContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onClose(msg: String?, status: Int)
        fun changeFragmentPage(page:Int, barcode:String?)
        fun openScanFragment()
        fun openAddProductFragmentFromScan(barode:String)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun onDestroy()
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
    }

    interface InteractorOutput : BaseInteractorOutputImpl {

    }
}