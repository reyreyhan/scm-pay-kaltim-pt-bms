package com.bm.main.pos.feature.manage.discount.list

import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.discount.Discount


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