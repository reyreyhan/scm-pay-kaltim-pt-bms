package com.bm.main.scm.feature.register;

import com.bm.main.scm.feature.registermerchant.RegisterMerchantContract
import com.bm.main.scm.utils.AppSession

class RegisterInteractor(var output: RegisterMerchantContract.InteractorOutput?) : RegisterMerchantContract.Interactor {

    private val appSession = AppSession()

}