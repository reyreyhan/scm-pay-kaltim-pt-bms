package com.bm.main.scm.feature.reportscm.mutation

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.activity_report_mutation_merchant_scm.*
import java.text.SimpleDateFormat
import java.util.*

class ReportMutationActivity : BaseActivity<ReportMutationPresenter, ReportMutationContract.View>(),
    ReportMutationContract.View {
    override fun createPresenter(): ReportMutationPresenter {
        return ReportMutationPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_report_mutation_merchant_scm
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
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
        val listMutation = mutableListOf<Mutation>()
        listMutation.add(
            Mutation(
                "Cashout ke BRI - 27893728947923",
                "Senin, 27 Agustus 2020 - 16:17",
                "1238718927343",
                50000.00
            )
        )
        listMutation.add(
            Mutation(
                "Pembayaran - OVO/Grab(082236126387)",
                "Senin, 27 Agustus 2020 - 16:17",
                "1238718927343",
                50000.00
            )
        )
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
        }
        tv_date_range.setOnClickListener {
            picker.show(supportFragmentManager, picker.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

data class Mutation(
    var title: String? = null,
    var date: String? = null,
    var id: String? = null,
    var ammount: Double? = null
)