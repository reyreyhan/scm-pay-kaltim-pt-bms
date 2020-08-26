package com.bm.main.scm.feature.transaction.historyTransaction

import android.content.Context
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.DialogModel
import com.bm.main.scm.models.FilterDialogDate
import com.bm.main.scm.models.transaction.HistoryTransaction
import com.bm.main.scm.models.transaction.Transaction
import com.bm.main.scm.models.transaction.TransactionRestModel
import com.bm.main.scm.utils.AppConstant
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.LocalDate

class TransactionPresenter(val context: Context, val view: TransactionContract.View) : BasePresenter<TransactionContract.View>(),
    TransactionContract.Presenter,
    TransactionContract.InteractorOutput {

    private var interactor = TransactionInteractor(this)
    private var transactionRestModel = TransactionRestModel(context)
    private var today: CalendarDay? = null
    private var filterSelected: DialogModel? = null
    private var filters: ArrayList<DialogModel>? = null
    private var selectedDate: FilterDialogDate? = null

    override fun onViewCreated() {
        val now = LocalDate.now()
        today = CalendarDay.from(now)
        val yesterday = today?.date!!.minusDays(30)
        val firstDate = CalendarDay.from(yesterday)
        val lastDate = today
        selectedDate = FilterDialogDate()
        selectedDate?.id = AppConstant.FilterDate.DAILY
        selectedDate?.firstDate = firstDate
        selectedDate?.lastDate = lastDate

        generateFilter()
        loadTransaction()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun loadTransaction() {
        interactor.callGetHistoryAPI(context, transactionRestModel, selectedDate?.firstDate?.date.toString(), selectedDate?.lastDate?.date.toString(), filterSelected!!.id.toString())
    }

    override fun onSuccessGetHistory(data: List<HistoryTransaction>?) {
        data?.let {
            if (it.isEmpty()) {
                view.showErrorMessage(999, "Data tidak ditemukan")
                return
            }
            val list = ArrayList<Transaction>()
            for (history in it) {
                history.detail?.let { detail ->
                    if (detail.isNotEmpty()) {
                        val header = Transaction()
                        header.tanggal = history.tanggal
                        header.type = "header"
                        val size = detail.size
                        for (i in size - 1 downTo 0) {
                            val trx = detail[i]
                            val pos = i + 1
                            trx.pos = pos.toString()
                            list.add(0, trx)
                        }
                        list.add(0, header)
                    }
                }
            }
            if (list.isEmpty()) {
                view.showErrorMessage(999, "Tidak ada data")
                return
            }
            view.setList(list)
        }
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code, msg)
    }

//    override fun getTodayDate(): CalendarDay {
//        return today!!
//    }

    override fun onChangeDate(firstDate: CalendarDay?, lastDate: CalendarDay?) {
        firstDate?.let {
            selectedDate?.firstDate = it
        }

        lastDate?.let {
            selectedDate?.lastDate = it
        }

        loadTransaction()
    }

//    override fun getFirstDate(): CalendarDay? {
//        return selectedDate?.firstDate
//    }
//
//    override fun getLastDate(): CalendarDay? {
//        return selectedDate?.lastDate
//    }

    override fun onSearch(id: String) {
        if (id.isEmpty()) {
            loadTransaction()
        } else {
            interactor.callGetSearchAPI(context, transactionRestModel, id)
        }
    }

    override fun onSuccessGetSearch(list: List<Transaction>?) {
        if (list == null) {
            view.showErrorMessage(999, "Data tidak ditemukan")
            return
        }
        if (list.isEmpty()) {
            view.showErrorMessage(999, "Data tidak ditemukan")
            return
        }
        view.setList(list)
    }

    private fun generateFilter() {
        filters = ArrayList()

        val all = DialogModel()
        all.id = ""
        all.value = "Semua"
        filters!!.add(all)
        filterSelected = all

        val hutang = DialogModel()
        hutang.id = "hutang"
        hutang.value = "Hutang"
        filters!!.add(hutang)

        val lunas = DialogModel()
        lunas.id = "lunas"
        lunas.value = "Lunas"
        filters!!.add(lunas)

        val batal = DialogModel()
        batal.id = "batal"
        batal.value = "Batal"
        filters!!.add(batal)

    }

    override fun getFilterSelected(): DialogModel {
        return filterSelected!!
    }

    override fun getFilters(): ArrayList<DialogModel> {
        return filters!!
    }

    override fun onChangeStatus(selected: DialogModel?) {
        selected?.let {
            filterSelected = it
        }

        loadTransaction()
    }

    override fun setFilterDateSelected(data: FilterDialogDate?) {
        selectedDate = data
        loadTransaction()
    }

    override fun getFilterDateSelected(): FilterDialogDate? {
        return selectedDate
    }

}