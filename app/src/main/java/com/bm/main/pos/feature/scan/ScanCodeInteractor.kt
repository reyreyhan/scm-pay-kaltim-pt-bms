package com.bm.main.pos.feature.scan

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class ScanCodeInteractor(val output: ScanCodeContract.InteractorOutput) :

    ScanCodeContract.Interactor {
    private var compositeDisposable = CompositeDisposable()

    override fun destroy() {
        compositeDisposable.clear()
    }


}
