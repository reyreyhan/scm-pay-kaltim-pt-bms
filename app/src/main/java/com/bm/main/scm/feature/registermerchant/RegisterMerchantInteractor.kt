package com.bm.main.scm.feature.registermerchant;

import com.bm.main.scm.feature.registermerchant.RegisterMerchantContract
import com.bm.main.scm.utils.AppSession

class RegisterMerchantInteractor(var output: RegisterMerchantContract.InteractorOutput?) : RegisterMerchantContract.Interactor {

    private val appSession = AppSession()

}