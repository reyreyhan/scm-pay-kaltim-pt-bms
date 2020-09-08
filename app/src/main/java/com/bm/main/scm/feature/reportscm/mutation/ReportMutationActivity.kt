package com.bm.main.scm.feature.reportscm.mutation

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.rabbit.QrMutation
import com.bm.main.scm.rabbit.QrisService
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_report_mutation_merchant_scm.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ReportMutationActivity : AppCompatActivity() {

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
    private var disposable: Disposable? = null

    @Inject
    lateinit var qrisService: QrisService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_mutation_merchant_scm)
        renderView()
        loadData(dateNow, dateNow)
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = "Laporan Mutasi"
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
        val listMutation = mutableListOf<QrMutation>()
//        listMutation.add(
//            Mutation(
//                "Cashout ke BRI - 27893728947923",
//                "Senin, 27 Agustus 2020 - 16:17",
//                "1238718927343",
//                50000.00
//            )
//        )
//        listMutation.add(
//            Mutation(
//                "Pembayaran - OVO/Grab(082236126387)",
//                "Senin, 27 Agustus 2020 - 16:17",
//                "1238718927343",
//                50000.00
//            )
//        )
        val adapter = ReportMutationAdapter(listMutation)
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

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loadData(dateStart: Date, dateEnd: Date) {
        val calendarStart = Calendar.getInstance().apply { time = dateStart }
        val calendarEnd = Calendar.getInstance().apply { time = dateEnd }
        val adapter = rv_report.adapter as ReportMutationAdapter

        disposable?.dispose()
        disposable = qrisService
            .getMutasiRange(
            "1247628",
            dateFormat.format(calendarStart.time),
            dateFormat.format(calendarEnd.time))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
//            swipe.isRefreshing = false
                if (result.rc == "00" && result.data.isNotEmpty()) {
                    result.data
                        .sortedByDescending { it.mutation_date }
                        .forEach {
                            it.time = respDateFormat.parse(it.mutation_date)
                            it.mutation_date = itemDateFormat.format(it.time)

//                        if (!listDates.contains(it.mutation_date)) {
//                            listDates.add(it.mutation_date)
//                            adapter.list.add(QrItem(0, it))
//                        }
                            adapter.list.add(it)
                        }
                    adapter.notifyItemRangeInserted(adapter.list.size, result.data.size)
                }
            }, { error->
                Log.d("Error: %s", error.toString())
            })
    }
}

data class Mutation(
    var title: String? = null,
    var date: String? = null,
    var id: String? = null,
    var ammount: Double? = null
)