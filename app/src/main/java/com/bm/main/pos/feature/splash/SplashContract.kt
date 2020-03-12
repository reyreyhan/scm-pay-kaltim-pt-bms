package com.bm.main.pos.feature.splash

import android.content.Context
import android.content.Intent
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl

interface SplashContract {

    interface View : BaseViewImpl {
        fun openLoginScreen()
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()

    }

    interface Interactor : BaseInteractorImpl {
        fun delayScreen(context: Context, time:Long)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun delaySuccess(isLogin:Boolean)
    }


}