package com.bm.main.scm.feature.report.transaction

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.feature.dialog.BottomDialog
import com.bm.main.scm.feature.dialog.RangeDateDialog
import com.bm.main.scm.models.DialogModel
import com.bm.main.scm.models.FilterDialogDate
import com.bm.main.scm.models.report.ReportTransaksi
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.ui.EndlessRecyclerViewScrollListener
import com.bm.main.scm.ui.ext.toast
import com.bm.main.scm.utils.Helper
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.activityt_report_transaksi_new.*
import org.threeten.bp.LocalDate
import timber.log.Timber

class TransactionActivity : BaseActivity<TransactionPresenter, TransactionContract.View>(),
    TransactionContract.View, BottomDialog.Listener, RangeDateDialog.Listener {

    private val openFilter = 1100
    val adapter = TransactionAdapter()
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private val rangeDateDialog = RangeDateDialog.newInstance()

    override fun createPresenter(): TransactionPresenter {
        return TransactionPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activityt_report_transaksi_new
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
    }

    private fun renderView() {
        sw_refresh.setOnRefreshListener {
            scrollListener.resetState()
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

        et_search.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                adapter.clearAdapter()
                sw_refresh.isRefreshing = true
                getPresenter()?.search(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        et_date_1.setOnClickListener {
            openFilter(getPresenter()?.getFilterDateSelected())
        }

        et_date_2.setOnClickListener{
            openFilter(getPresenter()?.getFilterDateSelected())
        }

        btn_sort.setOnClickListener {
//            adapter.clearAdapter()
//            sw_refresh.isRefreshing = true
//            getPresenter()?.sort()
            openSortDialog("Urutkan berdasarkan",getPresenter()?.getSortList()!!,getPresenter()?.getSelectedSort(),1)
        }
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
            title = "Laporan Penjualan Barang"

            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            setHomeAsUpIndicator(backArrow)
        }
    }

    override fun setData(list: List<ReportTransaksi>) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        adapter.setItems(list)
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun showErrorMessage(code: Int, msg: String) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        if(code == RestException.CODE_USER_NOT_FOUND){
            restartLoginActivity()
        }
        else{

            toast(this,msg)
        }

    }

    override fun showSuccessMessage(msg: String?) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        msg?.let {
            Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
        }
        reloadData()

    }

    override fun reloadData() {
        sw_refresh.isRefreshing = true
        adapter.clearAdapter()
        getPresenter()?.loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun openFilter(data: FilterDialogDate?) {
        hideLoadingDialog()
        val now = CalendarDay.from(LocalDate.now())
        val min = LocalDate.of(2000, 1, 1)
        val max = LocalDate.of(2100, 12, 31)
        rangeDateDialog.setData(min, max, CalendarDay.from(now.date.minusDays(1)), now)
        rangeDateDialog.show(supportFragmentManager, "rangedate")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if(resultCode == RESULT_OK && requestCode == openFilter){
//            val filter = data?.getParcelableExtra(AppConstant.DATA) as FilterDialogDate
//            sw_refresh.isRefreshing = true
//            adapter.clearAdapter()
//            getPresenter()?.loadData()
//        }
    }
    override fun openSortDialog(title: String, list: List<DialogModel>, selected: DialogModel?, type: Int) {
        val dialog = BottomDialog.newInstance()
        dialog.setData(title,type,list,selected)
        dialog.show(this.supportFragmentManager, BottomDialog.TAG)
    }

    override fun setDate(firstDate: String, lastDate: String) {
        val date1 = Helper.getDateFormat(this,firstDate,"yyyy-MM-dd","dd MMMM yyyy")
        val date2 = Helper.getDateFormat(this,lastDate,"yyyy-MM-dd","dd MMMM yyyy")
        et_date_1.text = date1
        et_date_2.text = date2
    }

    override fun onItemClicked(data: DialogModel, type: Int) {
        adapter.clearAdapter()
        sw_refresh.isRefreshing = true
        getPresenter()?.sort(data)
    }

    override fun onDateRangeClicked(firstDate: CalendarDay?, lastDate: CalendarDay?, type: Int) {
        adapter.clearAdapter()
        sw_refresh.isRefreshing = true
        Timber.d("FirstDate: ${firstDate!!.date}")
        Timber.d("LastDate: ${lastDate!!.date}")
        getPresenter()?.setDate(firstDate, lastDate)
        getPresenter()?.loadData()
    }
}
