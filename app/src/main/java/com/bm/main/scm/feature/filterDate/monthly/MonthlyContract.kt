package com.bm.main.scm.feature.filterDate.monthly

import android.content.Intent
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.FilterDialogDate

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