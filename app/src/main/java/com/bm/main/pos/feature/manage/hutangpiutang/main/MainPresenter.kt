package com.bm.main.pos.feature.manage.hutangpiutang.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.utils.AppConstant

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