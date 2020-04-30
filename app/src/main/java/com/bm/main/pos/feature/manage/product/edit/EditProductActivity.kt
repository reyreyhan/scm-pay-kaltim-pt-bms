package com.bm.main.pos.feature.manage.product.edit

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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
import com.bm.main.pos.ui.ext.alert
import com.bm.main.pos.ui.ext.successDialog
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.ImageCompression
import com.bm.main.pos.utils.ImageUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.activity_edit_product_new.*
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
        return R.layout.activity_edit_product_new
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
    }

    private fun renderView() {
        expandTambahKeterangan()
        btn_tambah.setOnClickListener {
            showLoadingDialog()
            val name = et_name_product.editableText.toString().trim()
            val buy = et_harga_beli.editableText.toString().trim()
            val sell = et_harga_jual.editableText.toString().trim()
            val stok = et_stok_barang.editableText.toString().trim()
            val desc = et_catatan_produk.editableText.toString().trim()
            val barcode = tv_barcode.text.toString().trim()
            getPresenter()?.onCheck(name, buy, sell, stok, "0", desc, barcode)
        }

        btn_hapus.setOnClickListener {
            val callback = object : DialogCallback {
                override fun onSuccess() {
                    getPresenter()?.deleteProduct()
                }

                override fun onFailed() {

                }
            }
            alert(this, "Apakah anda akan menghapus produk ini?", callback)
        }

        iv_tambah_foto.setOnClickListener {
            getPresenter()?.onCheckPhoto()
        }

        iv_foto.setOnClickListener {
            getPresenter()?.onCheckPhoto()
        }

        et_product_category.setOnClickListener {
            showLoadingDialog()
            getPresenter()?.onCheckCategory(false)
        }

        tv_tambah_keterangan.setOnClickListener {
            expandTambahKeterangan()
        }

        iv_arrow.setOnClickListener {
            expandTambahKeterangan()
        }

        et_harga_jual.addTextChangedListener(NumberTextWatcher(et_harga_jual))
        et_harga_beli.addTextChangedListener(NumberTextWatcher(et_harga_beli))
        et_stok_barang.addTextChangedListener(NumberTextWatcher(et_stok_barang))

        choosePhotoHelper = ChoosePhotoHelper.with(this)
            .asFilePath()
            .build(ChoosePhotoCallback { photo ->
                if (photo.isNullOrEmpty() || photo.isNullOrBlank()) {
                    getPresenter()?.setImagePhotoPath(null)
                    loadPhoto("")
                    iv_foto.visibility = View.VISIBLE
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
                                iv_tambah_foto.visibility = View.GONE
                            } else {
                                getPresenter()?.setImagePhotoPath(null)
                                loadPhoto("")
                                iv_tambah_foto.visibility = View.VISIBLE
                                showMessage(999, "Foto tidak ditemukan")
                            }
                        }
                    }
                    imageUtil.execute(photo)
                }
            })
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Detail Produk"

            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            setHomeAsUpIndicator(backArrow)
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
//        if (path.isEmpty() || path.isBlank()) {
//            iv_tambah_foto.visibility = View.VISIBLE
//        } else {
//            iv_tambah_foto.visibility = View.GONE
//        }
        if (path != "https://apifp.exploreindonesia.id/api2/images/small_") {
            Glide.with(this)
                .load(path)
                .transform(CenterCrop(), RoundedCorners(4))
                .into(iv_foto)
        } else {
            iv_tambah_foto.visibility = View.VISIBLE
            Glide.with(this)
                .load("https://apifp.exploreindonesia.id/api2/images/no_product.jpg")
                .transform(CenterCrop(), RoundedCorners(4))
                .into(iv_foto)
        }
        iv_foto.visibility = View.VISIBLE
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
        et_product_category.text = value
    }

    override fun setProductName(value: String) {
        et_name_product.setText(value)
    }

    override fun setStock(value: String) {
        et_stok_barang.setText(value)
    }

    override fun setMinStock(value: String) {
//        et_minstock.setText(value)
    }

    override fun setSellPrice(value: String) {
        et_harga_jual.setText(value)
    }

    override fun setBuyPrice(value: String) {
        et_harga_beli.setText(value)
    }

    override fun setDescription(value: String) {
        et_catatan_produk.setText(value)
    }

    override fun setBarcode(value: String) {
        tv_barcode.text = value
    }

    override fun expandTambahKeterangan() {
        if (container_keterangan.visibility == View.VISIBLE){
            iv_arrow.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_down_mini))
            tv_tambah_keterangan.visibility = View.VISIBLE
            container_keterangan.visibility = View.GONE
        }else{
            iv_arrow.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_up_mini))
            tv_tambah_keterangan.visibility = View.GONE
            container_keterangan.visibility = View.VISIBLE
        }
    }
}
