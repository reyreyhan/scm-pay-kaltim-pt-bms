package com.bm.main.scm.feature.report.labarugi.cashflow

import com.bm.main.scm.utils.AppSession
import io.reactivex.disposables.CompositeDisposable

class CashFlowInteractor(var output: CashFlowContract.InteractorOutput?) : CashFlowContract.Interactor {

    private var disposable = CompositeDisposable()
    private val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

}