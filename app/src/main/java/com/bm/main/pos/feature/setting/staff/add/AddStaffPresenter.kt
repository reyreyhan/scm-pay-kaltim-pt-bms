package com.bm.main.pos.feature.setting.staff.add

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import com.bm.main.pos.R
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.supplier.SupplierRestModel
import com.bm.main.pos.utils.Helper
import com.google.gson.reflect.TypeToken
import android.util.Log
import com.bm.main.pos.callback.PermissionCallback
import com.bm.main.pos.models.customer.CustomerRestModel
import com.bm.main.pos.models.staff.StaffRestModel
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.PermissionUtil


class AddStaffPresenter(val context: Context, val view: AddStaffContract.View) : BasePresenter<AddStaffContract.View>(),
    AddStaffContract.Presenter, AddStaffContract.InteractorOutput {

    private var interactor = AddStaffInteractor(this)
    private var restModel = StaffRestModel(context)
    private var photoPath:String? = null
    private var level:String? = ""
    private var permissionUtil: PermissionUtil = PermissionUtil(context)
    private lateinit var photoPermission:PermissionCallback



    override fun onViewCreated() {
        photoPermission = object : PermissionCallback {
            override fun onSuccess() {
                view.openImageChooser()
            }

            override fun onFailed() {
                view.showMessage(999,context.getString(R.string.reason_permission_camera))
            }
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

        interactor.callAddAPI(context,restModel,name,email,phone,address,level!!,photoPath)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessAdd(msg: String?) {
        view.onClose(msg,Activity.RESULT_OK)
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