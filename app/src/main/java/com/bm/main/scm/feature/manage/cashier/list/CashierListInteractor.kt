package com.bm.main.scm.feature.manage.cashier.list;

import com.bm.main.scm.utils.AppSession
import io.reactivex.disposables.CompositeDisposable

class CashierListInteractor(var output: CashierListContract.InteractorOutput?) : CashierListContract.Interactor {

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