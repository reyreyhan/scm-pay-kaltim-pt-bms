package com.bm.main.pos.feature.manage.product.edit

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.bm.main.fpl.templates.choosephotohelper.ChoosePhotoHelper
import com.bm.main.fpl.templates.choosephotohelper.callback.ChoosePhotoCallback
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.callback.DialogCallback
import com.bm.main.pos.feature.dialog.BottomDialog
import com.bm.main.pos.feature.manage.category.add.AddCategoryActivity
import com.bm.main.pos.feature.scan.ScanCodeActivity
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.NumberTextWatcher
import com.bm.main.pos.ui.ext.successDialog
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.Helper
import com.bm.main.pos.utils.ImageCompression
import com.bm.main.pos.utils.ImageUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.activity_edit_product.*
import kotlinx.android.synthetic.main.activity_edit_product.btn_camera
import kotlinx.android.synthetic.main.activity_edit_product.btn_save
import kotlinx.android.synthetic.main.activity_edit_product.btn_scan
import kotlinx.android.synthetic.main.activity_edit_product.btn_upload
import kotlinx.android.synthetic.main.activity_edit_product.et_barcode
import kotlinx.android.synthetic.main.activity_edit_product.et_buy
import kotlinx.android.synthetic.main.activity_edit_product.et_category
import kotlinx.android.synthetic.main.activity_edit_product.et_desc
import kotlinx.android.synthetic.main.activity_edit_product.et_minstock
import kotlinx.android.synthetic.main.activity_edit_product.et_name
import kotlinx.android.synthetic.main.activity_edit_product.et_sell
import kotlinx.android.synthetic.main.activity_edit_product.et_stok
import kotlinx.android.synthetic.main.activity_edit_product.iv_camera
import kotlinx.android.synthetic.main.activity_edit_product.iv_photo
import java.io.File

class EditProductActivity : BaseActivity<EditProductPresenter, EditProductContract.View>(),
    EditProductContract.View,
    BottomDialog.Listener {
    private val tag = EditProductActivity::class.java.simpleName
    private val categoryDialog = BottomDialog.newInstance()

    private var moreShown = true
    private lateinit var choosePhotoHelper: ChoosePhotoHelper
    private val codeopenscan = 1001
    private val CODE_ADD_CATEGORY = 1002

    override fun createPresenter(): EditProductPresenter {
        return EditProductPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_edit_product
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
    }

    private fun renderView() {
        btn_save.setOnClickListener {
            showLoadingDialog()
            val name = et_name.text.toString().trim()
            val buy = et_buy.text.toString().trim()
            val sell = et_sell.text.toString().trim()
            val stok = et_stok.text.toString().trim()
            val minstok = et_minstock.text.toString().trim()
            val desc = et_desc.text.toString().trim()
            val barcode = et_barcode.text.toString().trim()
            getPresenter()?.onCheck(name, buy, sell, stok, minstok, desc, barcode)
        }

        btn_scan.setOnClickListener {
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
                    val imageUtil = @SuppressLint("StaticFieldLeak")
                    object : ImageCompression(this@EditProductActivity) {
                        override fun onPostExecute(imagePath: String) {
                            super.onPostExecute(imagePath)
                            Log.d(tag, "masuk sini " + imagePath)
                            val compressedImageFile = File(imagePath)
                            if (compressedImageFile.exists()) {
                                val compressedSize = ImageUtil.getSizeFile(imagePath)
                                Log.d(tag, "choosePhotoHelper compressed size " + compressedSize)
                                getPresenter()?.setImagePhotoPath(imagePath)
                                loadPhoto(imagePath)
                                iv_camera.visibility = View.GONE
                            } else {
                                getPresenter()?.setImagePhotoPath(null)
                                loadPhoto("")
                                iv_camera.visibility = View.VISIBLE
                                showMessage(999, "Foto tidak ditemukan")
                            }
                        }
                    }
                    imageUtil.execute(photo)
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
            toolbar_title.text = getString(R.string.menu_edit_product)
            title = ""

            setHomeAsUpIndicator(ResourcesCompat.getDrawable(resources, R.drawable.ic_back_pos, null))
        }
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

    override fun onClose(msg: String?, status: Int) {
        val callback = object : DialogCallback {
            override fun onSuccess() {
                setResult(status, intent)
                finish()
            }

            override fun onFailed() {

            }
        }

        if (msg.isNullOrEmpty() || msg.isNullOrBlank()) {
            setResult(status, intent)
            finish()
        } else {
            successDialog(this, msg, callback)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun openScanPage() {
        val intent = Intent(this, ScanCodeActivity::class.java)
        startActivityForResult(intent, codeopenscan)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        choosePhotoHelper.onActivityResult(requestCode, resultCode, data);
        if (requestCode == codeopenscan && resultCode == Activity.RESULT_OK) {
            val code = data?.getStringExtra(AppConstant.DATA)
            if (code.isNullOrBlank() || code.isNullOrEmpty()) {
                setBarcode("")
            } else {
                setBarcode(code)
                showLoadingDialog()
                getPresenter()?.searchByBarcode(code)
            }
        } else if (requestCode == CODE_ADD_CATEGORY && resultCode == RESULT_OK) {
            showLoadingDialog()
            getPresenter()?.onCheckCategory(true)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        choosePhotoHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun openImageChooser() {
        choosePhotoHelper.showChooser()
    }

    override fun loadPhoto(path: String) {
        if (path.isEmpty() || path.isBlank()) {
            iv_camera.visibility = View.VISIBLE
        } else {
            iv_camera.visibility = View.GONE
        }
        Log.d("loadphoto edit ", path)
        if (path != "https://api-pos.fastpay.co.id/api/images/small_") {
            Glide.with(this)
                .load(path)
                .transform(CenterCrop(), RoundedCorners(4))
                .into(iv_photo)
        } else {
            Glide.with(this)
                .load("https://api-pos.fastpay.co.id/api/images/no_product.jpg")
                .transform(CenterCrop(), RoundedCorners(4))
                .into(iv_photo)
        }
//        Glide.with(this).asBitmap().load(path).transform(CenterCrop(), RoundedCorners(4))
//            .encodeFormat(Bitmap.CompressFormat.PNG).encodeQuality(50).diskCacheStrategy(
//                DiskCacheStrategy.NONE
//            ).override(50, 50).into(object : BitmapImageViewTarget(iv_photo) {
//                override fun onResourceReady(resource: Bitmap, animation: Transition<in Bitmap>?) {
//                    // here it's similar to RequestListener, but with less information (e.g. no model available)
//                    super.onResourceReady(resource, animation)
//                    //viewHolder.avi.setVisibility(View.GONE)
//                    // here you can be sure it's already set
//                }
//
//                // +++++ OR +++++
//                override fun setResource(resource: Bitmap?) {
//                    // this.getView().setImageDrawable(resource); is about to be called
//                    super.setResource(resource)
//                    // viewHolder.avi.setVisibility(View.GONE)
//                    //  iv_photo.scaleType(ImageView.ScaleType.FIT_CENTER)
//                    // here you can be sure it's already set
//                }
//
//                override fun onLoadFailed(errorDrawable: Drawable?) {
//                    super.onLoadFailed(errorDrawable)
//                    //viewHolder.avi.setVisibility(View.GONE)
//                    //                Glide.with(context).load( R.mipmap.ic_launcher).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.imageViewProduk);
////                    viewHolder.imageViewProduk.setImageDrawable(
////                        ContextCompat.getDrawable(
////                            context,
////                            R.mipmap.ic_launcher
////                        )
////                    )
//                }
//
//
//            })

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

    override fun onItemClicked(data: DialogModel, type: Int) {
        getPresenter()?.setSelectedCategory(data)
    }

    override fun setCategoryName(value: String) {
        et_category.text = value
    }

    override fun setProductName(value: String) {
        et_name.setText(value)
    }

    override fun setStock(value: String) {
        et_stok.setText(value)
    }

    override fun setMinStock(value: String) {
        et_minstock.setText(value)
    }

    override fun setSellPrice(value: String) {
        et_sell.setText(value)
    }

    override fun setBuyPrice(value: String) {
        et_buy.setText(value)
    }

    override fun setDescription(value: String) {
        et_desc.setText(value)
    }

    override fun setBarcode(value: String) {
        et_barcode.setText(value)
    }


}
