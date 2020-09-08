package com.bm.main.scm.feature.qrisscm;

import com.bm.main.scm.utils.AppSession
import io.reactivex.disposables.CompositeDisposable

class QrisSCMInteractor(var output: QrisSCMContract.InteractorOutput?) : QrisSCMContract.Interactor {

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