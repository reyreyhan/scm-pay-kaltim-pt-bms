package com.bm.main.scm.feature.filterDate.weekly

import android.content.Context
import android.content.Intent
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.FilterDialogDate
import com.bm.main.scm.utils.AppConstant
import com.bm.main.scm.utils.Helper
import org.threeten.bp.LocalDate
import kotlin.collections.ArrayList

class WeeklyPresenter(val context: Context, val view: WeeklyContract.View) :
    BasePresenter<WeeklyContract.View>(),
    WeeklyContract.Presenter, WeeklyContract.InteractorOutput {

    private var interactor = WeeklyInteractor(this)
    private var selected: FilterDialogDate? = null
    private val today = LocalDate.now()
    private val maxDate = today
    private var selectedDate = today


    override fun onViewCreated(intent: Intent) {
        selected = intent.getParcelableExtra(AppConstant.DATA) as FilterDialogDate
        if (selected == null) {
            selected = FilterDialogDate()
            selected?.id = AppConstant.FilterDate.MONTHLY
        } else {
            if (AppConstant.FilterDate.MONTHLY != selected?.id) {
                selected = FilterDialogDate()
                selected?.id = AppConstant.FilterDate.MONTHLY
            }
        }
        setDate()
//        view.hideNext()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun getSelectedData(): FilterDialogDate? {
        return selected
    }

    override fun getDates(date: LocalDate): List<FilterDialogDate> {
        val list = ArrayList<FilterDialogDate>()
        for (i in 0..3) {
            val model = generateModel(date, i)
            list.add(model)
        }
        return list
    }

    override fun setDate() {
        view.setMonthYear(
            Helper.getDateFormat(
                context,
                selectedDate.toString(),
                "yyyy-MM-dd",
                "MMMM yyyy"
            )
        )
        view.setList(getDates(selectedDate), selected!!)
    }

    override fun onNextMonth() {
        if (selectedDate < maxDate) {
            if (selectedDate.monthValue < 12) {
                selectedDate = selectedDate.plusMonths(1)
            } else {
                selectedDate = selectedDate.withMonth(1)
                selectedDate = selectedDate.plusYears(1)
            }
        }
        setDate()
    }

    override fun onPrevMonth() {
        if (selectedDate.monthValue > 1) {
            selectedDate = selectedDate.minusMonths(1)
        } else {
            selectedDate = selectedDate.withMonth(12)
            selectedDate = selectedDate.minusYears(1)
        }
        setDate()
    }

    private fun generateModel(date: LocalDate, count: Int): FilterDialogDate {
        val max = date.lengthOfMonth()
        val year = date.year
        val month = date.monthValue
        val first = CalendarDay.from(year, month, (count * 7) + 1)
        val model = FilterDialogDate()
        model.id = AppConstant.FilterDate.MONTHLY
        model.firstDate = first
        var last = CalendarDay.from(year, month, (count * 7) + 7)
        if (count == 3) {
            last = CalendarDay.from(year, month, max)
        }
        model.lastDate = last

        return model
    }
}