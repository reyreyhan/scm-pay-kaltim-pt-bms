package com.bm.main.pos.feature.splash

import android.content.Context
import android.content.Intent
import android.util.Log
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.utils.AppConstant

class SplashPresenter(val context: Context, val view: SplashContract.View) : BasePresenter<SplashContract.View>(),
        SplashContract.Presenter, SplashContract.InteractorOutput {


    private var interactor: SplashInteractor = SplashInteractor(this)

    override fun onViewCreated() {
        interactor.delayScreen(context,AppConstant.SPLASH_TIMER)
    }

    override fun delaySuccess(isLogin:Boolean) {
        if(isLogin){
            view.openLoginScreen()
        }
        else{
            view.restartMainActivity()
        }

    }
}