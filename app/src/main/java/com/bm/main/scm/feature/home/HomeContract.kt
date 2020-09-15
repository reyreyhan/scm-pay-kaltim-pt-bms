package com.bm.main.scm.feature.home

import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.user.Profile
import com.bm.main.scm.models.user.UserRestModel

interface HomeContract {

    interface View : BaseViewImpl {

    }


    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent:Intent)
        fun loadProfile()
        fun onDestroy()
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun callGetProfileAPI(context: Context, restModel: UserRestModel)
        fun saveUser(user: Profile)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetProfile(list: List<Profile>)
        fun onFailedAPI(code: Int, msg: String)
    }

}