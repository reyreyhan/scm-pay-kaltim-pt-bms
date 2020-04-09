package com.bm.main.pos.feature.newhome

import android.content.Context
import android.content.Intent
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.cart.Cart
import com.bm.main.pos.models.menu.Menu
import com.bm.main.pos.models.user.User
import com.bm.main.pos.models.user.UserRestModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.LocalDate

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