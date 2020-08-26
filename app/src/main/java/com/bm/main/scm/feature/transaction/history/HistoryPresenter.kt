package com.bm.main.scm.feature.transaction.history

import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.utils.AppConstant

class HistoryPresenter(val context: Context, val view: HistoryContract.View) : BasePresenter<HistoryContract.View>(),
    HistoryContract.Presenter,
    HistoryContract.InteractorOutput {

    private var interactor = HistoryInteractor(this)


    override fun onViewCreated(intent: Intent) {
        val position = intent.getIntExtra(AppConstant.POSITION,0)
        view.setSelectTab(position)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }
}