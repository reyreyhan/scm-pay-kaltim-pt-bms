package com.bm.main.pos.feature.filterDate.daily

import android.content.Intent
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.FilterDialogDate
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