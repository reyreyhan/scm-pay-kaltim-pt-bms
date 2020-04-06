package com.bm.main.pos.feature.manage.customer.edit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.bm.main.fpl.templates.choosephotohelper.ChoosePhotoHelper
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.callback.DialogCallback
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.ext.successDialog
import com.bm.main.pos.ui.ext.toast
import kotlinx.android.synthetic.main.activity_edit_customer_new.*

class EditCustomerActivity : BaseActivity<EditCustomerPresenter, EditCustomerContract.View>(),
    EditCustomerContract.View {

    private lateinit var choosePhotoHelper: ChoosePhotoHelper


    override fun createPresenter(): EditCustomerPresenter {
        return EditCustomerPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_edit_customer_new
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
    }

    private fun renderView() {

//        choosePhotoHelper = ChoosePhotoHelper.with(this)
//            .asFilePath()
//            .build(ChoosePhotoCallback { photo ->
//                if (photo.isNullOrEmpty() || photo.isNullOrBlank()) {
//                    getPresenter()?.setImagePhotoPath(null)
//                    loadPhoto("")
//                } else {
//                    val imageUtil = @SuppressLint("StaticFieldLeak")
//                    object : ImageCompression(this@EditCustomerActivity) {
//                        override fun onPostExecute(imagePath: String) {
//                            super.onPostExecute(imagePath)
//                            val compressedImageFile = File(imagePath)
//                            if (compressedImageFile.exists()) {
//                                val compressedSize = ImageUtil.getSizeFile(imagePath)
//                                Log.d("choosePhotoHelper compressed size", "" + compressedSize)
//                                getPresenter()?.setImagePhotoPath(imagePath)
//                                loadPhoto(imagePath)
//                            } else {
//                                getPresenter()?.setImagePhotoPath(null)
//                                loadPhoto("")
//                                showMessage(999, "Foto tidak ditemukan")
//                            }
//                        }
//                    }
//                    imageUtil.execute(photo)
//                }
//            })

        btn_simpan.setOnClickListener {
            hideKeyboard()
            showLoadingDialog()
            val name = et_name.text.toString().trim()
            val email = et_email.text.toString().trim()
            val phone = et_no_hp.text.toString().trim()
//            val address = et_address.text.toString().trim()
            getPresenter()?.onCheck(name, email, phone)
        }
        btn_delete.setOnClickListener {
            getPresenter()?.deleteCustomer()
        }
//        iv_photo.setOnClickListener {
//            getPresenter()?.onCheckPhoto()
//        }
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.menu_edit_customer)

            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            setHomeAsUpIndicator(backArrow)
        }

    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
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

    override fun onCloseDelete(msg: String?, status:Int){
        val callback = object : DialogCallback {
            override fun onSuccess() {
                if (Activity.RESULT_OK == status) {
                    setResult(9999)
                    finish()
                }
            }

            override fun onFailed() {

            }
        }
        if (msg.isNullOrEmpty() || msg.isNullOrBlank()) {
            if (Activity.RESULT_OK == status) {
                setResult(9999)
                finish()
            }
        } else {
            successDialog(this, msg, callback)
        }
    }

    override fun onClose(msg: String?, status: Int, data: Customer?) {
        val callback = object : DialogCallback {
            override fun onSuccess() {
                if (Activity.RESULT_OK == status) {
                    setResult(status)
                    finish()
                }
            }
            override fun onFailed() {

            }
        }
        if (msg.isNullOrEmpty() || msg.isNullOrBlank()) {
            if (Activity.RESULT_OK == status) {
                setResult(status)
                finish()
            }
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

    override fun setCustomer(name: String?, email: String?, phone: String?, address: String?, url: String?) {
        name?.let {
            et_name.setText(it)
        }

        email?.let {
            et_email.setText(it)
        }

        phone?.let {
            et_no_hp.setText(it)
        }

//        address?.let {
//            et_address.setText(it)
//        }
//
//        url?.let {
//            loadPhoto(it)
//        }


    }

    override fun openImageChooser() {
        choosePhotoHelper.showChooser()
    }

    override fun loadPhoto(path: String) {
//        Glide.with(this)
//            .load(path)
//            .error(R.drawable.ic_user_pos)
//            .transform(CenterCrop(), CircleCrop())
//            .into(iv_photo)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        choosePhotoHelper.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        choosePhotoHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
