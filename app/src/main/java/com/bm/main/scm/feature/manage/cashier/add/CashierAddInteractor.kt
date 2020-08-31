package com.bm.main.scm.feature.manage.cashier.add;

import com.bm.main.scm.utils.AppSession
import io.reactivex.disposables.CompositeDisposable

class CashierAddInteractor(var output: CashierAddContract.InteractorOutput?) : CashierAddContract.Interactor {

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