package com.bm.main.scm.feature.printer

import com.bm.main.scm.utils.AppSession
import io.reactivex.disposables.CompositeDisposable

class PrinterInteractor(var output: PrinterContract.InteractorOutput?) : PrinterContract.Interactor {

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