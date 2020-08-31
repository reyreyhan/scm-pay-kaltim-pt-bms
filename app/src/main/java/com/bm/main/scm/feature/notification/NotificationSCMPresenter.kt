package com.bm.main.scm.feature.notification

import android.content.Context
import com.bm.main.scm.base.BasePresenter

class NotificationSCMPresenter(val context: Context, val view: NotificationSCMContract.View) : BasePresenter<NotificationSCMContract.View>(),
    NotificationSCMContract.Presenter, NotificationSCMContract.InteractorOutput {

//    private var interactor: LoginInteractor = LoginInteractor(this)
//    private var userRestModel = UserRestModel(context)


    override fun onViewCreated() {
//        interactor.clearSession()
    }

    override fun onDestroy() {
//        interactor.onDestroy()
    }


}