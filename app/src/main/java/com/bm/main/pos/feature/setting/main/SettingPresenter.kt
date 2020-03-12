package com.bm.main.pos.feature.setting.main

import android.content.Context
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.feature.setting.main.SettingInteractor
import com.bm.main.pos.feature.setting.main.SettingContract

class SettingPresenter(val context: Context, val view: SettingContract.View) : BasePresenter<SettingContract.View>(),
    SettingContract.Presenter, SettingContract.InteractorOutput {


    private var interactor = SettingInteractor(this)

    override fun onViewCreated() {

    }
}