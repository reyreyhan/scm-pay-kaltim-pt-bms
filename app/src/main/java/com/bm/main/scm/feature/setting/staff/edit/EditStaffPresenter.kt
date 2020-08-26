package com.bm.main.scm.feature.setting.staff.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.bm.main.scm.R
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.callback.PermissionCallback
import com.bm.main.scm.models.staff.Staff
import com.bm.main.scm.models.staff.StaffRestModel
import com.bm.main.scm.utils.AppConstant
import com.bm.main.scm.utils.Helper
import com.bm.main.scm.utils.PermissionUtil

class EditStaffPresenter(val context: Context, val view: EditStaffContract.View) : BasePresenter<EditStaffContract.View>(),
    EditStaffContract.Presenter, EditStaffContract.InteractorOutput {


    private var interactor = EditStaffInteractor(this)
    private var restModel = StaffRestModel(context)
    private var data : Staff ?= null
    private var newdata : Staff ?= null
    private var photoPath:String? = null
    private var permissionUtil: PermissionUtil = PermissionUtil(context)
    private lateinit var photoPermission:PermissionCallback
    private var level:String? = ""




    override fun onViewCreated(intent: Intent) {
        photoPermission = object : PermissionCallback {
            override fun onSuccess() {
                view.openImageChooser()
            }

            override fun onFailed() {
                view.showMessage(999,context.getString(R.string.reason_permission_camera))
            }
        }
        data = intent.getSerializableExtra(AppConstant.DATA) as Staff
        if(data == null){
            view.onClose(null,Activity.RESULT_CANCELED,data)
            return
        }

        data?.let {
            view.setStaff(it.nama_lengkap,it.email,it.no_telp,it.alamat,it.level,it.gbr)
        }
    }

    override fun onCheck(name:String,email:String,phone:String,address:String) {
        if(name.isBlank() || name.isEmpty()){
            view.showMessage(999,context.getString(R.string.err_empty_name))
            return
        }

        if(email.isBlank() || email.isEmpty()){
            view.showMessage(999,context.getString(R.string.err_empty_email))
            return
        }

        if(!Helper.isEmailValid(email)){
            view.showMessage(999,context.getString(R.string.err_email_format))
            return
        }

        if(phone.isBlank() || phone.isEmpty()){
            view.showMessage(999,context.getString(R.string.err_empty_phone))
            return
        }

        if(!Helper.isPhoneValid(phone)){
            view.showMessage(999,context.getString(R.string.err_phone_format))
            return
        }

        if(address.isBlank() || address.isEmpty()){
            view.showMessage(999,context.getString(R.string.err_empty_address))
            return
        }

        newdata = Staff()
        newdata?.key = data?.key
        newdata?.nama_lengkap = name
        newdata?.email = email
        newdata?.no_telp = phone
        newdata?.alamat = address
        newdata?.level = level
        newdata?.gbr = photoPath
        interactor.callEditAPI(context,restModel,data?.key!!,name,email,phone,address,level!!,photoPath)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessEdit(msg: String?) {
        view.onClose(msg,Activity.RESULT_OK,newdata)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showMessage(code,msg)
    }

    override fun onCheckPhoto() {
        permissionUtil.checkCameraPermission(photoPermission)
    }

    override fun setImagePhotoPath(path: String?) {
        photoPath = path
    }

    override fun setLevel(value: String?) {
        value?.let {
            level = it
        }
    }

}