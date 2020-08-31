package com.bm.main.scm.feature.cashout;

import com.bm.main.scm.utils.AppSession
import io.reactivex.disposables.CompositeDisposable

class CashoutInteractor(var output: CashoutContract.InteractorOutput?) : CashoutContract.Interactor {

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