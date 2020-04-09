package com.bm.main.pos.feature.merchant

import android.content.Context
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.models.FilterDialogDate
import com.bm.main.pos.models.transaction.HistoryTransaction
import com.bm.main.pos.models.transaction.Transaction
import com.bm.main.pos.models.transaction.TransactionRestModel
import com.bm.main.pos.models.user.User
import com.bm.main.pos.models.user.UserRestModel
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.Helper
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.LocalDate
import timber.log.Timber

class MerchantPresenter(val context:Context, val view: MerchantContract.View) : BasePresenter<MerchantContract.View>(),
    MerchantContract.Presenter,
    MerchantContract.InteractorOutput{

    private var interactor = MerchantInteractor(this)
    private var transactionRestModel = TransactionRestModel(context)
    private var userRestModel = UserRestModel(context)
    private var today: CalendarDay? = null
    private var filterSelected: DialogModel? = null
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
        loadTransaction()
        loadProfile()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun loadTransaction() {
        interactor.callGetHistoryAPI(context, transactionRestModel, selectedDate?.firstDate?.date.toString(), selectedDate?.lastDate?.date.toString(), "lunas")
    }

    override fun onSuccessGetHistory(data: List<HistoryTransaction>?) {
        data?.let {
            if (it.isEmpty()) {
                view.showErrorMessage(999, "Data tidak ditemukan")
                return
            }
            Timber.d("Detail Terakhir: " + data[data.lastIndex].detail.toString())
            val list = ArrayList<Transaction>()
            for (history in it) {
                history.detail?.let { detail ->
                    if (detail.isNotEmpty()) {
                        val size = detail.size
                        for (i in size - 1 downTo 0) {
                            val trx = detail[i]
                            list.add(0, trx)
                        }
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

    override fun loadProfile() {
        interactor.callGetProfileAPI(context, userRestModel)
    }

    override fun onSuccessGetProfile(list: List<User>) {
        if (list.isEmpty()) {
            view.showErrorMessage(999, "User tidak ditemukan")
            return
        }

        val user = list[0]
        interactor.saveUser(user)
        view.setProfile(user.nama_lengkap!!,
            user.alamat!!,
            user.kota!!,
            user.no_telp!!,
            user.gbr!!,
            "Rp " + Helper.convertToCurrency(user.omset!!))
    }


    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code, msg)
    }
}