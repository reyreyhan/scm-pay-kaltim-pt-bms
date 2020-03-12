package com.bm.main.pos.feature.splash

import android.content.Context
import android.os.Handler
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.utils.AppSession

class SplashInteractor(var output: SplashContract.InteractorOutput?) : SplashContract.Interactor {

    private val appSession = AppSession()

    override fun delayScreen(context:Context, time:Long) {
        val key = PreferenceClass.getTokenPos()

        Handler().postDelayed({
            output?.delaySuccess(key.isNullOrEmpty())
        },time)

    }



}