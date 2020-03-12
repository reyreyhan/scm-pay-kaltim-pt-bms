package com.bm.main.pos.feature.manage.customer.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.bm.main.pos.R
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.callback.PermissionCallback
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.customer.CustomerRestModel
import com.bm.main.pos.models.supplier.Supplier
import com.bm.main.pos.models.supplier.SupplierRestModel
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.Helper
import com.bm.main.pos.utils.PermissionUtil

class EditCustomerPresenter(val context: Context, val view: EditCustomerContract.View) : BasePresenter<EditCustomerContract.View>(),
    EditCustomerContract.Presenter,
    EditCustomerContract.InteractorOutput {


    private var interactor = EditCustomerInteractor(this)
    private var restModel = CustomerRestModel(context)
    private var data: Customer? = null
    private var newdata: Customer? = null
    private var photoPath: String? = null
    private var permissionUtil: PermissionUtil = PermissionUtil(context)
    private lateinit var photoPermission: PermissionCallback


    override fun onViewCreated(intent: Intent) {
        photoPermission = object : PermissionCallback {
            override fun onSuccess() {
                view.openImageChooser()
            }

            override fun onFailed() {
                view.showMessage(999, context.getString(R.string.reason_permission_camera))
            }
        }
        data = intent.getSerializableExtra(AppConstant.DATA) as Customer
        if (data == null) {
            view.onClose(null, Activity.RESULT_CANCELED, data)
            return
        }

        data?.let {
            view.setCustomer(it.nama_pelanggan, it.email, it.telpon, it.alamat, it.gbr)
        }
    }

    override fun onCheck(name: String, email: String, phone: String, address: String) {
        if (name.isNullOrBlank() || name.isNullOrEmpty()) {
            view.showMessage(999, context.getString(R.string.err_empty_name))
            return
        }

//        if (email.isNotEmpty() && email.isNotBlank()) {
//            if (!Helper.isEmailValid(email)) {
//                view.showMessage(999, context.getString(R.string.err_email_format))
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

        newdata = Customer()
        newdata?.id_pelanggan = data?.id_pelanggan
        newdata?.nama_pelanggan = name
        newdata?.email = email
        newdata?.telpon = phone
        newdata?.alamat = address
        newdata?.gbr = photoPath
        interactor.callEditCustomerAPI(context, restModel, data?.id_pelanggan!!, name, email, phone, address, photoPath)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessEditCustomer(msg: String?) {
        view.onClose(msg, Activity.RESULT_OK, newdata)
    }

    override fun onFailedEditCustomer(code: Int, msg: String) {
        view.showMessage(code, msg)
    }

    override fun onCheckPhoto() {
        permissionUtil.checkCameraPermission(photoPermission)
    }

    override fun setImagePhotoPath(path: String?) {
        photoPath = path
    }

}