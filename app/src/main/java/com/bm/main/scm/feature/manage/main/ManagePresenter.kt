package com.bm.main.scm.feature.manage.main

import android.content.Context
import com.bm.main.scm.base.BasePresenter

class ManagePresenter(val context: Context, val view: ManageContract.View) : BasePresenter<ManageContract.View>(),
    ManageContract.Presenter,
    ManageContract.InteractorOutput {


    private var interactor: ManageInteractor =
        ManageInteractor(this)

    override fun onViewCreated() {

    }
}