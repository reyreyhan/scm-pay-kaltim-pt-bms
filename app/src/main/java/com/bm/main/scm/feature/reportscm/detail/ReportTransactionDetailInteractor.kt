package com.bm.main.scm.feature.reportscm.detail;

import com.bm.main.scm.utils.AppSession
import io.reactivex.disposables.CompositeDisposable

class ReportTransactionDetailInteractor(var output: ReportTransactionDetailContract.InteractorOutput?) : ReportTransactionDetailContract.Interactor {

    private val appSession = AppSession()
    private var disposable = CompositeDisposable()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }
}