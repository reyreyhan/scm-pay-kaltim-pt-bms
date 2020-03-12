package com.bm.main.pos.feature.splash

import android.content.Intent
import android.os.Bundle
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity


class SplashActivity : BaseActivity<SplashPresenter, SplashContract.View>(), SplashContract.View {

    override fun createPresenter(): SplashPresenter {
        return SplashPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_splash
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        getPresenter()?.onViewCreated()
    }

    override fun openLoginScreen() {
        restartLoginActivity()
    }


}
