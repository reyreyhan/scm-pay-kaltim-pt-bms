package com.bm.main.scm.feature.reportscm.transaction.cashier

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.feature.reportscm.detail.ReportTransactionDetailActivity
import com.bm.main.scm.rabbit.QrTransaction
import com.bm.main.scm.rabbit.QrisService
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_report_cashier_scm.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ReportTransactionCashierActivity :
    BaseActivity<ReportTransactionCashierPresenter, ReportTransactionCashierContract.View>(),
    ReportTransactionCashierContract.View, ReportTransactionCashierAdapter.ReportTransactionListener {

    private val dateFormat by lazy { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    private val itemDateFormat by lazy { SimpleDateFormat("d MMMM yyyy", Locale.getDefault()) }
    private val respDateFormat by lazy {
        SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss.SSS",
            Locale.getDefault()
        )
    }
    private val listDates by lazy { ArrayList<String>() }
    val dateNow by lazy {
        Calendar.getInstance().time
    }

    @Inject
    lateinit var qrisService: QrisService

    private var disposable: Disposable? = null


    override fun createPresenter(): ReportTransactionCashierPresenter {
        return ReportTransactionCashierPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_report_cashier_scm
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
//        getPresenter()?.onViewCreated()
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = "Laporan"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setBackgroundDrawable(ColorDrawable(resources.getColor(android.R.color.white)))
            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                backArrow.setTint(resources.getColor(android.R.color.black))
            }
            setHomeAsUpIndicator(backArrow)
        }
    }

    private fun renderView() {
        initDatePicker()
        initRecyclerView()
        loadData(dateNow, dateNow)
    }

    private fun initRecyclerView() {
        val listTransaction = mutableListOf<QrTransaction>()
        val adapter = ReportTransactionCashierAdapter(listTransaction, this)
        rv_report.adapter = adapter
        rv_report.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    @SuppressLint("SimpleDateFormat")
    private fun initDatePicker() {
        val now = Calendar.getInstance()
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val dateNow = formatter.format(now.timeInMillis)
        tv_date_range.text = "${dateNow} - ${dateNow}"
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        builder.setSelection(androidx.core.util.Pair(now.timeInMillis, now.timeInMillis))
        val picker = builder.build()
        picker.addOnNegativeButtonClickListener { picker.dismiss() }
        picker.addOnPositiveButtonClickListener {
            val date1 = formatter.format(it.first?.let { it1 -> Date(it1) })
            val date2 = formatter.format(it.second?.let { it1 -> Date(it1) })
            tv_date_range.text = "${date1} - ${date2}"
        }
        tv_date_range.setOnClickListener {
            picker.show(supportFragmentManager, picker.toString())
        }
    }

    fun setBottomCounter(list: List<QrTransaction>) {
        var sumOmzet = 0.0
        list.forEach { qr ->
            sumOmzet += qr.nominal.toFloat()
        }
        tv_value_total_transaction.text = "${list.size} transaksi"
        tv_value_total_ammount.text = "Rp ${sumOmzet.toInt()}"
    }

    private fun loadData(dateStart: Date, dateEnd: Date) {
        val adapter = rv_report.adapter as ReportTransactionCashierAdapter

        disposable?.dispose()
        disposable = qrisService
            .getTransaksiRange(
                "1247628",
                dateFormat.format(dateStart),
                dateFormat.format(dateEnd)
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
//                swipe.isRefreshing = false
                if (result.rc == "00" && result.data.isNotEmpty()) {
                    result.data
                        .sortedByDescending { it.time_request }
                        .forEach {
                            val timeStr = it.time_request.replace("T", " ")
                            val timeFormatted = respDateFormat.parse(timeStr)
                            Timber.d("Time: %s", timeStr)
                            Timber.d("Time Long: %d", timeFormatted.time)
                            Timber.d("Time Str: %s", itemDateFormat.format(timeFormatted))
                            it.time = timeFormatted.time
                            it.time_request = itemDateFormat.format(timeFormatted)
                            adapter.list.add(it)
                        }
                    setBottomCounter(adapter.list)
                    adapter.notifyDataSetChanged()
                }
            }, { error ->
                Log.d("Error: %s", error.toString())
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun OnReportTransactionClick(transaction: QrTransaction) {
        startActivity(Intent(this, ReportTransactionDetailActivity::class.java).apply {
            putExtra("QrTransaction", transaction)
        })
    }
}

data class Transaction(
    var title: String? = null,
    var date: String? = null,
    var id: String? = null,
    var ammount: Double? = null
)