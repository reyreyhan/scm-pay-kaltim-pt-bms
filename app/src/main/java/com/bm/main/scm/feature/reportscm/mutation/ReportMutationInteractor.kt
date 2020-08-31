package com.bm.main.scm.feature.reportscm.mutation;

import com.bm.main.scm.utils.AppSession
import io.reactivex.disposables.CompositeDisposable

class ReportMutationInteractor(var output: ReportMutationContract.InteractorOutput?) : ReportMutationContract.Interactor {

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