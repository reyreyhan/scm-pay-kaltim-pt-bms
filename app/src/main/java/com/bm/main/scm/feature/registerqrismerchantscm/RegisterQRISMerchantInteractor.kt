package com.bm.main.scm.feature.registerqrismerchantscm;

import com.bm.main.scm.rabbit.QrisMpService
import com.bm.main.scm.rabbit.QrisRegisterRequest
import com.bm.main.scm.utils.AppSession
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RegisterQRISMerchantInteractor(var output: RegisterQRISMerchantContract.InteractorOutput?) :
    RegisterQRISMerchantContract.Interactor {

    private val appSession = AppSession()
    private val disposables = CompositeDisposable()
    override fun onDestroy() {
        disposables.clear()
    }

    override fun callApiRegister(
        apiService: QrisMpService,
        request: QrisRegisterRequest
    ) {
        disposables.add(
            apiService.registerQris(
                request.id_outlet,
                request.kategori_usaha,
                request.nama,
                request.jenis_kelamin,
                request.nik,
                request.kriteria_usaha,
                request.izin_usaha,
                request.npwp,
                request.id_propinsi,
                request.id_kota,
                request.id_kecamatan,
                request.id_kelurahan,
                request.alamat
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    output?.onSuccessRegister(it)
                }, { error ->
                    output?.onFailedRegister(error.message!!)
                })
        )
    }

//    override fun callApiRegister(apiService: LengkapiQrisRestInterface, lengkapiQrisRequest: LengkapiQrisRequest) {
//        disposables.add(apiService.daftarQqris(
//            lengkapiQrisRequest.id_outlet,
//            lengkapiQrisRequest.nama,
//            lengkapiQrisRequest.no_idt,
//            lengkapiQrisRequest.tipe_idt,
//            lengkapiQrisRequest.gendre,
//            lengkapiQrisRequest.id_propinsi,
//            lengkapiQrisRequest.id_kota,
//            lengkapiQrisRequest.id_kecamatan,
//            lengkapiQrisRequest.id_kelurahan,
//            lengkapiQrisRequest.alamat,
//            lengkapiQrisRequest.foto_ktp,
//            lengkapiQrisRequest.foto_selfi,
//            lengkapiQrisRequest.foto_toko_dpn,
//            lengkapiQrisRequest.kd_pos,
//            lengkapiQrisRequest.nama_toko,
//            lengkapiQrisRequest.kd_pos_toko,
//            lengkapiQrisRequest.kelurahan_toko,
//            lengkapiQrisRequest.kabupaten_toko,
//            lengkapiQrisRequest.propinsi_toko,
//            lengkapiQrisRequest.alamat_toko,
//            lengkapiQrisRequest.email,
//            lengkapiQrisRequest.handphone,
//            lengkapiQrisRequest.no_whatsapp,
//            lengkapiQrisRequest.created)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//                output?.onSuccessRegister(it)
//            },{error ->
//                output?.onFailedRegister(error.message!!)
//            }))
//    }

}