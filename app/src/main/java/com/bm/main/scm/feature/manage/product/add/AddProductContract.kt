package com.bm.main.scm.feature.manage.product.add

import android.content.Context
import android.os.Bundle
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.DialogModel
import com.bm.main.scm.models.category.Category
import com.bm.main.scm.models.category.CategoryRestModel
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.models.product.ProductRestModel

interface AddProductContract {
    interface View : BaseViewImpl {
        fun showMessage(code: Int, msg: String?)
        fun onClose(msg: String?, status: Int, barcode: String?)
        fun setBarcodeText(code:String)
        fun setProduct(data:Product)
        fun openImageChooser()
        fun hideBarcode()
        fun expandTambahKeterangan()
        fun loadPhoto(path: String)
        fun openCategories(title: String, list: List<DialogModel>, selected: DialogModel?)
        fun setCategoryName(name: String)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(bundle:Bundle)
        fun onDestroy()
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
        fun onSuccessAddProduct(msg: String?, barcode: String?)
        fun onSuccessGetCategories(list: List<Category>)
        fun onSuccessByBarcode(list: List<Product>)
        fun onFailedAPI(code: Int, msg: String)
    }
}