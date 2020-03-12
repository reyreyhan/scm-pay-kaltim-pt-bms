package com.bm.main.pos.feature.register

import android.content.Intent
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.user.User

interface RegisterContract {

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