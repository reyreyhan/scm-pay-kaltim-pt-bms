package com.bm.main.pos.feature.report.labarugi.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.feature.filterDate.main.MainActivity
import com.bm.main.pos.feature.report.labarugi.cashflow.CashFlowFragment
import com.bm.main.pos.feature.report.labarugi.penjualan.PenjualanFragment
import com.bm.main.pos.models.FilterDialogDate
import com.bm.main.pos.models.TabModel
import com.bm.main.pos.models.report.ReportLabaRugi
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.ViewPagerAdapter
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.Helper
import kotlinx.android.synthetic.main.activityt_report_profit.*

class MainActivity : BaseActivity<MainPresenter, MainContract.View>(),
    MainContract.View{

    private val openFilter = 1100
    private val fragmentManager = supportFragmentManager
    private val viewPagerAdapter = ViewPagerAdapter(fragmentManager)
    private val sellFragment = PenjualanFragment.newInstance()
    private val reportFragment = CashFlowFragment.newInstance()


    override fun createPresenter(): MainPresenter {
        return MainPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activityt_report_profit
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
        reloadData()
    }

    private fun renderView() {
        ll_search.setOnClickListener {
            openFilter(getPresenter()?.getFilterDateSelected())
        }

        sw_refresh.setOnRefreshListener {
            reloadData()
        }

        setupTab()
    }

    private fun setupTab(){
        val type : ArrayList<TabModel> = ArrayList()
        val tab1 = TabModel()
        tab1.fragment = sellFragment
        tab1.title = "Laporan Penjualan"
        val tab2 = TabModel()
        tab2.fragment = reportFragment
        tab2.title = "Laporan Keuangan"
        type.add(tab1)
        type.add(tab2)
        setupViewPager(type)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item!!)
    }


    private fun setupToolbar() {
        supportActionBar?.apply {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            title = "Laporan"

            val backArrow = ContextCompat.getDrawable(this@MainActivity,R.drawable.ic_back_pos)
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



    private fun setupViewPager(list: List<TabModel>) {
        for (type in list) {
            viewPagerAdapter.addFragment(type.fragment, type.title)
        }
        viewpager.adapter = viewPagerAdapter
        tabs.tabMode = TabLayout.MODE_FIXED
        tabs.setupWithViewPager(viewpager)

    }

    @SuppressLint("SetTextI18n")
    override fun setData(data: ReportLabaRugi) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false

        val info = data.info
        info?.let {
            tv_omzet.text = "Rp ${Helper.convertToCurrency(it.omset!!)}"
            tv_modal.text = "Rp ${Helper.convertToCurrency(it.modal!!)}"
            tv_transaksi.text = Helper.convertToCurrency(it.jumlah_transaksi!!)
            tv_qty.text = Helper.convertToCurrency(it.jumlah_barang!!)
            tv_laba.text = Helper.convertToCurrency(it.penjualan_bersih!!)
        }

        sellFragment.setData(data.laporan_penjualan)
        reportFragment.setData(data.laporan_keuangan)
    }

    override fun showErrorMessage(code: Int, msg: String) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        if(code == RestException.CODE_USER_NOT_FOUND){
            restartLoginActivity()
        }
        else{
            toast(this,msg)
            sellFragment.setData(null)
            reportFragment.setData(null)
        }

    }

    override fun reloadData() {
        sw_refresh.isRefreshing = true
        emptyData()
        getPresenter()?.loadData()
    }

    override fun setDate(firstDate: String, lastDate: String) {
        val date1 = Helper.getDateFormat(this,firstDate,"yyyy-MM-dd","dd MMMM yyyy")
        val date2 = Helper.getDateFormat(this,lastDate,"yyyy-MM-dd","dd MMMM yyyy")
        var date = date1
        if(date1 != date2){
            date = "$date1 - $date2"
        }
        tv_search.text = date
        et_search.text = date
    }

    @SuppressLint("SetTextI18n")
    private fun emptyData(){
        tv_omzet.text = "Rp 0"
        tv_modal.text = "Rp 0"
        tv_transaksi.text = "0"
        tv_qty.text = "0"
        tv_laba.text = "Rp 0"

    }

    override fun openFilter(data: FilterDialogDate?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(AppConstant.DATA,data)
        startActivityForResult(intent,openFilter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && requestCode == openFilter){
            val filter = data?.getParcelableExtra(AppConstant.DATA) as FilterDialogDate
            sw_refresh.isRefreshing = true
            emptyData()
            getPresenter()?.setFilterDateSelected(filter)
            setDate(filter.firstDate?.date.toString(),filter.lastDate?.date.toString())
        }
    }

}
