package com.bm.main.pos.feature.manage.product.add

import android.content.Context
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.models.category.Category
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.models.product.ProductRestModel
import com.bm.main.pos.models.supplier.Supplier
import com.bm.main.pos.models.supplier.SupplierRestModel

interface AddProductContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onClose(msg: String?, status: Int)
        fun openScanPage()
        fun openImageChooser()
        fun loadPhoto(path: String)
        fun openCategories(title: String, list: List<DialogModel>, selected: DialogModel?)
        fun setCategoryName(name: String)
        fun openEditPage(product: Product)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
        fun onCheckScan()
        fun onCheckPhoto()
        fun setImagePhotoPath(path: String?)
        fun setImagePhotoUrl(url: String)
        fun onCheckCategory(forceUpdate: Boolean)
        fun onCheck(
            name: String,
            buy: String,
            sell: String,
            stok: String,
            minstok: String,
            desc: String,
            barcode: String
        )

        fun setSelectedCategory(data: DialogModel)
        fun searchByBarcode(search: String)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetCategoriesAPI(context: Context, restModel: CategoryRestModel)
        fun callSearchByBarcodeAPI(context: Context, restModel: ProductRestModel, search: String)
        fun callAddProductAPI(
            context: Context,
            model: ProductRestModel,
            name: String,
            kode: String,
            idkategori: String,
            jual: String,
            beli: String,
            stok: String,
            minstok: String,
            gbr: String?,
            desk: String,
            photoUrl: String
        )
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessAddProduct(msg: String?)
        fun onSuccessGetCategories(list: List<Category>)
        fun onFailedAPI(code: Int, msg: String)
        fun onSuccessByBarcode(list: List<Product>)
        fun onFailedByBarcode(code: Int, msg: String)
    }
}