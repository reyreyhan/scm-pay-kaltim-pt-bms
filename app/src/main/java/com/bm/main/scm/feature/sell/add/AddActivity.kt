package com.bm.main.scm.feature.sell.add

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.bm.main.fpl.templates.choosephotohelper.ChoosePhotoHelper
import com.bm.main.fpl.templates.choosephotohelper.callback.ChoosePhotoCallback
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.di.userComponent
import com.bm.main.scm.feature.dialog.BottomDialog
import com.bm.main.scm.feature.manage.category.add.AddCategoryActivity
import com.bm.main.scm.feature.manage.product.ProductViewModel
import com.bm.main.scm.feature.scan.ScanCodeActivity
import com.bm.main.scm.feature.sell.chooseProduct.ChooseProductActivity
import com.bm.main.scm.models.DialogModel
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.ui.NumberTextWatcher
import com.bm.main.scm.ui.ext.htmlText
import com.bm.main.scm.ui.ext.toast
import com.bm.main.scm.utils.AppConstant
import com.bm.main.scm.utils.Helper
import com.bm.main.scm.utils.ImageHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.CustomTarget
import kotlinx.android.synthetic.main.activity_add_manual_sell.*
import timber.log.Timber

class AddActivity : BaseActivity<AddPresenter, AddContract.View>(),
    AddContract.View,
    BottomDialog.Listener {

    private val categoryDialog = BottomDialog.newInstance()
    private val productViewModel by lazy { ViewModelProvider(this, userComponent!!.productComponentFactory()).get(ProductViewModel::class.java) }
    private lateinit var choosePhotoHelper: ChoosePhotoHelper
    private val CODE_OPEN_SCAN = 1001
    private val CODE_OPEN_CHOOSE_PRODUCT = 1023
    private val CODE_ADD_CATEGORY = 1002
    private val CODE_OPEN_SEARCH = 3278
    private var moreShown = true

    override fun createPresenter(): AddPresenter {
        return AddPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_add_manual_sell
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
    }

    private fun renderView() {
        action_find_label.htmlText("Cari <br><b>Barang</b>")
        action_scan_label.htmlText("Scan <br><b>Barcode</b>")

        btn_save.setOnClickListener {
            showLoadingDialog()
            val name = et_name.text.toString().trim()
            val buy = et_buy.text.toString().trim()
            val sell = et_sell.text.toString().trim()
            val stok = et_stok.text.toString().trim()
            val desc = et_desc.text.toString().trim()
            val barcode = et_barcode.text.toString().trim()
            getPresenter()?.onCheck(name, buy, sell, stok, desc, barcode)
        }

        et_category.setOnClickListener {
            showLoadingDialog()
            getPresenter()?.onCheckCategory(false)
        }

        action_find.setOnClickListener {
            openChooseProduct()
//            startActivityForResult(Intent(this, ProductSearchActivity::class.java), CODE_OPEN_SEARCH)
        }

        btn_scan.setOnClickListener {
            getPresenter()?.onCheckScan()
        }

        action_scan.setOnClickListener {
            getPresenter()?.onCheckScan()
        }

        btn_camera.setOnClickListener {
            getPresenter()?.onCheckPhoto()
        }

        btn_upload.setOnClickListener {
            getPresenter()?.onCheckPhoto()
        }

        et_category.setOnClickListener {
            showLoadingDialog()
            getPresenter()?.onCheckCategory(false)
        }

        et_sell.addTextChangedListener(NumberTextWatcher(et_sell))
        et_buy.addTextChangedListener(NumberTextWatcher(et_buy))
        et_stok.addTextChangedListener(NumberTextWatcher(et_stok))

        choosePhotoHelper = ChoosePhotoHelper.with(this)
            .asFilePath()
            .build(ChoosePhotoCallback { photo ->
                if (photo.isNullOrEmpty() || photo.isNullOrBlank()) {
                    getPresenter()?.setImagePhotoPath(null)
                    loadPhoto("")
                    iv_camera.visibility = View.VISIBLE
                } else {
                    getPresenter()?.setImagePhotoPath(photo)
                    loadPhoto(photo)
                    iv_camera.visibility = View.GONE
//                    val imageUtil = @SuppressLint("StaticFieldLeak")
//                    object : ImageCompression(this@AddActivity) {
//                        override fun onPostExecute(imagePath: String) {
//                            super.onPostExecute(imagePath)
//                            val compressedImageFile = File(imagePath)
//                            if (compressedImageFile.exists()) {
//                                getPresenter()?.setImagePhotoPath(imagePath)
//                                loadPhoto(imagePath)
//                                iv_camera.visibility = View.GONE
//                            } else {
//                                getPresenter()?.setImagePhotoPath(null)
//                                loadPhoto("")
//                                iv_camera.visibility = View.VISIBLE
//                                showMessage(999, "Foto tidak ditemukan")
//                            }
//                        }
//                    }
//                    imageUtil.execute(photo)
                }
            })

        switch_btn.setOnClickListener {
            if (moreShown) {
                Helper.collapse(ll_more)
                switch_btn.setImageResource(R.drawable.ic_down_arrow)
            } else {
                Helper.expand(ll_more)
                switch_btn.setImageResource(R.drawable.ic_up_arrow)
            }

            moreShown = !moreShown
        }

        switch_btn.performClick()
        enableInput(true)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbarx)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            toolbar_title.text = intent.getStringExtra("title") ?: getString(R.string.menu_sell_manual)
            title = ""

            setHomeAsUpIndicator(ResourcesCompat.getDrawable(resources, R.drawable.ic_back_pos, null))
        }
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun showMessage(code: Int, msg: String?) {
        hideLoadingDialog()
        if (code == RestException.CODE_USER_NOT_FOUND) {
            restartLoginActivity()
        } else {
            msg?.let {
                toast(this, it)
            }
        }
    }

    override fun onSuccess(data: Product) {
        val newIntent = intent
        newIntent.putExtra(AppConstant.DATA, data)
        setResult(Activity.RESULT_OK, newIntent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun openCategories(title: String, list: List<DialogModel>, selected: DialogModel?) {
        hideLoadingDialog()
        if (categoryDialog.dialog != null && categoryDialog.dialog!!.isShowing) {

        } else {
            categoryDialog.setData(title, 1, list, selected)
            categoryDialog.setAction(getString(R.string.btn_add_category)) {
                categoryDialog.dismissAllowingStateLoss()
                startActivityForResult(Intent(this, AddCategoryActivity::class.java), CODE_ADD_CATEGORY)
            }
            categoryDialog.show(supportFragmentManager, "category")
        }
    }

    override fun setCategoryName(name: String) {
        et_category.text = name
    }

    fun openChooseProduct() {
        val intent = Intent(this, ChooseProductActivity::class.java)
        startActivityForResult(intent, CODE_OPEN_CHOOSE_PRODUCT)
    }

    override fun openScanPage() {
        val intent = Intent(this, ScanCodeActivity::class.java)
        startActivityForResult(intent, CODE_OPEN_SCAN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.d(requestCode.toString())
        choosePhotoHelper.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_OPEN_SCAN && resultCode == Activity.RESULT_OK) {
            val code = data?.getStringExtra(AppConstant.DATA)
            if (!code.isNullOrBlank() && !code.isNullOrEmpty()) {
                showLoadingDialog()
                et_barcode.setText(code)
                getPresenter()?.searchByBarcode(code)
            }
        } else if (requestCode == CODE_ADD_CATEGORY && resultCode == RESULT_OK) {
            showLoadingDialog()
            getPresenter()?.onCheckCategory(true)
        } else if (requestCode == CODE_OPEN_SEARCH && resultCode == RESULT_OK && data != null) {
            // set value from item
            try {
                productViewModel.moshi.adapter(Product::class.java).fromJson(data.getStringExtra("item"))?.let { setData(it) }
            } catch (e: Exception) {
                Timber.e(e)
            }
        } else if (requestCode == CODE_OPEN_CHOOSE_PRODUCT && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                toast(this, "Data tidak ditemukan")
                return
            }
            if (data.getSerializableExtra(AppConstant.DATA) == null) {
                toast(this, "Data tidak ditemukan")
                return
            }
            val product = data.getSerializableExtra(AppConstant.DATA) as Product
            if (product.id_barang == null) {
                toast(this, "Data tidak ditemukan")
            } else {
                onSuccess(product)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun setData(product: Product) {
        et_name.setText(product.nama_barang)
        et_desc.setText(product.deskripsi)
        et_barcode.setText(product.kodebarang)
        et_buy.setText(product.hargabeli)
        et_sell.setText(product.hargajual)
        et_stok.setText(product.stok)
        product.gbr?.let {
            if (it.isNotBlank()) {
                Glide.with(iv_photo).asBitmap().load(it).into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?) {
                        iv_photo.setImageBitmap(resource)
                        ImageHelper.bitmapToCacheFile(this@AddActivity, resource, "tmp_item.${product.nama_barang?.replace("\\s", "") ?: System.currentTimeMillis()}.jpg") {
                            getPresenter()?.setImagePhotoPath(it)
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
                iv_camera.visibility = View.GONE
            }
        }
        enableInput(true)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        choosePhotoHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun openImageChooser() {
        choosePhotoHelper.showChooser()
    }

    override fun loadPhoto(path: String) {
        Timber.d("loadphoto $path")
        if (path != "https://api-pos.fastpay.co.id/api/images/small_") {
            Glide.with(this)
                .load(path)
                .transform(CenterCrop(), RoundedCorners(4))
                .into(iv_photo)
        } else {
            Glide.with(this)
                .load(path)
                .transform(CenterCrop(), RoundedCorners(4))
                .error(Glide.with(this).load("https://api-pos.fastpay.co.id/api/images/no_product.jpg"))
                .into(iv_photo)
        }
    }

    override fun onItemClicked(data: DialogModel, type: Int) {
        getPresenter()?.setSelectedCategory(data)
    }

    private fun enableInput(enable: Boolean = true) {
        et_name.isEnabled = enable
        et_category.isEnabled = enable
        et_buy.isEnabled = enable
        et_sell.isEnabled = enable
        et_stok.isEnabled = enable
        et_desc.isEnabled = enable
        btn_upload.isEnabled = enable
        et_barcode.isEnabled = enable

        if (enable) {
            ll_photo.setBackgroundResource(R.drawable.border_dash_white)
            ll_barcode_input.setBackgroundResource(R.drawable.border_dash_white)
            iv_photo.setBackgroundResource(R.drawable.rounded_white_4dp_stroke_secondary)
        } else {
            ll_photo.setBackgroundResource(R.drawable.border_dash_gray)
            ll_barcode_input.setBackgroundResource(R.drawable.border_dash_gray)
            iv_photo.setBackgroundResource(R.drawable.rounded_gray_4dp_stroke_gray)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }
}