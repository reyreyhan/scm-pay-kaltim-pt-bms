package com.bm.main.scm.feature.filterDate.daily

import android.content.Context
import android.content.Intent
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.FilterDialogDate
import com.bm.main.scm.utils.AppConstant
import com.bm.main.scm.utils.Helper
import org.threeten.bp.LocalDate

class DailyPresenter(val context: Context, val view: DailyContract.View) : BasePresenter<DailyContract.View>(),
    DailyContract.Presenter, DailyContract.InteractorOutput {

    private var interactor = DailyInteractor(this)
    private var selected:FilterDialogDate? = null

    override fun onViewCreated(intent: Intent) {
        val today = LocalDate.now()
        view.setMaxdate(today)
        selected = intent.getParcelableExtra(AppConstant.DATA) as FilterDialogDate
        if(selected == null){
            selected = FilterDialogDate()
            selected?.id = AppConstant.FilterDate.DAILY
        }
        else{
            if(AppConstant.FilterDate.DAILY == selected?.id){
                selected?.firstDate?.let {
                    view.setFirstDateText(Helper.getDateFormat(context, it.date.toString(), "yyyy-MM-dd", "dd MMMM yyyy"))
                }
                selected?.lastDate?.let {
                    view.setLastDateText(Helper.getDateFormat(context, it.date.toString(), "yyyy-MM-dd", "dd MMMM yyyy"))
                }

                view.setRange(selected?.firstDate,selected?.lastDate)
            }
            else{
                selected = FilterDialogDate()
                selected?.id = AppConstant.FilterDate.DAILY
            }

        }

    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun getSelectedData(): FilterDialogDate? {
        return selected
    }

    override fun setFirstDate(data: CalendarDay?) {
        selected?.firstDate = data
        view.setFirstDateText("")
        selected?.firstDate?.let {
            view.setFirstDateText(Helper.getDateFormat(context, it.date.toString(), "yyyy-MM-dd", "dd MMMM yyyy"))
        }
    }

    override fun setLastDate(data: CalendarDay?) {
        selected?.lastDate = data
        view.setLastDateText("")
        selected?.lastDate?.let {
            view.setLastDateText(Helper.getDateFormat(context, it.date.toString(), "yyyy-MM-dd", "dd MMMM yyyy"))
        }
    }

}