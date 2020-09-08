package com.bm.main.scm.feature.qrisscm

import android.content.Context
import com.bm.main.scm.base.BasePresenter

class QrisSCMPresenter(val context: Context, val view: QrisSCMContract.View) : BasePresenter<QrisSCMContract.View>(),
    QrisSCMContract.Presenter, QrisSCMContract.InteractorOutput {

//    private var interactor: LoginInteractor = LoginInteractor(this)
//    private var userRestModel = UserRestModel(context)


    override fun onViewCreated() {
//        interactor.clearSession()
    }

    override fun onDestroy() {
//        interactor.onDestroy()
    }


}