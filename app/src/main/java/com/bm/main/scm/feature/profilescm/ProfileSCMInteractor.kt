package com.bm.main.scm.feature.profilescm;

import com.bm.main.scm.utils.AppSession
import io.reactivex.disposables.CompositeDisposable

class ProfileSCMInteractor(var output: ProfileSCMContract.InteractorOutput?) : ProfileSCMContract.Interactor {

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