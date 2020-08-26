package com.bm.main.scm.feature.splash

import android.content.Context
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl

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