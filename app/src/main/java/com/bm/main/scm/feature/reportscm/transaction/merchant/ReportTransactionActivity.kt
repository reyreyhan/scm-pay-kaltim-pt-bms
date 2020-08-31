package com.bm.main.scm.feature.reportscm.transaction.merchant

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import kotlinx.android.synthetic.main.activity_report_merchant_scm.*
import java.text.SimpleDateFormat
import java.util.*

class ReportTransactionActivity : BaseActivity<ReportTransactionPresenter, ReportTransactionContract.View>(), ReportTransactionContract.View {
    override fun createPresenter(): ReportTransactionPresenter {
        return ReportTransactionPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_report_merchant_scm
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
        val listGroup = mutableListOf<TransactionGroup>()
        val listTransaction = mutableListOf<Transaction>()
        listTransaction.add(Transaction("OVO - Rizqy Ali Syaifurrahman", "Senin, 27 Agustus 2020 - 16:17", "12341231235", 25000.00))
        listTransaction.add(Transaction("Link Aja - Rizqy Ali Syaifurrahman", "Senin, 28 Agustus 2020 - 16:17", "5435324223", 50000.00))
        listGroup.add(TransactionGroup("Kasir 1 - Aisyah", listTransaction))
        listGroup.add(TransactionGroup("Kasir 2 - Putra", listTransaction))
        listGroup.add(TransactionGroup("Kasir 3 - Alea", listTransaction))
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

class TransactionGroup(title:String, list:List<Transaction>) :
    ExpandableGroup<Transaction>(title, list) {
}

data class Transaction(
    var title:String? = null,
    var date:String? = null,
    var id:String? = null,
    var ammount:Double? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(date)
        parcel.writeString(id)
        parcel.writeValue(ammount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transaction> {
        override fun createFromParcel(parcel: Parcel): Transaction {
            return Transaction(parcel)
        }

        override fun newArray(size: Int): Array<Transaction?> {
            return arrayOfNulls(size)
        }
    }
}