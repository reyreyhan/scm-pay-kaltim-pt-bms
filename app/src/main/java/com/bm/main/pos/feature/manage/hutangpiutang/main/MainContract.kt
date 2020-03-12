package com.bm.main.pos.feature.manage.hutangpiutang.main

import android.content.Intent
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.customer.Customer

interface MainContract {

    interface View : BaseViewImpl {


    }

    interface Presenter : BasePresenterImpl<View> {
        fun onDestroy()
        fun onViewCreated(intent: Intent)

    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()

    }

    interface InteractorOutput : BaseInteractorOutputImpl {

    }


}