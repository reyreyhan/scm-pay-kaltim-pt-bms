package com.bm.main.scm.feature.setting.account

import android.app.Activity
import android.content.Context
import com.bm.main.scm.R
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.utils.Helper
import com.bm.main.scm.callback.PermissionCallback
import com.bm.main.scm.models.user.UserRestModel
import com.bm.main.scm.utils.PermissionUtil
//import com.google.firebase.inappmessaging.internal.Logging


class AccountPresenter(val context: Context, val view: AccountContract.View) : BasePresenter<AccountContract.View>(),
    AccountContract.Presenter, AccountContract.InteractorOutput {

    private var interactor = AccountInteractor(this)
    private var restModel = UserRestModel(context)
    private var photoPath:String? = null
    private var permissionUtil: PermissionUtil = PermissionUtil(context)
    private lateinit var photoPermission:PermissionCallback



    override fun onViewCreated() {
        photoPermission = object : PermissionCallback {
            override fun onSuccess() {
//                Logging.logd("success open camera")
                view.openImageChooser()
            }

            override fun onFailed() {
                view.showMessage(999,context.getString(R.string.reason_permission_camera))
            }
        }

        val user = interactor.loadProfile(context)
        user?.let {
            view.setInfo(it)
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

        interactor.callUpdateAPI(context,restModel,name,email,phone,address,photoPath)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessUpdate(msg: String?) {
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
}