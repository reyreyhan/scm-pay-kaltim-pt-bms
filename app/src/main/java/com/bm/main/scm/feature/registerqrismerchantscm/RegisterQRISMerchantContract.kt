package com.bm.main.scm.feature.registerqrismerchantscm

import android.content.Intent
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.user.merchant.LengkapiQrisResponse
import com.bm.main.scm.rabbit.QrisMpService
import com.bm.main.scm.rabbit.QrisRegisterRequest

interface RegisterQRISMerchantContract {

    interface View : BaseViewImpl {
        fun setMerchantName(name: String)
        fun enableBtnRegister(enable: Boolean)
        fun getQrisMpApiService():QrisMpService
        fun showSuccessDialog()
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun onBtnRegisterCheck(
            katUsaha: String,
            kriUsaha: String,
            hp: String,
            ktp: String,
            prov: String,
            kota: String,
            kec: String,
            kel: String,
            alamat: String,
            fotoKtp: String,
            fotoSelfie: String
        )

        fun register(
            nama: String,
            gender: String,
            katUsaha: String,
            kriUsaha: String,
            hp: String,
            ktp: String,
            npwp: String,
            prov: String,
            kota: String,
            kec: String,
            kel: String,
            pos:String,
            alamat: String,
            fotoKtp: String,
            fotoSelfie: String,
            dateNow:String
        )

        fun onDestroy()
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
//        fun callApiRegister(
//            apiService: LengkapiQrisRestInterface,
//            lengkapiQris: LengkapiQrisRequest
//        )
        fun callApiRegister(
            apiService: QrisMpService,
            qrisRegisterRequest: QrisRegisterRequest
        )
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessRegister(response:LengkapiQrisResponse)
        fun onFailedRegister(msg: String)
    }


}