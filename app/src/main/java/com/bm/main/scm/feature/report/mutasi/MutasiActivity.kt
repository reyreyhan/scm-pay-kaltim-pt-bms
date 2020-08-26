package com.bm.main.scm.feature.report.mutasi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.feature.filterDate.main.MainActivity
import com.bm.main.scm.models.FilterDialogDate
import com.bm.main.scm.models.report.ReportMutasi
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.ui.EndlessRecyclerViewScrollListener
import com.bm.main.scm.utils.AppConstant
import com.bm.main.scm.utils.Helper
import kotlinx.android.synthetic.main.activityt_report_mutasi.*

class MutasiActivity : BaseActivity<MutasiPresenter, MutasiContract.View>(),
    MutasiContract.View {

    private val openFilter = 1100
    val adapter = MutasiAdapter()
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun createPresenter(): MutasiPresenter {
        return MutasiPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activityt_report_mutasi
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

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter

        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onFirstItemVisible(isFirstItem: Boolean) {
                sw_refresh.isEnabled = isFirstItem && adapter.itemCount > 0
            }

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {

            }
        }
        rv_list.addOnScrollListener(scrollListener)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupToolbar() {
        supportActionBar?.apply {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            title = "Laporan"

            val backArrow = resources.getDrawable(R.drawable.ic_back_pos)
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

    @SuppressLint("SetTextI18n")
    override fun setData(data: ReportMutasi) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false

        val info = data.info
        info?.let {
            tv_nett.text = "Rp ${Helper.convertToCurrency(it.penjualan_bersih!!)}"
            tv_transaksi.text = Helper.convertToCurrency(it.jumlah_transaksi!!)
        }

        val transaksi = data.transaksi
        transaksi?.let {
            if(it.isNotEmpty()){
                rv_list.visibility = View.VISIBLE
                tv_error.visibility = View.GONE
                adapter.clearAdapter()
                adapter.setItems(it)
            }
            else{
                showErrorMessage(999,"Tidak ada data")
            }
        }
    }

    override fun showErrorMessage(code: Int, msg: String) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        if(code == RestException.CODE_USER_NOT_FOUND){
            restartLoginActivity()
        }
        else{
            rv_list.visibility = View.GONE
            tv_error.visibility = View.VISIBLE
            tv_error.text = msg
        }

    }

    @SuppressLint("SetTextI18n")
    override fun reloadData() {
        sw_refresh.isRefreshing = true
        tv_nett.text = "Rp 0"
        tv_transaksi.text = "0"
        getPresenter()?.loadData()
    }

    override fun setDate(firstDate: String, lastDate: String) {
        val date1 = Helper.getDateFormat(this,firstDate,"yyyy-MM-dd","dd MMMM yyyy")
        val date2 = Helper.getDateFormat(this,lastDate,"yyyy-MM-dd","dd MMMM yyyy")
        var date = date1
        if(date1 != date2){
            date = "$date1 - $date2"
        }
        et_search.text = date
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
            tv_nett.text = "Rp 0"
            tv_transaksi.text = "0"
            getPresenter()?.setFilterDateSelected(filter)
            setDate(filter.firstDate?.date.toString(),filter.lastDate?.date.toString())
        }
    }

}
