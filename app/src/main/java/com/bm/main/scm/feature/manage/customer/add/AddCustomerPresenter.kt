package com.bm.main.scm.feature.manage.customer.add

import android.app.Activity
import android.content.Context
import com.bm.main.scm.R
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.callback.PermissionCallback
import com.bm.main.scm.models.customer.CustomerRestModel
import com.bm.main.scm.utils.Helper
import com.bm.main.scm.utils.PermissionUtil

class AddCustomerPresenter(val context: Context, val view: AddCustomerContract.View) :
    BasePresenter<AddCustomerContract.View>(),
    AddCustomerContract.Presenter, AddCustomerContract.InteractorOutput {

    private var interactor = AddCustomerInteractor(this)
    private var restModel = CustomerRestModel(context)
    private var photoPath: String? = null
    private var permissionUtil: PermissionUtil = PermissionUtil(context)
    private lateinit var photoPermission: PermissionCallback

    override fun onViewCreated() {
        photoPermission = object : PermissionCallback {
            override fun onSuccess() {
                view.openImageChooser()
            }

            override fun onFailed() {
                view.showMessage(999, context.getString(R.string.reason_permission_camera))
            }
        }
    }

    override fun onCheck(name: String, email: String, phone: String, address: String) {
        if (name.isNullOrBlank() || name.isNullOrEmpty()) {
            view.showMessage(999, context.getString(R.string.err_empty_name))
            return
        }

//        if(email.isNotEmpty() && email.isNotBlank()){
//            if(!Helper.isEmailValid(email)){
//                view.showMessage(999,context.getString(R.string.err_email_format))
//                return
//            }
//        }

        if (phone.isNullOrBlank() || phone.isNullOrEmpty()) {
            view.showMessage(999, context.getString(R.string.err_empty_phone))
            return
        }

        if (!Helper.isPhoneValid(phone)) {
            view.showMessage(999, context.getString(R.string.err_phone_format))
            return
        }

        if (address.isNullOrBlank() || address.isNullOrEmpty()) {
            view.showMessage(999, context.getString(R.string.err_empty_address))
            return
        }

        interactor.callAddCustomerAPI(context, restModel, name, email, phone, address, photoPath)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessAddCustomer(msg: String?) {
        view.onClose(msg, Activity.RESULT_OK)
    }

    override fun onFailedAddCustomer(code: Int, msg: String) {
        view.showMessage(code, msg)
    }

    override fun onCheckPhoto() {
        permissionUtil.checkCameraPermission(photoPermission)
    }

    override fun setImagePhotoPath(path: String?) {
        photoPath = path
    }
}