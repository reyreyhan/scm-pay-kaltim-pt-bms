package com.bm.main.scm.feature.reportscm.transaction.cashier

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.activity_report_cashier_scm.*
import java.text.SimpleDateFormat
import java.util.*

class ReportTransactionCashierActivity : BaseActivity<ReportTransactionCashierPresenter, ReportTransactionCashierContract.View>(), ReportTransactionCashierContract.View {
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
    }

    private fun initRecyclerView() {
        val listTransaction = mutableListOf<Transaction>()
        listTransaction.add(Transaction("OVO - Rizqy Ali Syaifurrahman", "Senin, 27 Agustus 2020 - 16:17", "12341231235", 25000.00))
        listTransaction.add(Transaction("Link Aja - Rizqy Ali Syaifurrahman", "Senin, 28 Agustus 2020 - 16:17", "5435324223", 50000.00))
        listTransaction.add(Transaction("Link Aja - Rizqy Ali Syaifurrahman", "Senin, 29 Agustus 2020 - 16:17", "489257934759", 75000.00))
        val adapter = ReportTransactionCashierAdapter(listTransaction)
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

data class Transaction(
    var title:String? = null,
    var date:String? = null,
    var id:String? = null,
    var ammount:Double? = null
)