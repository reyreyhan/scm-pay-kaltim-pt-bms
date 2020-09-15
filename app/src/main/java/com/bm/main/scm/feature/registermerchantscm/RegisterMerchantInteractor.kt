package com.bm.main.scm.feature.registermerchantscm;

import android.content.Context
import com.bm.main.scm.models.Message
import com.bm.main.scm.models.user.RegisterMerchantRequest
import com.bm.main.scm.models.user.UserRestModel
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class RegisterMerchantInteractor(var output: RegisterMerchantContract.InteractorOutput?) : RegisterMerchantContract.Interactor {

    private val appSession = AppSession()
    private val compositeDisposable = CompositeDisposable()

    override fun callApiRegister(
        context: Context,
        userRestModel: UserRestModel,
        request: RegisterMerchantRequest
    ) {
        compositeDisposable.add(
            userRestModel
                .register(
                    request.email,
                    request.additional_data,
                    request.no_telp,
                    request.nama_lengkap,
                    request.nama_toko,
                    request.password,
                    request.prop_code,
                    request.city_code,
                    request.kec_code,
                    request.kel_code,
                    request.kode_pos,
                    request.alamat)
                .subscribeWith(object : DisposableObserver<Message>() {

            override fun onNext(@NonNull response: Message) {
                output?.onRegisterSuccess()
            }

            override fun onError(@NonNull e: Throwable) {
                e.printStackTrace()
                var errorCode = 999
                var errorMessage = "Terjadi kesalahan"
                if (e is RestException) {
                    errorCode = e.errorCode
                    errorMessage = e.message ?: "Terjadi kesalahan"
                }
                else{
                    errorMessage = e.message.toString()
                }
                output?.onRegisterFailed(errorCode,errorMessage)
            }

            override fun onComplete() {

            }
        }))
    }

//    override fun callApiRegister(
//        context: Context,
//        userRestModel: UserRestModel,
//       requestBody: RequestBody
//    ) {
//        compositeDisposable.add(
//            userRestModel
//                .register(requestBody)
//                .subscribeWith(object : DisposableObserver<Message>() {
//
//            override fun onNext(@NonNull response: Message) {
//                output?.onRegisterSuccess()
//            }
//
//            override fun onError(@NonNull e: Throwable) {
//                e.printStackTrace()
//                var errorCode = 999
//                var errorMessage = "Terjadi kesalahan"
//                if (e is RestException) {
//                    errorCode = e.errorCode
//                    errorMessage = e.message ?: "Terjadi kesalahan"
//                }
//                else{
//                    errorMessage = e.message.toString()
//                }
//                output?.onRegisterFailed(errorCode,errorMessage)
//            }
//
//            override fun onComplete() {
//
//            }
//        }))
//    }
    override fun onDestroy() {
        compositeDisposable.clear()
    }

}