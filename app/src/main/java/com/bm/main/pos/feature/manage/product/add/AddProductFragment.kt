package com.bm.main.pos.feature.manage.product.add

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bm.main.fpl.activities.BaseActivity
import com.bm.main.fpl.templates.choosephotohelper.ChoosePhotoHelper
import com.bm.main.fpl.templates.choosephotohelper.callback.ChoosePhotoCallback
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseFragment
import com.bm.main.pos.feature.dialog.BottomDialog
import com.bm.main.pos.feature.manage.category.add.AddCategoryActivity
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.NumberTextWatcher
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.utils.ImageCompression
import com.bm.main.pos.utils.ImageUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.fragment_add_product_new.view.*
import org.jetbrains.anko.image
import timber.log.Timber
import java.io.File

class AddProductFragment : BaseFragment<AddProductPresenter, AddProductContract.View>(),
    AddProductContract.View, BottomDialog.Listener{

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle) =
            AddProductFragment().apply {
                arguments = bundle
            }
    }
    private val CODE_ADD_CATEGORY = 1001
    private val categoryDialog = BottomDialog.newInstance()

    private lateinit var _view: View

    private lateinit var choosePhotoHelper: ChoosePhotoHelper

    override fun createPresenter(): AddProductPresenter {
        return AddProductPresenter(activity as Context, this)
    }

    override fun onCreateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return  inflater.inflate(R.layout.fragment_add_product_new, container, false)
    }

    override fun initAction(view: View) {
        _view = view
        renderView()
        arguments?.let { getPresenter()?.onViewCreated(it)}
    }

    private fun renderView() {
        expandTambahKeterangan()
        _view.btn_tambah.setOnClickListener {
            showLoadingDialog()
            val name = _view.et_name_product.editableText.toString().trim()
            val buy = _view.et_harga_beli.editableText.toString().trim()
            val sell = _view.et_harga_jual.editableText.toString().trim()
            val stok = _view.et_stok_barang.editableText.toString().trim()
            val desc = _view.et_catatan_produk.editableText.toString().trim()
            val barcode = _view.tv_barcode.text.toString().trim()
            getPresenter()?.onCheck(name, buy, sell, stok, "0", desc, barcode)
        }

        _view.iv_tambah_foto.setOnClickListener {
            getPresenter()?.onCheckPhoto()
        }

        _view.et_product_category.setOnClickListener {
            showLoadingDialog()
            getPresenter()?.onCheckCategory(false)
        }

        _view.tv_tambah_keterangan.setOnClickListener {
            expandTambahKeterangan()
        }

        _view.iv_arrow.setOnClickListener {
            expandTambahKeterangan()
        }

        _view.et_harga_jual.addTextChangedListener(NumberTextWatcher(_view.et_harga_jual))
        _view.et_harga_beli.addTextChangedListener(NumberTextWatcher(_view.et_harga_beli))
        _view.et_stok_barang.addTextChangedListener(NumberTextWatcher(_view.et_stok_barang))


        choosePhotoHelper = ChoosePhotoHelper.with(this)
            .asFilePath()
            .build(ChoosePhotoCallback { photo ->
                if (photo.isNullOrEmpty() || photo.isNullOrBlank()) {
                    getPresenter()?.setImagePhotoPath(null)
                    loadPhoto("")
                    _view.iv_foto.visibility = View.VISIBLE
                } else {
                    val imageUtil = @SuppressLint("StaticFieldLeak")
                    object : ImageCompression(requireContext()) {
                        override fun onPostExecute(imagePath: String) {
                            super.onPostExecute(imagePath)
                            Log.d("addProduct ", imagePath)
                            val compressedImageFile = File(imagePath)
                            if (compressedImageFile.exists()) {
                                val compressedSize = ImageUtil.getSizeFile(imagePath)
                                Log.d(
                                    "choosePhotoHelper compressed size",
                                    imagePath + " " + compressedSize
                                )
                                getPresenter()?.setImagePhotoPath(imagePath)
                                loadPhoto(imagePath)
                                _view.iv_tambah_foto.visibility = View.GONE
                            } else {
                                getPresenter()?.setImagePhotoPath(null)
                                loadPhoto("")
                                _view.iv_tambah_foto.visibility = View.VISIBLE
                                showMessage(999, "Foto tidak ditemukan")
                            }
                        }
                    }
                    imageUtil.execute(photo)
                }
            })
    }

    override fun showMessage(code: Int, msg: String?) {
        hideLoadingDialog()
        if (code == RestException.CODE_USER_NOT_FOUND) {
            restartLoginActivity()
        } else {
            msg?.let {
                toast(it)
            }
        }
    }

    override fun onClose(msg: String?, status: Int) {

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

    override fun hideBarcode() {
        _view.tv_barcode.visibility = View.GONE
    }

    override fun expandTambahKeterangan() {
        if (_view.container_keterangan.visibility == View.VISIBLE){
            _view.iv_arrow.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_down_mini))
            _view.tv_tambah_keterangan.visibility = View.VISIBLE
            _view.container_keterangan.visibility = View.GONE
        }else{
            _view.iv_arrow.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_up_mini))
            _view.tv_tambah_keterangan.visibility = View.GONE
            _view.container_keterangan.visibility = View.VISIBLE
        }
    }

    override fun loadPhoto(path: String) {
        Timber.d("loadphoto $path")
        if (path != "https://api-pos.fastpay.co.id/api/images/small_") {
            Glide.with(this)
                .load(path)
                .transform(CenterCrop(), RoundedCorners(4))
                .into(_view.iv_foto)
        } else {
            Glide.with(this)
                .load("https://api-pos.fastpay.co.id/api/images/no_product.jpg")
                .transform(CenterCrop(), RoundedCorners(4))
                .into(_view.iv_foto)
        }
    }

    override fun openCategories(title: String, list: List<DialogModel>, selected: DialogModel?) {
        hideLoadingDialog()
        if (categoryDialog.dialog != null && categoryDialog.dialog!!.isShowing) {
        } else {
            categoryDialog.setData(title, 1, list, selected)
            categoryDialog.setAction(getString(R.string.btn_add_category)) {
                categoryDialog.dismissAllowingStateLoss()
                startActivityForResult(
                    Intent(requireContext(), AddCategoryActivity::class.java),
                    CODE_ADD_CATEGORY
                )
            }
            categoryDialog.show(fragmentManager!!, "category")
        }
    }

    override fun setCategoryName(name: String) {
        _view.et_product_category.text = name
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.d(requestCode.toString())
        choosePhotoHelper.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == CODE_OPEN_SCAN && resultCode == Activity.RESULT_OK) {
//            val code = data?.getStringExtra(AppConstant.DATA)
//            if (code.isNullOrBlank() || code.isNullOrEmpty()) {
//                et_barcode.setText("")
//            } else {
//                et_barcode.setText(code)
//                showLoadingDialog()
//                getPresenter()?.searchByBarcode(code)
//            }
//        }
        if (requestCode == CODE_ADD_CATEGORY && resultCode == BaseActivity.RESULT_OK) {
            showLoadingDialog()
            getPresenter()?.onCheckCategory(true)
        }
    }

    override fun setBarcodeText(code: String) {
        _view.tv_barcode.visibility = View.VISIBLE
        _view.tv_barcode.text = code
    }

    override fun onItemClicked(data: DialogModel, type: Int) {
        getPresenter()?.setSelectedCategory(data)
    }
}