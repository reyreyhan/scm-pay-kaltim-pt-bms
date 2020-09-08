package com.bm.main.scm.feature.setting.account

//import com.bm.main.pos.utils.glide.GlideApp
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.bm.main.fpl.templates.choosephotohelper.ChoosePhotoHelper
import com.bm.main.fpl.templates.choosephotohelper.callback.ChoosePhotoCallback
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.callback.DialogCallback
import com.bm.main.scm.feature.setting.password.PasswordActivity
import com.bm.main.scm.models.user.User
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.ui.ext.successDialog
import com.bm.main.scm.ui.ext.toast
import com.bm.main.scm.utils.ImageCompression
import com.bm.main.scm.utils.ImageUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.activity_account.*
import java.io.File


class AccountActivity : BaseActivity<AccountPresenter, AccountContract.View>(), AccountContract.View {

    private lateinit var choosePhotoHelper: ChoosePhotoHelper


    override fun createPresenter(): AccountPresenter {
        return AccountPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_account
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
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
//                    val path = FilePathUtil.getPath(this@AddCustomerActivity,uriImage)
                    val imageUtil = @SuppressLint("StaticFieldLeak")
                    object : ImageCompression(this@AccountActivity) {
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
            getPresenter()?.onCheck(name,email,phone,address)
        }

        iv_photo.setOnClickListener {
           // getPresenter()?.onCheckPhoto()
            choosePhotoHelper.showChooser()
        }

        btn_password.setOnClickListener {
            openPasswordPage()
        }
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Akun"

            val backArrow = ContextCompat.getDrawable(this@AccountActivity,R.drawable.ic_toolbar_back)//resources.getDrawable(R.drawable.ic_back_pos)
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

    override fun onClose(msg: String?,status: Int) {
        val callback = object: DialogCallback {
            override fun onSuccess() {
                setResult(status,intent)
                finish()
            }

            override fun onFailed() {

            }
        }

        if(msg.isNullOrEmpty() || msg.isNullOrBlank()){
            setResult(status,intent)
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

    override fun openImageChooser() {
        choosePhotoHelper.showChooser()
    }
    override fun loadPhoto(path: String) {
        Glide.with(this)
            .load(path)
            .error(R.drawable.ic_user_pos)
            .transform(CenterCrop(), CircleCrop())
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

    override fun setInfo(user: User) {
//        user.nama_lengkap?.let {
//            et_name.setText(it)
//        }
//
//        user.no_telp?.let {
//            et_phone.setText(it)
//        }
//
//        user.email?.let {
//            et_email.setText(it)
//        }
//
//        user.alamat?.let {
//            et_address.setText(it)
//        }
//
//        user.gbr?.let {
//            loadPhoto(it)
//        }
    }

    override fun openPasswordPage() {
        val intent = Intent(this,PasswordActivity::class.java)
        startActivity(intent)
    }



}
