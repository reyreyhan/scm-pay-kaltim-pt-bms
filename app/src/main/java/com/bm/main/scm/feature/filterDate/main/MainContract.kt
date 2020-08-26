package com.bm.main.scm.feature.filterDate.main

import android.content.Intent
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.FilterDialogDate

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