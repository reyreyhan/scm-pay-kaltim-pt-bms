package com.bm.main.scm.feature.reportscm.transaction.merchant

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.rabbit.QrTransaction
import com.bm.main.scm.rabbit.QrisService
import com.google.android.material.datepicker.MaterialDatePicker
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_report_merchant_scm.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ReportTransactionActivity : AppCompatActivity() {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_merchant_scm)
        renderView()
        loadData(dateNow, dateNow)
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
    }

    private fun initRecyclerView() {
        val listGroup = mutableListOf<TransactionGroup>()
        listGroup.add(TransactionGroup("Kasir 1 - Aisyah", mutableListOf<QrTransaction>()))
        listGroup.add(TransactionGroup("Kasir 2 - Putra", mutableListOf<QrTransaction>()))
        listGroup.add(TransactionGroup("Kasir 3 - Alea", mutableListOf<QrTransaction>()))
        val adapter = ReportTransactionAdapter(listGroup)
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
            loadData(Date(it.first!!), Date(it.second!!))
        }
        tv_date_range.setOnClickListener {
            picker.show(supportFragmentManager, picker.toString())
        }
    }

    private fun loadData(dateStart: Date, dateEnd: Date) {
//        val calendarStart = Calendar.getInstance().apply { time = dateStart }
//        val calendarEnd = Calendar.getInstance().apply { time = dateEnd }
        val adapter = rv_report.adapter as ReportTransactionAdapter

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
                val list = mutableListOf<QrTransaction>()
                if (result.rc == "00" && result.data.isNotEmpty()) {
                    result.data
                        .sortedByDescending { it.time_request }
                        .forEach {
                            val timeStr = it.time_request.replace("T", " ")
                            val timeFormatted = respDateFormat.parse(timeStr)
                            it.time = timeFormatted.time
                            it.time_request = itemDateFormat.format(timeFormatted)

                            adapter.list.forEach { group ->
                                group.items.add(it)
                                list.add(it)
                            }
                        }
                    setBottomCounter(list)
                    adapter.notifyDataSetChanged()
                }
            }, { error ->
                Log.d("Error: %s", error.toString())
            })
//                if (calendar.get(Calendar.DAY_OF_YEAR) > calendarNow.get(Calendar.DAY_OF_YEAR)) {
//                    loadData(calendar.apply { add(Calendar.DAY_OF_YEAR, -1) }.time)
//                } else if (trxAdapter.itemCount == 0) {
//                    t_error.visibility = View.VISIBLE
//                }
    }

    fun setBottomCounter(list:List<QrTransaction>){
        var sumOmzet = 0.0
        list.forEach { qr ->
            sumOmzet+= qr.nominal.toFloat()
        }
        tv_value_total_transaction.text = "${list.size} transaksi"
        tv_value_total_ammount.text = "Rp ${sumOmzet.toInt()}"
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

class TransactionGroup(title: String, list: List<QrTransaction>) :
    ExpandableGroup<QrTransaction>(title, list)