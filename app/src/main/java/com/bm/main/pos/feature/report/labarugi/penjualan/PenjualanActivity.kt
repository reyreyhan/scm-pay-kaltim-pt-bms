package com.bm.main.pos.feature.report.labarugi.penjualan

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.feature.dialog.SingleDateDialog
import com.bm.main.pos.models.FilterDialogDate
import com.bm.main.pos.models.report.ReportLabaRugi
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.Helper
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.activityt_report_profit_new.*
import kotlin.math.roundToInt


class PenjualanActivity : BaseActivity<PenjualanPresenter, PenjualanContract.View>(),
    PenjualanContract.View, SingleDateDialog.Listener {


    private val openFilter = 1100
    val adapter = PenjualanAdapter()

    private val singleDateDialog = SingleDateDialog.newInstance()

    var earning:Double? = null
    var profit:Double? = null
    var transaction:Int? = null

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

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter
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

    @SuppressLint("SetTextI18n")
    override fun setData(data: ReportLabaRugi) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        val info = data.info
        info?.let {
            earning = it.omset!!.toDouble()
            transaction = it.jumlah_transaksi!!.toInt()
            profit = it.penjualan_bersih!!.toDouble()
            tv_earning.text = "Rp ${Helper.convertToCurrency(earning!!)}"
//            tv_modal.text = "Rp ${Helper.convertToCurrency(it.modal!!)}"
            tv_count_transaction.text = Helper.convertToCurrency(transaction!!.toString())
//            tv_qty.text = Helper.convertToCurrency(it.jumlah_barang!!)
            tv_profit.text = Helper.convertToCurrency(profit!!)
        }
        setList(data.laporan_penjualan!!)
        getPresenter()?.loadYesterdayData()
        sw_refresh.isRefreshing = false
    }

    override fun onDateClicked(selected: CalendarDay?, type: Int) {
        getPresenter()?.setDate(selected, null)
        reloadData()
    }

    override fun setYesterdayData(data: ReportLabaRugi) {
        val info = data.info
        info?.let{
            val percentProfit =
                (info.penjualan_bersih!!.toDouble()-profit!!).div(info.penjualan_bersih.toDouble()).times(100).roundToInt()
            val percentEarning = (info.omset!!.toDouble()-earning!!).div(info.omset.toDouble()).times(100).roundToInt()
            val percentTransaction = (info.jumlah_transaksi!!.toInt()-transaction!!).div(info.jumlah_transaksi.toInt()).times(100)

            tv_percent_earning.text = "$percentEarning%"
            tv_percent_profit.text = "$percentProfit%"
            tv_percent_transaction.text = "$percentTransaction%"
        }
    }
}

