package com.bm.main.pos.feature.report.main

import android.content.Context
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.feature.setting.main.SettingInteractor
import com.bm.main.pos.feature.setting.main.SettingContract

class ReportPresenter(val context: Context, val view: ReportContract.View) : BasePresenter<ReportContract.View>(),
    ReportContract.Presenter, ReportContract.InteractorOutput {


    private var interactor = ReportInteractor(this)

    override fun onViewCreated() {

    }
}