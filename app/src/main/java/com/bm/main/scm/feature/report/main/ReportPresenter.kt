package com.bm.main.scm.feature.report.main

import android.content.Context
import com.bm.main.scm.base.BasePresenter

class ReportPresenter(val context: Context, val view: ReportContract.View) : BasePresenter<ReportContract.View>(),
    ReportContract.Presenter, ReportContract.InteractorOutput {


    private var interactor = ReportInteractor(this)

    override fun onViewCreated() {

    }
}