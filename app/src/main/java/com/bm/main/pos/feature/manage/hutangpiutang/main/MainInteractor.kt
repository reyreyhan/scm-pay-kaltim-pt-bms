package com.bm.main.pos.feature.manage.hutangpiutang.main;

import com.bm.main.pos.utils.AppSession
import io.reactivex.disposables.CompositeDisposable

class MainInteractor(var output: MainContract.InteractorOutput?) :
    MainContract.Interactor {

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