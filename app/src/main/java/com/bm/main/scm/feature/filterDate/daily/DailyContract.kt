package com.bm.main.scm.feature.filterDate.daily

import android.content.Intent
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.FilterDialogDate
import org.threeten.bp.LocalDate

interface DailyContract {

    interface View : BaseViewImpl {
        fun setMaxdate(data:LocalDate?)
        fun setFirstDateText(text:String?)
        fun setLastDateText(text:String?)
        fun setRange(first:CalendarDay?,last:CalendarDay?)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun getSelectedData(): FilterDialogDate?
        fun setFirstDate(data:CalendarDay?)
        fun setLastDate(data:CalendarDay?)
        fun onDestroy()
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
    }

    interface InteractorOutput : BaseInteractorOutputImpl {

    }


}