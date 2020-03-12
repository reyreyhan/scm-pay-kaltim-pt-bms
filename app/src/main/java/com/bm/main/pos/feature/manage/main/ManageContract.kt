package com.bm.main.pos.feature.manage.main

import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl

interface ManageContract {

    interface View : BaseViewImpl {
        fun openProductPage()
        fun openCategoryPage()
        fun openDiscountPage()
        fun openCustomerPage()
        fun openSupplierPage()
        fun openCreditPage()
        fun openKulakanPage()

    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()

    }

    interface Interactor : BaseInteractorImpl {

    }

    interface InteractorOutput : BaseInteractorOutputImpl {

    }


}