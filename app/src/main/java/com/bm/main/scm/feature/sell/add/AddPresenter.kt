package com.bm.main.scm.feature.sell.add

import android.content.Context
import android.content.Intent
import com.bm.main.fpl.activities.BaseActivity
import com.bm.main.fpl.constants.EventParam
import com.bm.main.scm.R
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.callback.PermissionCallback
import com.bm.main.scm.models.DialogModel
import com.bm.main.scm.models.cart.CartRestModel
import com.bm.main.scm.models.category.Category
import com.bm.main.scm.models.category.CategoryRestModel
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.models.product.ProductRestModel
import com.bm.main.scm.utils.AppConstant
import com.bm.main.scm.utils.PermissionUtil
import com.google.firebase.analytics.FirebaseAnalytics

class AddPresenter(val context: Context, val view: AddContract.View) : BasePresenter<AddContract.View>(),
    AddContract.Presenter,
    AddContract.InteractorOutput {

    private var interactor = AddInteractor(this)
    private var cartRestModel = CartRestModel(context)
    private var restModel = ProductRestModel(context)
    private var barcode: String? = null
    private var categoryRestModel = CategoryRestModel(context)
    private var categories: ArrayList<DialogModel> = ArrayList()
    private var category: DialogModel? = null
    private var permissionUtil: PermissionUtil = PermissionUtil(context)
    private lateinit var cameraPermission: PermissionCallback
    private lateinit var photoPermission: PermissionCallback
    private var photoPath: String? = null
    private var photoUrl: String = ""

    override fun onViewCreated(intent: Intent) {
        barcode = intent.getStringExtra(AppConstant.DATA)

        cameraPermission = object : PermissionCallback {
            override fun onSuccess() {
                view.openScanPage()
            }

            override fun onFailed() {
                view.showMessage(999, context.getString(R.string.reason_permission_camera))
            }
        }

        photoPermission = object : PermissionCallback {
            override fun onSuccess() {
                view.openImageChooser()
            }

            override fun onFailed() {
                view.showMessage(999, context.getString(R.string.reason_permission_camera))
            }
        }
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onCheckScan() {
        permissionUtil.checkCameraPermission(cameraPermission)
    }

    override fun onCheckPhoto() {
        permissionUtil.checkCameraPermission(photoPermission)
    }

    override fun setImagePhotoPath(path: String?) {
        photoPath = path
    }

    override fun setImagePhotoUrl(url: String) {
        photoUrl = url
    }

    override fun onCheck(name: String, buy: String, sell: String, stok: String, desc: String, barcode: String) {
        if (name.isBlank() || name.isEmpty()) {
            view.showMessage(999, context.getString(R.string.err_empty_product_name))
            return
        }

        if (category == null) {
            category = DialogModel()
        }

        if (buy.isBlank() || buy.isEmpty() || "0" == buy) {
            view.showMessage(999, context.getString(R.string.err_empty_buy))
            return
        }

        if (sell.isBlank() || sell.isEmpty() || "0" == sell) {
            view.showMessage(999, context.getString(R.string.err_empty_sell))
            return
        }

        if (stok.isBlank() || stok.isEmpty() || "0" == stok) {
            view.showMessage(999, context.getString(R.string.err_empty_stock))
            return
        }

        view.showLoadingDialog()
        if (!barcode.isNullOrBlank()) {
            (context as BaseActivity).logEventFireBase(
                "Tambah Product",
                name,
                EventParam.EVENT_ACTION_ADD_PRODUCT,
                FirebaseAnalytics.Event.ADD_TO_CART,
                EventParam.EVENT_ACTION_ADD_PRODUCT,
                AddActivity::class.java.getSimpleName()
            )
            interactor.callAddWithBarodeAPI(context, cartRestModel, name, barcode!!, buy, sell, stok, photoPath.orEmpty(), desc)
        } else {
            interactor.callAddAPI(context, cartRestModel, name, barcode.orEmpty(), buy, sell, stok, photoPath.orEmpty(), desc)
        }
    }

    override fun onSuccessAdd(data: List<Product>) {
        view.hideLoadingDialog()
        if (data.isEmpty()) {
            onFailedAPI(999, "Terjadi kesalahan")
            return
        }
        view.onSuccess(data[0])
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.hideLoadingDialog()
        view.showMessage(code, msg)
    }

    override fun setSelectedCategory(data: DialogModel) {
        category = data
        view.setCategoryName(data.value!!)
    }

    override fun searchByBarcode(search: String) {
        interactor.callSearchByBarcodeAPI(context, restModel, search)
    }

    override fun onCheckCategory(forceUpdate: Boolean) {
        if (categories.isEmpty() || forceUpdate) {
            interactor.callGetCategoriesAPI(context, categoryRestModel)
        } else {
            view.openCategories("Pilih Kategori", categories, category)
        }
    }

    override fun onSuccessGetCategories(list: List<Category>) {
//        if (list.isEmpty()) {
//            view.showMessage(999, "Belum ada kategori")
//            return
//        }
        categories = ArrayList()
        for (cat in list) {
            val model = DialogModel()
            model.id = cat.id_kategori
            model.value = cat.nama_kategori
            categories.add(model)
        }
        view.openCategories("Pilih Kategori", categories, category)
    }

    override fun onSuccessByBarcode(list: List<Product>) {
        view.hideLoadingDialog()
        if (list.isNotEmpty()) {
            val data = list[0]
//            Log.d("addProductPresenter ",list[0].toString())
//            Log.d("addProductPresenter ",data.)

            view.setData(data)
        }
    }

    override fun onFailedByBarcode(code: Int, msg: String) {
        view.hideLoadingDialog()
    }
}