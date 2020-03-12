package com.bm.main.pos.feature.report.labarugi.main;

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.models.report.ReportLabaRugi
import com.bm.main.pos.models.report.ReportRestModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

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

    override fun callGetReportsAPI(context: Context, restModel: ReportRestModel, awal:String, akhir:String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.labaRugi(key!!,awal,akhir).subscribeWith(object : DisposableObserver<ReportLabaRugi>() {

            override fun onNext(@NonNull response: ReportLabaRugi) {
                output?.onSuccessGetReports(response)
            }

            override fun onError(@NonNull e: Throwable) {
                e.printStackTrace()
                var errorCode = 999
                var errorMessage = "Terjadi kesalahan"
                if (e is RestException) {
                    errorCode = e.errorCode
                    errorMessage = e.message ?: "Terjadi kesalahan"
                }
                else{
                    errorMessage = e.message.toString()
                }
                output?.onFailedAPI(errorCode,errorMessage)
            }

            override fun onComplete() {

            }
        }))
    }
}