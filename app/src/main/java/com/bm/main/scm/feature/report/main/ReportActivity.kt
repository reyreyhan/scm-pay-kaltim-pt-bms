package com.bm.main.scm.feature.report.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.feature.report.labarugi.penjualan.PenjualanActivity
import com.bm.main.scm.feature.report.stock.StockActivity
import com.bm.main.scm.feature.report.transaction.TransactionActivity
import kotlinx.android.synthetic.main.activityt_report_main_new.*

class ReportActivity : BaseActivity<ReportPresenter, ReportContract.View>(), ReportContract.View{
    override fun createPresenter(): ReportPresenter {
        return ReportPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activityt_report_main_new
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
    }

    fun renderView(){
        btn_report_profit.setOnClickListener {
            openProfit()
        }
        btn_report_sales.setOnClickListener {
            openTransaction()
        }
        btn_report_stock.setOnClickListener {
            openStock()
        }
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Laporan"

            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            setHomeAsUpIndicator(backArrow)
        }
    }

    override fun openTransaction() {
        val intent = Intent(this, TransactionActivity::class.java)
        startActivity(intent)
    }

    override fun openKulakan() {

    }

    override fun openStock() {
        val intent = Intent(this, StockActivity::class.java)
        startActivity(intent)
    }

    override fun openProfit() {
        val intent = Intent(this, PenjualanActivity::class.java)
        startActivity(intent)
    }

    override fun openMutasi() {

    }

}