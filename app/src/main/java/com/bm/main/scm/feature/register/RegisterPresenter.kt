package com.bm.main.scm.feature.register

import android.content.Context
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.feature.registermerchant.RegisterMerchantContract

class RegisterPresenter(val context: Context, val view: RegisterMerchantContract.View) : BasePresenter<RegisterMerchantContract.View>(),
    RegisterMerchantContract.Presenter, RegisterMerchantContract.InteractorOutput {

//    private var interactor: RegisterInteractor = RegisterInteractor(this)

    override fun onViewCreated() {

    }



}