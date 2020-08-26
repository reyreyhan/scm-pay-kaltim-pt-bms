package com.bm.main.scm.feature.setting.main

import android.content.Context
import com.bm.main.scm.base.BasePresenter

class SettingPresenter(val context: Context, val view: SettingContract.View) : BasePresenter<SettingContract.View>(),
    SettingContract.Presenter, SettingContract.InteractorOutput {


    private var interactor = SettingInteractor(this)

    override fun onViewCreated() {

    }
}