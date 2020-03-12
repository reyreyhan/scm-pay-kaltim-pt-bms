package com.bm.main.pos.feature.report.stock

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.feature.dialog.BottomDialog
import com.bm.main.pos.feature.dialog.RangeDateDialog
import com.bm.main.pos.feature.filterDate.main.MainActivity
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.models.FilterDialogDate
import com.bm.main.pos.models.report.ReportStock
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.EndlessRecyclerViewScrollListener
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.utils.AppConstant
import kotlinx.android.synthetic.main.activityt_report_stock.*

class StockActivity : BaseActivity<StockPresenter, StockContract.View>(),
    StockContract.View, BottomDialog.Listener{

    private val openFilter = 1100
    val adapter = StockAdapter()
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun createPresenter(): StockPresenter {
        return StockPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activityt_report_stock
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

        btn_date.setOnClickListener {
            openFilter(getPresenter()?.getFilterDateSelected())
        }

        btn_sort.setOnClickListener {
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
            title = "Laporan"

            val backArrow = ContextCompat.getDrawable(this@StockActivity,R.drawable.ic_back_pos)
            setHomeAsUpIndicator(backArrow)
        }

    }

    override fun setData(list: List<ReportStock>) {
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
            adapter.clearAdapter()
            getPresenter()?.setFilterDateSelected(filter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun openSortDialog(title: String, list: List<DialogModel>, selected: DialogModel?, type: Int) {
        val dialog = BottomDialog.newInstance()
        dialog.setData(title,type,list,selected)
        dialog.show(this.supportFragmentManager, BottomDialog.TAG)
    }

    override fun onItemClicked(data: DialogModel, type: Int) {
        adapter.clearAdapter()
        sw_refresh.isRefreshing = true
        getPresenter()?.sort(data)
    }
}
