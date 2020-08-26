package com.bm.main.scm.events

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.scm.models.DialogModel

data class onMenuClicked(val id: Int)
data class onCode(val id: String)
data class onReloadTransaction(val isReload:Boolean)
data class onHistoryChangedDate(val firstDate: CalendarDay?,val lastDate: CalendarDay?)
data class onHistoryChangedStatus(val selected: DialogModel?)