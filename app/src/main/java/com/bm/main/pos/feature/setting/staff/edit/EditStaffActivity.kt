package com.bm.main.pos.feature.setting.staff.edit

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.bm.main.fpl.templates.choosephotohelper.ChoosePhotoHelper
import com.bm.main.fpl.templates.choosephotohelper.callback.ChoosePhotoCallback
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.callback.DialogCallback
import com.bm.main.pos.models.staff.Staff
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.ext.successDialog
import com.bm.main.pos.utils.*
import com.bumptech.glide.Glide
//import com.bm.main.pos.utils.glide.GlideApp
import kotlinx.android.synthetic.main.activity_edit_staff.*
import java.io.File


class EditStaffActivity : BaseActivity<EditStaffPresenter, EditStaffContract.View>(),
    EditStaffContract.View {

    private lateinit var choosePhotoHelper: ChoosePhotoHelper


    override fun createPresenter(): EditStaffPresenter {
        return EditStaffPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_edit_staff
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
    }

    private fun renderView(){

        choosePhotoHelper = ChoosePhotoHelper.with(this)
            .asFilePath()
            .build(ChoosePhotoCallback { photo ->
                if(photo.isNullOrEmpty() || photo.isNullOrBlank()){
                    getPresenter()?.setImagePhotoPath(null)
                    loadPhoto("")
                }
                else{
//                    val tmpBitmap = BitmapFactory.decodeFile(photo)
//                    val uri = Uri.fromFile(File(photo))
//                    val bitmap = ImageHelper.rotateImageIfRequired(tmpBitmap, uri)
//                    val uriImage = ImageUtil.getImageUri(this, bitmap)
//                    val path = FilePathUtil.getPath(this@EditCustomerActivity,uriImage)
                    val imageUtil = @SuppressLint("StaticFieldLeak")
                    object : ImageCompression(this@EditStaffActivity) {
                        override fun onPostExecute(imagePath: String) {
                            super.onPostExecute(imagePath)
                            val compressedImageFile = File(imagePath)
                            if(compressedImageFile.exists()){
                                val compressedSize = ImageUtil.getSizeFile(imagePath)
                                Log.d("choosePhotoHelper compressed size",""+compressedSize)
                                getPresenter()?.setImagePhotoPath(imagePath)
                                loadPhoto(imagePath)
                            }else{
                                getPresenter()?.setImagePhotoPath(null)
                                loadPhoto("")
                                showMessage(999,"Foto tidak ditemukan")
                            }
                        }
                    }
                    //imageUtil.execute(path!!)
                    imageUtil.execute(photo)


                }

            })

        btn_save.setOnClickListener {
            hideKeyboard()
            showLoadingDialog()
            val name = et_name.text.toString().trim()
            val email = et_email.text.toString().trim()
            val phone = et_phone.text.toString().trim()
            val address = et_address.text.toString().trim()
            getPresenter()?.onCheck(name,email,phone, address)
        }

        iv_photo.setOnClickListener {
            getPresenter()?.onCheckPhoto()
        }

        rg_job.setOnCheckedChangeListener { _, p1 ->
            when (p1) {
                R.id.rb_admin -> getPresenter()?.setLevel("admin")
                R.id.rb_kasir -> getPresenter()?.setLevel("kasir")

            }
        }
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Edit Staff"

            val backArrow = resources.getDrawable(R.drawable.ic_back_pos)
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
        if(code == RestException.CODE_USER_NOT_FOUND){
            restartLoginActivity()
        }
        else{
            msg?.let {
                toast(this,it)
            }

        }

    }

    override fun onClose(msg: String?,status: Int,data:Staff?) {
        val callback = object: DialogCallback {
            override fun onSuccess() {
                val newIntent = intent
                if(Activity.RESULT_OK == status){
                    newIntent.putExtra(AppConstant.DATA,data)
                }
                setResult(status,newIntent)
                finish()
            }

            override fun onFailed() {

            }
        }

        if(msg.isNullOrEmpty() || msg.isNullOrBlank()){
            val newIntent = intent
            if(Activity.RESULT_OK == status){
                newIntent.putExtra(AppConstant.DATA,data)
            }
            setResult(status,newIntent)
            finish()
        }
        else{
            successDialog(this,msg,callback)
        }

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }
    override fun setStaff(name: String?, email: String?, phone: String?, address: String?, level:String?, url:String?)
    {
        name?.let {
            et_name.setText(it)
        }

        email?.let {
            et_email.setText(it)
        }

        phone?.let {
            et_phone.setText(it)
        }

        address?.let {
            et_address.setText(it)
        }

        url?.let {
            loadPhoto(it)
        }

        if("admin" == level){
            rg_job.check(R.id.rb_admin)
        }
        else{
            rg_job.check(R.id.rb_kasir)
        }


    }

    override fun openImageChooser() {
        choosePhotoHelper.showChooser()
    }
    override fun loadPhoto(path: String) {
        Glide.with(this)
            .load(path)
            .error(R.drawable.ic_user_pos)
            .transform(CenterCrop(),CircleCrop())
            .into(iv_photo)

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
