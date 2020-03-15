package com.bm.main.pos.feature.newhome

import android.content.Context
import android.content.Intent
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.menu.Menu
import com.bm.main.pos.models.user.User
import com.bm.main.pos.models.user.UserRestModel

interface NewHomeContract {

    interface View : BaseViewImpl {
    }


    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()
        fun onDestroy()
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
    }


}