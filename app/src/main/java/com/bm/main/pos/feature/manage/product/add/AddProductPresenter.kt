package com.bm.main.pos.feature.manage.product.add

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.bm.main.fpl.activities.BaseActivity
import com.bm.main.fpl.constants.EventParam
import com.bm.main.pos.R
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.callback.PermissionCallback
import com.bm.main.pos.feature.manage.product.main.AddProductMainActivity
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.models.category.Category
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.product.ProductRestModel
import com.bm.main.pos.utils.PermissionUtil
import com.google.gson.Gson
import timber.log.Timber

class AddProductPresenter(val context: Context, val view: AddProductContract.View) :
    BasePresenter<AddProductContract.View>(),
    AddProductContract.Presenter,
    AddProductContract.InteractorOutput {

    private var interactor =
        AddProductInteractor(this)
    private var restModel = ProductRestModel(context)
    private var categoryRestModel = CategoryRestModel(context)
    private var categories: ArrayList<DialogModel> = ArrayList()
    private var category: DialogModel? = null
    private var permissionUtil: PermissionUtil = PermissionUtil(context)
    private lateinit var photoPermission: PermissionCallback
    private var photoPath: String? = null
    private var photoUrl: String = ""

    override fun onViewCreated(bundle:Bundle) {
        photoPermission = object : PermissionCallback {
            override fun onSuccess() {
                view.openImageChooser()
            }

            override fun onFailed() {
                view.showMessage(999, context.getString(R.string.reason_permission_camera))
            }
        }

        if (bundle.getBoolean("FromScan")){
            bundle.getString("Barcode")?.let { view.setBarcodeText(it) }
        }else{
            view.hideBarcode()
        }
    }

    override fun onDestroy() {
        interactor.onDestroy()
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
            AddProductMainActivity::class.java.simpleName
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
}