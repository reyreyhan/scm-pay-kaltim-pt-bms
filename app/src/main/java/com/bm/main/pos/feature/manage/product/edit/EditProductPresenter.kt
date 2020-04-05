package com.bm.main.pos.feature.manage.product.edit

//import com.bm.main.pos.models.supplier.SupplierRestModel
//import com.bm.main.pos.utils.Helper
//import com.google.gson.reflect.TypeToken
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.bm.main.pos.R
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.callback.PermissionCallback
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.models.category.Category
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.models.product.ProductRestModel
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.PermissionUtil
import com.google.gson.Gson


class EditProductPresenter(val context: Context, val view: EditProductContract.View) : BasePresenter<EditProductContract.View>(),
    EditProductContract.Presenter,
    EditProductContract.InteractorOutput {

    private var interactor = EditProductInteractor(this)
    private var restModel = ProductRestModel(context)
    private var categoryRestModel = CategoryRestModel(context)
    private var categories: ArrayList<DialogModel> = ArrayList()
    private var category: DialogModel? = null
    private var permissionUtil: PermissionUtil = PermissionUtil(context)
    private lateinit var cameraPermission: PermissionCallback
    private lateinit var photoPermission: PermissionCallback
    private var photoPath: String? = null
    private var data: Product? = null


    override fun onViewCreated(intent: Intent) {
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

        data = intent.getSerializableExtra(AppConstant.DATA) as Product
        if (data == null) {
            view.onClose(null, Activity.RESULT_CANCELED)
            return
        }

        checkProduct()
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

    override fun onCheck(name: String, buy: String, sell: String, stok: String, minstok: String, desc: String, barcode: String) {
        if (name.isBlank() || name.isEmpty()) {
            view.showMessage(999, context.getString(R.string.err_empty_product_name))
            return
        }

        if (category == null) {
            view.showMessage(999, context.getString(R.string.err_empty_product_category))
            return
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

        interactor.callEditProductAPI(context, restModel, data?.id_barang!!, name, barcode, category?.id!!, sell, buy, stok, minstok, photoPath, desc)
    }

    override fun onSuccessEditProduct(msg: String?) {
        view.onClose(msg, Activity.RESULT_OK)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showMessage(code, msg)
    }

    override fun onCheckCategory(forceUpdate: Boolean) {
        if (categories.isEmpty() || forceUpdate) {
            interactor.callGetCategoriesAPI(context, categoryRestModel)
        } else {
            view.openCategories("Pilih Kategori", categories!!, category)
        }
    }

    override fun onSuccessGetCategories(list: List<Category>) {
        if (list.isEmpty()) {
            view.showMessage(999, "Belum ada kategori")
            return
        }
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
            val product = list[0]
            this.data = product
            checkProduct()
        }


    }

    override fun onFailedByBarcode(code: Int, msg: String) {
        view.hideLoadingDialog()
    }

    private fun checkProduct() {
        data?.let {
            val cat = DialogModel()
            if (!it.id_kategori.isNullOrBlank() && !it.id_kategori.isNullOrEmpty() && it.id_kategori != "0" &&
                    !it.nama_kategori.isNullOrBlank() && !it.nama_kategori.isNullOrEmpty()
            ) {
                cat.id = it.id_kategori
                cat.value = it.nama_kategori
            }
            setSelectedCategory(cat)

            if (it.gbr != "https://apifp.exploreindonesia.id/api2/images/small_") {
                view.loadPhoto(it.gbr)
            }

            view.setProductName(it.nama_barang)
            view.setBuyPrice(it.hargabeli)
            view.setSellPrice(it.hargajual)
            view.setStock(it.stok)
            view.setMinStock(it.minimalstok)
            view.setDescription(it.deskripsi)
            view.setBarcode(it.kodebarang)
        }
    }


}