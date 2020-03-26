package com.bm.main.pos.feature.report.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.feature.report.labarugi.penjualan.PenjualanActivity
import com.bm.main.pos.utils.AppConstant
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

        }
        btn_report_stock.setOnClickListener {

        }
    }


    override fun openTransaction() {
    }

    override fun openKulakan() {

    }

    override fun openStock() {

    }

    override fun openProfit() {
        val intent = Intent(this, PenjualanActivity::class.java)
        startActivity(intent)
    }

    override fun openMutasi() {

    }

}