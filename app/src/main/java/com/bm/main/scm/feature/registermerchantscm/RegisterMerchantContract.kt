package com.bm.main.scm.feature.registermerchantscm

import android.content.Context
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.user.RegisterMerchantRequest
import com.bm.main.scm.models.user.UserRestModel

interface RegisterMerchantContract {

    interface View : BaseViewImpl {
        fun enableBtnRegister(enable:Boolean)
        fun showSuccessDialog()
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun register( email: String,
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
                      alamat: String)
        fun checkBtnRegister( email: String,
                              additional_data: String,
                              no_telp:String,
                              nama_lengkap:String,
                              nama_toko:String,
                              password:String,
                              password2: String,
                              prop_code:String,
                              city_code:String,
                              kec_code:String,
                              kel_code:String,
                              kode_pos:String,
                              alamat:String)
        fun onDestroy()
    }

    interface Interactor : BaseInteractorImpl {
        fun callApiRegister(context: Context, userRestModel: UserRestModel, registerMerchantRequest: RegisterMerchantRequest)
//        fun callApiRegister(context: Context, userRestModel: UserRestModel, requestBody: RequestBody)
        fun onDestroy()
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onRegisterSuccess()
        fun onRegisterFailed(code:Int, msg:String)
    }


}