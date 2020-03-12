package com.bm.main.pos.feature.manage.product.add

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import com.bm.main.pos.R
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.supplier.SupplierRestModel
import com.bm.main.pos.utils.Helper
import com.google.gson.reflect.TypeToken
import android.util.Log
import com.bm.main.fpl.activities.BaseActivity
import com.bm.main.fpl.constants.EventParam
import com.bm.main.pos.callback.PermissionCallback
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.models.category.Category
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.models.product.ProductRestModel
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.ImageUtil
import com.bm.main.pos.utils.PermissionUtil
import com.google.android.gms.measurement.module.Analytics
import com.google.firebase.analytics.FirebaseAnalytics
import timber.log.Timber
import java.io.File
import kotlin.math.log

class AddProductPresenter(val context: Context, val view: AddProductContract.View) :
    BasePresenter<AddProductContract.View>(),
    AddProductContract.Presenter,
    AddProductContract.InteractorOutput {

    private var interactor = AddProductInteractor(this)
    private var restModel = ProductRestModel(context)
    private var categoryRestModel = CategoryRestModel(context)
    private var categories: ArrayList<DialogModel> = ArrayList()
    private var category: DialogModel? = null
    private var permissionUtil: PermissionUtil = PermissionUtil(context)
    private lateinit var cameraPermission: PermissionCallback
    private lateinit var photoPermission: PermissionCallback
    private var photoPath: String? = null
    private var photoUrl: String = ""

    override fun onViewCreated() {
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

    override fun onCheck(
        name: String,
        buy: String,
        sell: String,
        stok: String,
        minstok: String,
        desc: String,
        barcode: String
    ) {
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
        (context as BaseActivity).logEventFireBase(
            "Tambah Product",
            name,
            EventParam.EVENT_ACTION_ADD_PRODUCT,
            EventParam.EVENT_ACTION_ADD_PRODUCT,
            AddProductActivity::class.java.getSimpleName()
        )
        Timber.e("add product, $name -- $barcode -- $sell -- $buy -- $stok -- $photoPath -- $desc -- $photoUrl")
        interactor.callAddProductAPI(
            context,
            restModel,
            name,
            barcode,
            category?.id!!,
            sell,
            buy,
            stok,
            minstok,
            photoPath,
            desc,
            photoUrl
        )
    }

    override fun onSuccessAddProduct(msg: String?) {
        view.onClose(msg, Activity.RESULT_OK)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showMessage(code, msg)
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
        Log.d("categories", Gson().toJson(categories))
        view.openCategories("Pilih Kategori", categories!!, category)
    }

    override fun setSelectedCategory(data: DialogModel) {
        category = data
        view.setCategoryName(data.value!!)
    }

    override fun searchByBarcode(search: String) {
        interactor.callSearchByBarcodeAPI(context, restModel, search)
    }

    override fun onSuccessByBarcode(list: List<Product>) {
        view.hideLoadingDialog()
        if (list.isNotEmpty()) {
            val data =
                list.firstOrNull { it.nama_barang.isNotEmpty() && it.gbr.isNotEmpty() && it.hargajual.isNotEmpty() && it.hargabeli.isNotEmpty() }
                    ?: list.first()
//            Log.d("addProductPresenter ",list[0].toString())
//            Log.d("addProductPresenter ",data.)

            view.openEditPage(data)
        }
    }

    override fun onFailedByBarcode(code: Int, msg: String) {
        view.hideLoadingDialog()
    }
}