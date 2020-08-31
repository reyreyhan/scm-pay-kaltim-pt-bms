package com.bm.main.scm.feature.notification;

import com.bm.main.scm.utils.AppSession
import io.reactivex.disposables.CompositeDisposable

class NotificationSCMInteractor(var output: NotificationSCMContract.InteractorOutput?) : NotificationSCMContract.Interactor {

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