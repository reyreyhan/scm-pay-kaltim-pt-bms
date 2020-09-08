package com.bm.main.scm.feature.profilescm;

import com.bm.main.scm.models.user.merchant.MerchantToko
import com.bm.main.scm.models.user.merchant.MerchantUser
import com.bm.main.scm.utils.AppConstant
import com.bm.main.scm.utils.AppSession
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable

class ProfileSCMInteractor(var output: ProfileSCMContract.InteractorOutput?) : ProfileSCMContract.Interactor {

    private val appSession = AppSession()
    private var disposable = CompositeDisposable()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun getProfile() {
        val userJson = appSession.getSharedPreferenceString(AppConstant.USER)
        val tokoJson = appSession.getSharedPreferenceString(AppConstant.TOKO)
//        Timber.d("user: %s", userJson)
        val user = Gson().fromJson(userJson, MerchantUser::class.java)
        val toko = Gson().fromJson(tokoJson, MerchantToko::class.java)
//        Timber.d("user name: %s", user.nama_lengkap)
        output?.onSuccessGetProfile(user, toko)
    }
}