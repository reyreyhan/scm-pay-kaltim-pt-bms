package com.bm.main.scm.feature.manage.discount.list

import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.discount.Discount


interface DiscountListContract {

    interface View : BaseViewImpl {
        fun setData(list:List<Discount>)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
    }

    interface Interactor : BaseInteractorImpl {
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
    }


}