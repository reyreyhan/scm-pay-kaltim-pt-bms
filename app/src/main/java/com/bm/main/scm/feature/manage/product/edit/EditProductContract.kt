package com.bm.main.scm.feature.manage.product.edit

import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.DialogModel
import com.bm.main.scm.models.category.Category
import com.bm.main.scm.models.category.CategoryRestModel
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.models.product.ProductRestModel

interface EditProductContract {

    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onClose(msg: String?, status: Int)
        fun openScanPage()
        fun openImageChooser()
        fun loadPhoto(path: String)
        fun openCategories(title: String, list: List<DialogModel>, selected: DialogModel?)
        fun setProductName(value: String)
        fun setCategoryName(value: String)
        fun setStock(value: String)
        fun setMinStock(value: String)
        fun setSellPrice(value: String)
        fun setBuyPrice(value: String)
        fun setDescription(value: String)
        fun setBarcode(value: String)
        fun expandTambahKeterangan()
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun onDestroy()
        fun onCheckScan()
        fun onCheckPhoto()
        fun setImagePhotoPath(path: String?)
        fun onCheckCategory(forceUpdate: Boolean)
        fun onCheck(name: String, buy: String, sell: String, stok: String, minstok: String, desc: String, barcode: String)
        fun setSelectedCategory(data: DialogModel)
        fun searchByBarcode(search: String)
        fun deleteProduct()
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callDeleteProductAPI(context: Context, restModel:ProductRestModel, id:String)
        fun callGetCategoriesAPI(context: Context, restModel: CategoryRestModel)
        fun callSearchByBarcodeAPI(context: Context, restModel: ProductRestModel, search: String)
        fun callEditProductAPI(context: Context, model: ProductRestModel, id: String, name: String, kode: String, idkategori: String, jual: String, beli: String, stok: String, minstok: String, gbr: String?, desk: String)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessEditProduct(msg: String?)
        fun onSuccessDeleteProduct(msg: String?)
        fun onSuccessGetCategories(list: List<Category>)
        fun onFailedAPI(code: Int, msg: String)
        fun onSuccessByBarcode(list: List<Product>)
        fun onFailedByBarcode(code: Int, msg: String)
    }


}