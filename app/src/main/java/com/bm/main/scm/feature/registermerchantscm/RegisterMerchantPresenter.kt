package com.bm.main.scm.feature.registermerchantscm

import android.content.Context
import com.bm.main.fpl.utils.StringJson
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.user.RegisterMerchantRequest
import com.bm.main.scm.models.user.UserRestModel
import timber.log.Timber


class RegisterMerchantPresenter(val context: Context, val view: RegisterMerchantContract.View) :
    BasePresenter<RegisterMerchantContract.View>(),
    RegisterMerchantContract.Presenter, RegisterMerchantContract.InteractorOutput {

    private var interactor: RegisterMerchantInteractor = RegisterMerchantInteractor(this)
    private var userRestModel: UserRestModel = UserRestModel(context)

    override fun onViewCreated() {

    }

    override fun register(
        email: String,
        additional_data: String,
        no_telp: String,
        nama_lengkap: String,
        nama_toko: String,
        password: String,
        prop_code: String,
        city_code: String,
        kec_code: String,
        kel_code: String,
        kode_pos: String,
        alamat: String
    ) {
//        val jsonObject: JsonObject = JsonParser().parse(StringJson(context).requestAddtionalData()).asJsonObject
        val request = RegisterMerchantRequest(
            email,
//            jsonObject,
            StringJson(context).requestAddtionalData(),
            no_telp,
            nama_lengkap,
            nama_toko,
            password,
            prop_code,
            city_code,
            kec_code,
            kel_code,
            kode_pos,
            alamat
        )
        /*val gson = Gson()
        val json = gson.toJson(request)
        val bodyRequest = RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"), json
        )*/
        interactor.callApiRegister(context, userRestModel, request)
    }

    override fun checkBtnRegister(
        email: String,
        additional_data: String,
        no_telp: String,
        nama_lengkap: String,
        nama_toko: String,
        password: String,
        password2: String,
        prop_code: String,
        city_code: String,
        kec_code: String,
        kel_code: String,
        kode_pos: String,
        alamat: String
    ) {
        if (nama_lengkap.isEmpty()) {
            view.enableBtnRegister(false)
            Timber.d("Nama Lengkap Kosong")
            return
        }
        if (nama_toko.isEmpty()) {
            view.enableBtnRegister(false)
            Timber.d("Nama Toko Kosong")
            return
        }
        if (no_telp.isEmpty()) {
            view.enableBtnRegister(false)
            Timber.d("No Telp Kosong")
            return
        }
        //        if (Helper.isPhoneValid(no_telp)){
//            view.enableBtnRegister(false)
//            return
//        }
        if (password.isEmpty()) {
            view.enableBtnRegister(false)
            Timber.d("Pin Kosong")
            return
        }
        if (password2.isEmpty()) {
            view.enableBtnRegister(false)
            Timber.d("Pin2 Kosong")
            return
        }
        //        if (password2!=password){
//            view.enableBtnRegister(false)
//            return
//        }
        if (prop_code.isEmpty()) {
            view.enableBtnRegister(false)
            Timber.d("prov Kosong")
            return
        }
        if (city_code.isEmpty()) {
            view.enableBtnRegister(false)
            Timber.d("city Kosong")
            return
        }
        if (kec_code.isEmpty()) {
            view.enableBtnRegister(false)
            Timber.d("district Kosong")
            return
        }
        if (kel_code.isEmpty()) {
            view.enableBtnRegister(false)
            Timber.d("subdistrict Kosong")
            return
        }
        if (kode_pos.isEmpty()) {
            view.enableBtnRegister(false)
            Timber.d("subdistrict Kosong")
            return
        }
        if (email.isEmpty()) {
            view.enableBtnRegister(false)
            Timber.d("Email Kosong")
            return
        }
        if (alamat.isEmpty()) {
            view.enableBtnRegister(false)
            Timber.d("address Kosong")
            return
        }
        view.enableBtnRegister(true)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onRegisterSuccess() {
        view.showSuccessDialog()
    }

    override fun onRegisterFailed(code: Int, msg: String) {
        view.showToast(msg)
    }

}