package com.bm.main.pos.feature.filterDate.monthly

import android.content.Intent
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl
import com.bm.main.pos.models.FilterDialogDate
import org.threeten.bp.LocalDate

interface MonthlyContract {

    interface View : BaseViewImpl {
        fun setYear(year:String)
        fun setList(list:List<FilterDialogDate>,selected:FilterDialogDate)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun onDestroy()
        fun getSelectedData(): FilterDialogDate?
        fun getDates(year:Int):List<FilterDialogDate>
        fun onNextYear()
        fun onPrevYear()
        fun setDate()
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
    }

    interface InteractorOutput : BaseInteractorOutputImpl {

    }


}