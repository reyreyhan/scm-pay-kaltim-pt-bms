package com.bm.main.pos.feature.sell.add

import android.content.Context
import android.content.Intent
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.models.cart.CartRestModel
import com.bm.main.pos.models.category.Category
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.models.product.ProductRestModel

interface AddContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onSuccess(data: Product)
        fun openScanPage()
        fun openImageChooser()
        fun loadPhoto(path: String)
        fun openCategories(title: String, list: List<DialogModel>, selected: DialogModel?)
        fun setCategoryName(name: String)
        fun setData(product: Product)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun onDestroy()
        fun onCheckScan()
        fun onCheckPhoto()
        fun setImagePhotoPath(path: String?)
        fun setImagePhotoUrl(url: String)
        fun onCheck(name: String, buy: String, sell: String, stok: String, desc: String, barcode: String)
        fun onCheckCategory(forceUpdate: Boolean)
        fun setSelectedCategory(data: DialogModel)
        fun searchByBarcode(search: String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callAddAPI(context: Context, restModel: CartRestModel, name: String, kodebarang: String, buy: String, sell: String, stock: String, gbr: String, desc: String, photoUrl: String = "")
        fun callAddWithBarodeAPI(context: Context, restModel: CartRestModel, name: String, barcode: String, buy: String, sell: String, stock: String, gbr: String, desc: String)
        fun callSearchByBarcodeAPI(context: Context, restModel: ProductRestModel, search: String)
        fun callGetCategoriesAPI(context: Context, restModel: CategoryRestModel)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessAdd(data: List<Product>)
        fun onFailedAPI(code: Int, msg: String)
        fun onSuccessGetCategories(list: List<Category>)
        fun onSuccessByBarcode(list: List<Product>)
        fun onFailedByBarcode(code: Int, msg: String)
    }
}