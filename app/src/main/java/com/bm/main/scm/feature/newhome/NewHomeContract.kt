package com.bm.main.scm.feature.newhome

import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.user.User
import com.bm.main.scm.models.user.UserRestModel

interface NewHomeContract {

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
        fun saveUser(user: User)
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetProfile(list: List<User>)
        fun onFailedAPI(code: Int, msg: String)
    }

}