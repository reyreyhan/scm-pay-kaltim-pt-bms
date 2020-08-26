package com.bm.main.scm.feature.splash

import android.os.Bundle
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity


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
