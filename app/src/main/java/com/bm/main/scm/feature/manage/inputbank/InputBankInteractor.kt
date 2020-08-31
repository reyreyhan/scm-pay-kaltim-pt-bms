package com.bm.main.scm.feature.manage.inputbank;

import com.bm.main.scm.utils.AppSession
import io.reactivex.disposables.CompositeDisposable

class InputBankInteractor(var output: InputBankContract.InteractorOutput?) : InputBankContract.Interactor {

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