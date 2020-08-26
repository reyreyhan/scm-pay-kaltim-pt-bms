package com.bm.main.scm.feature.manage.main

import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl

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