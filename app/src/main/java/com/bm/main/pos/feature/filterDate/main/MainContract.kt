package com.bm.main.pos.feature.filterDate.main

import android.content.Context
import android.content.Intent
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.FilterDialogDate
import com.bm.main.pos.models.cart.Cart
import com.bm.main.pos.models.user.User
import com.bm.main.pos.models.user.UserRestModel
import org.threeten.bp.LocalDate

interface MainContract {

    interface View : BaseViewImpl {
        fun onSelected(data:FilterDialogDate?)
        fun showDaily()
        fun showWeekly()
        fun showMonthly()
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun onDestroy()
        fun setSelectedData(data:FilterDialogDate?)
        fun getSelectedData():FilterDialogDate?
        fun setSelectedMenu(data:Int)
        fun onCheckDaily()
        fun onCheckWeekly()
        fun onCheckMonthly()
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()

    }

    interface InteractorOutput : BaseInteractorOutputImpl


}