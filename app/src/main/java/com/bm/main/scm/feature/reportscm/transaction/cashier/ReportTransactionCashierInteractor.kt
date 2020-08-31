package com.bm.main.scm.feature.reportscm.transaction.cashier;

import com.bm.main.scm.utils.AppSession
import io.reactivex.disposables.CompositeDisposable

class ReportTransactionCashierInteractor(var output: ReportTransactionCashierContract.InteractorOutput?) : ReportTransactionCashierContract.Interactor {

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