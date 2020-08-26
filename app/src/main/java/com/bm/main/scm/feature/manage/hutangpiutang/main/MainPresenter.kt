package com.bm.main.scm.feature.manage.hutangpiutang.main

import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BasePresenter

class MainPresenter(val context: Context, val view: MainContract.View) : BasePresenter<MainContract.View>(),
    MainContract.Presenter,
    MainContract.InteractorOutput {

    private var interactor = MainInteractor(this)


    override fun onViewCreated(intent: Intent) {

    }

    override fun onDestroy() {
        interactor.onDestroy()
    }
}