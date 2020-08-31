package com.bm.main.scm.feature.profilescm

import android.content.Context
import com.bm.main.scm.base.BasePresenter

class ProfileSCMPresenter(val context: Context, val view: ProfileSCMContract.View) : BasePresenter<ProfileSCMContract.View>(),
    ProfileSCMContract.Presenter, ProfileSCMContract.InteractorOutput {

//    private var interactor: LoginInteractor = LoginInteractor(this)
//    private var userRestModel = UserRestModel(context)


    override fun onViewCreated() {
//        interactor.clearSession()
    }

    override fun onDestroy() {
//        interactor.onDestroy()
    }


}