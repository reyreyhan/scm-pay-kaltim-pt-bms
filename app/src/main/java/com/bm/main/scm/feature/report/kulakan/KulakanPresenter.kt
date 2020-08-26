package com.bm.main.scm.feature.report.kulakan

import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.DialogModel
import com.bm.main.scm.models.FilterDialogDate
import com.bm.main.scm.models.report.ReportRestModel
import com.bm.main.scm.models.transaction.HistoryTransaction
import com.bm.main.scm.models.transaction.Transaction
import com.bm.main.scm.utils.AppConstant
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.LocalDate

class KulakanPresenter(val context: Context, val view: KulakanContract.View) : BasePresenter<KulakanContract.View>(),
    KulakanContract.Presenter, KulakanContract.InteractorOutput {

    private var interactor = KulakanInteractor(this)
    private var transactionRestModel = ReportRestModel(context)
    private var firstDate: CalendarDay?= null
    private var lastDate: CalendarDay?= null
    private var today: CalendarDay?= null
    private var filterSelected:DialogModel ?= null
    private var filters:ArrayList<DialogModel> ?= null
    private var code:Int = -1
    private var selectedDate:FilterDialogDate?=null

    override fun onViewCreated(intent: Intent) {
        code = intent.getIntExtra(AppConstant.CODE,-1)
        val now = LocalDate.now()
        today = CalendarDay.from(now)
        val yesterday = today?.date!!.minusDays(1)
        firstDate =  CalendarDay.from(yesterday)
        lastDate = today
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
        interactor.callGetHistoryAPI(context,transactionRestModel,selectedDate?.firstDate?.date.toString(),selectedDate?.lastDate?.date.toString(),filterSelected!!.id.toString())

    }

    override fun onSuccessGetHistory(data:List<HistoryTransaction>?) {
        data?.let {
            if(it.isEmpty()){
                view.showErrorMessage(999,"Data tidak ditemukan")
                return
            }
            val list = ArrayList<Transaction>()
            for(history in it){
                history.detail?.let {detail->
                    if(detail.isNotEmpty()){
                        val header = Transaction()
                        header.tanggal = history.tanggal
//                        header.type = "header"
                        header.totalorder = history.totalordersemua
                        val size = detail.size
                        for(i in size-1 downTo 0){
                            val trx = detail[i]
                            val pos = i+1
//                            trx.pos = pos
                            list.add(0,trx)
                        }

                        list.add(0,header)

                    }
                }
            }
            if(list.isEmpty()){
                view.showErrorMessage(999,"Tidak ada data")
                return
            }
            view.setList(list)
        }
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code,msg)
    }

    override fun onSearch(id: String) {
        if(id.isEmpty()){
            loadTransaction()
        }
        else{
            interactor.callGetSearchAPI(context,transactionRestModel,id)
        }
    }

    override fun onSuccessGetSearch(list: List<Transaction>?) {
        if(list == null){
            view.showErrorMessage(999,"Data tidak ditemukan")
            return
        }
        if(list.isEmpty()){
            view.showErrorMessage(999,"Data tidak ditemukan")
            return
        }
        view.setList(list)
    }

    private fun generateFilter(){
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

    override fun getCode(): Int {
        return code
    }

    override fun setFilterDateSelected(data: FilterDialogDate?) {
        selectedDate = data
        loadTransaction()
    }

    override fun getFilterDateSelected(): FilterDialogDate? {
        return selectedDate
    }

}