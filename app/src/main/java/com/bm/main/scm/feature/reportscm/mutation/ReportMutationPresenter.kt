package com.bm.main.scm.feature.reportscm.mutation

import android.content.Context
import com.bm.main.scm.base.BasePresenter

class ReportMutationPresenter(val context: Context, val view: ReportMutationContract.View) : BasePresenter<ReportMutationContract.View>(),
    ReportMutationContract.Presenter, ReportMutationContract.InteractorOutput {

//    private var interactor: LoginInteractor = LoginInteractor(this)
//    private var userRestModel = UserRestModel(context)


    override fun onViewCreated() {
//        interactor.clearSession()
    }

    override fun onDestroy() {
//        interactor.onDestroy()
    }


}