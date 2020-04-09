package com.bm.main.pos.feature.report.labarugi.penjualan

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.feature.dialog.SingleDateDialog
import com.bm.main.pos.feature.transaction.detail.DetailSuccessActivity
import com.bm.main.pos.models.FilterDialogDate
import com.bm.main.pos.models.report.ReportLabaRugi
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.Helper
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.activityt_report_profit_new.*
import org.jetbrains.anko.textColor
import timber.log.Timber
import kotlin.math.roundToInt


class PenjualanActivity : BaseActivity<PenjualanPresenter, PenjualanContract.View>(),
    PenjualanContract.View, SingleDateDialog.Listener {

    val adapter = PenjualanAdapter()

    private val singleDateDialog = SingleDateDialog.newInstance()

    var earning = 0.0
    var profit = 0.0
    var transaction = 0
    var earningYest = 0.0
    var profitYest = 0.0
    var transactionYest = 0
    var percentProfit = 0
    var percentEarning = 0
    var percentTransaction = 0
    var idTrx = ""

    override fun createLayout(): Int {
        return R.layout.activityt_report_profit_new
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
        reloadData()
    }

    override fun createPresenter(): PenjualanPresenter {
        return PenjualanPresenter(this, this)
    }

    private fun renderView(){
        ll_search.setOnClickListener {
            openFilter(getPresenter()?.getFilterDateSelected())
        }

        sw_refresh.setOnRefreshListener {
            reloadData()
        }

        btn_detail_laporan.setOnClickListener {
//            val intent = Intent(this, DetailSuccessActivity::class.java)
//            intent.putExtra(AppConstant.DATA, )
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Laporan Keuntungan"

            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            setHomeAsUpIndicator(backArrow)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun setList(list: List<ReportLabaRugi.Penjualan>) {
        rv_list.visibility = View.VISIBLE
//        tv_error.visibility = View.GONE
        adapter.clearAdapter()
        adapter.setItems(list)
    }

    override fun showErrorMessage(isToday:Boolean?, code: Int, msg: String) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        if(code == RestException.CODE_USER_NOT_FOUND){
            restartLoginActivity()
        }
        else{
            isToday?.let{
                if (isToday){
                    rv_list.visibility = View.GONE
                    toast(this, msg)
                    getPresenter()?.loadYesterdayData()
                }else{
                    toast(this, msg)
                }
            }
//            rv_list.visibility = View.GONE
//            tv_error.visibility = View.VISIBLE
//            tv_error.text = msg
        }
    }

    override fun setDate(firstDate: String, lastDate: String) {
        //val date1 = Helper.getDateFormat(this,firstDate,"yyyy-MM-dd","dd MMMM yyyy")
        val date2 = Helper.getDateFormat(this,lastDate,"yyyy-MM-dd","dd MMMM yyyy")
        var date = date2
//        if(date1 != date2){
//            date = "$date1 - $date2"
//        }
        et_search.text = date
    }

    override fun reloadData() {
        sw_refresh.isRefreshing = true
        emptyData()
        getPresenter()?.loadData()
    }

    private fun emptyData(){
        tv_earning.text = "Rp 0"
        tv_profit.text = "Rp 0"
        tv_count_transaction.text = "0"
        tv_percent_transaction.text = ""
        tv_percent_profit.text = ""
        tv_percent_earning.text = ""
    }

    override fun openFilter(data: FilterDialogDate?) {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.putExtra(AppConstant.DATA,data)
//        startActivityForResult(intent, openFilter)
        hideLoadingDialog()
        val now = CalendarDay.from(org.threeten.bp.LocalDate.now())
        singleDateDialog.setData(now, now.date.minusDays(30), now.date, AppConstant.FilterDate.DAILY)
        singleDateDialog.show(supportFragmentManager, "singledate")
//        if (singleDateDialog.dialog == null && !singleDateDialog.dialog!!.isShowing) {
//
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun setData(list: List<ReportLabaRugi.Penjualan>?) {
        getPresenter()?.onCheck(list)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun setData(data: ReportLabaRugi) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        val info = data.info
        info?.let {
            earning = it.omset!!.toDouble()
            transaction = it.jumlah_transaksi!!.toInt()
            profit = it.penjualan_bersih!!.toDouble()
        }
        setList(data.laporan_penjualan!!)
        setReportUIData()
        getPresenter()?.loadYesterdayData()
        sw_refresh.isRefreshing = false
    }

    override fun onDateClicked(selected: CalendarDay?, type: Int) {
        getPresenter()?.setDate(selected, null)
        reloadData()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun setYesterdayData(data: ReportLabaRugi) {
        val info = data.info
        info?.let {
            profitYest = info.penjualan_bersih!!.toDouble()
            earningYest = info.omset!!.toDouble()
            transactionYest = info.jumlah_transaksi!!.toInt()
        }
        setReportUIData()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun setReportUIData() {
        tv_earning.text = "Rp ${Helper.convertToCurrency(earning)}"
        tv_count_transaction.text = Helper.convertToCurrency(transaction.toString())
        tv_profit.text = "Rp ${Helper.convertToCurrency(profit)}"

        percentProfit = compareData(profit, profitYest)
        percentEarning = compareData(earning, earningYest)
        percentTransaction = compareData(transaction.toDouble(), transactionYest.toDouble())

        tv_percent_earning.text = "$percentEarning%"
        tv_percent_profit.text = "$percentProfit%"
        tv_percent_transaction.text = "$percentTransaction%"
        if (percentEarning > 0 ){
            arrow_earning.setImageDrawable(getDrawable(R.drawable.ic_profit_up))
            tv_percent_earning.textColor = getColor(R.color.md_green_700)
        }else{
            arrow_earning.setImageDrawable(getDrawable(R.drawable.ic_profit_down))
            tv_percent_earning.textColor = getColor(R.color.md_red_A700)
        }
        if (percentProfit > 0 ){
            arrow_profit.setImageDrawable(getDrawable(R.drawable.ic_profit_up))
            tv_percent_profit.textColor = getColor(R.color.md_green_700)
        }else{
            arrow_profit.setImageDrawable(getDrawable(R.drawable.ic_profit_down))
            tv_percent_profit.textColor = getColor(R.color.md_red_A700)
        }
        if (percentTransaction > 0 ){
            arrow_transaction.setImageDrawable(getDrawable(R.drawable.ic_profit_up))
            tv_percent_transaction.textColor = getColor(R.color.md_green_700)
        }else{
            arrow_transaction.setImageDrawable(getDrawable(R.drawable.ic_profit_down))
            tv_percent_transaction.textColor = getColor(R.color.md_red_A700)
        }
    }

    fun compareData(value1:Double?, value2:Double?):Int{
        var result = 0
        if (value1 != null && value2 != null){
            Timber.d("Value1: $value1")
            Timber.d("Value2: $value2")
            if (value1 == 0.0 && value2 == 0.0){
                result = 0
            }else if (value1 == 0.0){
                result = (value1.minus(value2)).div(value2).times(100).roundToInt()
            }else if (value2 == 0.0){
                result = (value1.minus(value2)).div(value1).times(100).roundToInt()
            }else{
                result = (value1.minus(value2)).div(value2).times(100).roundToInt()
            }
        }
        Timber.d("ValueCompare: $result")
        return result
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun resetData(isToday:Boolean?) {
        if (isToday!!){
            earning = 0.0
            transaction = 0
            profit = 0.0
        }else{
            earningYest = 0.0
            profitYest = 0.0
            transactionYest = 0
        }
        setReportUIData()
    }
}

