package com.bm.main.scm.feature.registerqrismerchant

import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl

interface RegisterQRISMerchantContract {

    interface View : BaseViewImpl {
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()

    }

    interface Interactor : BaseInteractorImpl {
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
    }


}