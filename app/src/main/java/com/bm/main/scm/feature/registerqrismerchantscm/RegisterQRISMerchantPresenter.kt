package com.bm.main.scm.feature.registerqrismerchantscm

import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.user.merchant.LengkapiQrisResponse
import com.bm.main.scm.rabbit.FastpayData
import com.bm.main.scm.rabbit.QrisRegisterRequest
import com.bm.main.scm.utils.Helper

class RegisterQRISMerchantPresenter(
    val context: Context,
    val view: RegisterQRISMerchantContract.View
) : BasePresenter<RegisterQRISMerchantContract.View>(),
    RegisterQRISMerchantContract.Presenter, RegisterQRISMerchantContract.InteractorOutput {

    private var interactor: RegisterQRISMerchantInteractor = RegisterQRISMerchantInteractor(this)
    private var fastpayData: FastpayData = FastpayData()

    override fun onViewCreated(intent: Intent) {
        fastpayData = intent.getParcelableExtra("FASTPAY_DATA")
        view.setMerchantName(fastpayData.nama_outlet!!)
    }

    override fun onBtnRegisterCheck(
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
    ) {
//        if (katUsaha.isEmpty()) {
//            view.enableBtnRegister(false)
//            return
//        }
//        if (kriUsaha.isEmpty()) {
//            view.enableBtnRegister(false)
//            return
//        }
//        if (hp.isEmpty()) {
//            view.enableBtnRegister(false)
//            return
//        }
//        if (!Helper.isPhoneValid(hp)) {
//            view.enableBtnRegister(false)
//            return
//        }
//        if (ktp.isEmpty()) {
//            view.enableBtnRegister(false)
//            return
//        }
//        if (prov.isEmpty()) {
//            view.enableBtnRegister(false)
//            return
//        }
//        if (kota.isEmpty()) {
//            view.enableBtnRegister(false)
//            return
//        }
//        if (kec.isEmpty()) {
//            view.enableBtnRegister(false)
//            return
//        }
//        if (kel.isEmpty()) {
//            view.enableBtnRegister(false)
//            return
//        }
//        if (alamat.isEmpty()) {
//            view.enableBtnRegister(false)
//            return
//        }
//        if (fotoKtp.isEmpty()) {
//            view.enableBtnRegister(false)
//            return
//        }
//        if (fotoSelfie.isEmpty()) {
//            view.enableBtnRegister(false)
//            return
//        }
//        view.enableBtnRegister(false)
    }

    override fun register(
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
    ) {
        view.showLoadingDialog()
//        var request = LengkapiQrisRequest(
//            Helper.createPartFromString(fastpayData.id_outlet!!),
//            Helper.createPartFromString(fastpayData.nama_pemilik!!),
//            Helper.createPartFromString(ktp),
//            Helper.createPartFromString("KTP"),
//            Helper.createPartFromString(gender),
//            Helper.createPartFromString(fastpayData.id_propinsi!!),
//            Helper.createPartFromString(fastpayData.id_kota!!),
//            Helper.createPartFromString(fastpayData.id_kecamatan!!),
//            Helper.createPartFromString(fastpayData.id_kelurahan!!),
//            Helper.createPartFromString(fastpayData.alamat_pemilik!!),
//            Helper.createPartFromFile(fotoKtp, "foto_ktp"),
//            Helper.createPartFromFile(fotoSelfie,"foto_selfie"),
//            Helper.createPartFromFile(fotoSelfie, "foto_toko_dpn"),
//            Helper.createPartFromString(fastpayData.kode_pos!!),
//            Helper.createPartFromString(nama),
//            Helper.createPartFromString(pos),
//            Helper.createPartFromString(kel),
//            Helper.createPartFromString(kota),
//            Helper.createPartFromString(prov),
//            Helper.createPartFromString(alamat),
//            Helper.createPartFromString(fastpayData.email!!),
//            Helper.createPartFromString(hp),
//            Helper.createPartFromString(hp),
//            Helper.createPartFromString(dateNow)
//        )
        val request = QrisRegisterRequest(
            Helper.createPartFromString(fastpayData.id_outlet!!),
            Helper.createPartFromString(katUsaha),
            Helper.createPartFromString(nama),
            Helper.createPartFromString(gender),
            Helper.createPartFromString(ktp),
            Helper.createPartFromString(kriUsaha),
            Helper.createPartFromString(""),
            Helper.createPartFromString(npwp),
            Helper.createPartFromString(prov),
            Helper.createPartFromString(kota),
            Helper.createPartFromString(kec),
            Helper.createPartFromString(kel),
            Helper.createPartFromString(alamat)
        )
        interactor.callApiRegister(view.getQrisMpApiService(), request)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessRegister(response:LengkapiQrisResponse) {
        view.hideLoadingDialog()
        view.showToast("$response.response_code, ${response.keterangan}")
        view.showSuccessDialog()
    }

    override fun onFailedRegister(msg: String) {
        view.showToast(msg)
    }


}