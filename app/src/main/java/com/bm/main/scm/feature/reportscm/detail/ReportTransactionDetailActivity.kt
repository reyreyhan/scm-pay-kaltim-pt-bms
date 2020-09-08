package com.bm.main.scm.feature.reportscm.detail

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.feature.dialog.SuccessDialog
import kotlinx.android.synthetic.main.activity_detail_transaction_scm.*

class ReportTransactionDetailActivity :
    BaseActivity<ReportTransactionDetailPresenter, ReportTransactionDetailContract.View>(),
    ReportTransactionDetailContract.View, SuccessDialog.SuccessDialogListener {

    override fun createPresenter(): ReportTransactionDetailPresenter {
        return ReportTransactionDetailPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_detail_transaction_scm
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        getPresenter()?.onViewCreated(intent)
        renderView()
    }

    private fun renderView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        rv_payment_detail.adapter = ReportTransactionDetailAdapter(mutableListOf<String>())
        rv_payment_detail.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        getPresenter()?.setAdapterList(rv_payment_detail.adapter as ReportTransactionDetailAdapter)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == android.R.id.home) finish()
        return super.onOptionsItemSelected(item!!)
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = "Detail Transaksi"
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

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun onPositiveButtonDialog() {

    }


}