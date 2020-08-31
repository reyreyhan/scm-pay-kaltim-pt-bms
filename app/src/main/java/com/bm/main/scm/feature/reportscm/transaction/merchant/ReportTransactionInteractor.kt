package com.bm.main.scm.feature.reportscm.transaction.merchant;

import com.bm.main.scm.utils.AppSession
import io.reactivex.disposables.CompositeDisposable

class ReportTransactionInteractor(var output: ReportTransactionContract.InteractorOutput?) : ReportTransactionContract.Interactor {

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