package com.bm.main.pos.feature.filterDate.weekly

import android.content.Intent
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.FilterDialogDate
import org.threeten.bp.LocalDate

interface WeeklyContract {

    interface View : BaseViewImpl {
        fun setMonthYear(date:String)
        fun setList(list:List<FilterDialogDate>,selected:FilterDialogDate)
        fun hideNext()
        fun showNext()
        fun hidePrev()
        fun showPrev()
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun onDestroy()
        fun getSelectedData(): FilterDialogDate?
        fun getDates(date:LocalDate):List<FilterDialogDate>
        fun onNextMonth()
        fun onPrevMonth()
        fun setDate()
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
    }

    interface InteractorOutput : BaseInteractorOutputImpl {

    }


}