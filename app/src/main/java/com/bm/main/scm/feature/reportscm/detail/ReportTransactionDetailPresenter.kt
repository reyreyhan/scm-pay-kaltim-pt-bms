package com.bm.main.scm.feature.reportscm.detail

import android.content.Context
import android.content.Intent
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.rabbit.QrTransaction
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class ReportTransactionDetailPresenter(
    val context: Context,
    val view: ReportTransactionDetailContract.View
) : BasePresenter<ReportTransactionDetailContract.View>(),
    ReportTransactionDetailContract.Presenter, ReportTransactionDetailContract.InteractorOutput {

    private var interactor: ReportTransactionDetailInteractor =
        ReportTransactionDetailInteractor(this)
    private var qrTransaction: QrTransaction? = null
    private val respDateFormat by lazy {
        SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    }
    private val respTimeFormat by lazy {
        SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onViewCreated(intent: Intent) {
        val item = intent.getParcelableExtra<QrTransaction>("QrTransaction")
        qrTransaction = item
        Timber.d("Date Long: %d", item.time)
    }

    override fun setAdapterList(adapter: ReportTransactionDetailAdapter) {
        val list = mutableListOf<String>()
        list.add(respDateFormat.format(Date(qrTransaction!!.time!!)))
        list.add(respTimeFormat.format(Date(qrTransaction!!.time!!)))
        list.add(qrTransaction!!.merchant_repo)
        list.add(qrTransaction!!.nominal)
        list.add("BERHASIL")
        list.add(qrTransaction!!.nmid)
        adapter.list = list
        adapter.notifyDataSetChanged()
    }

}
