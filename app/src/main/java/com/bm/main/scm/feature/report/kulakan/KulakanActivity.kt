package com.bm.main.scm.feature.report.kulakan

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.events.onReloadTransaction
import com.bm.main.scm.feature.dialog.BottomDialog
import com.bm.main.scm.feature.filterDate.main.MainActivity
import com.bm.main.scm.feature.transaction.detail.old.DetailActivity
import com.bm.main.scm.models.DialogModel
import com.bm.main.scm.models.FilterDialogDate
import com.bm.main.scm.models.transaction.Transaction
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.utils.AppConstant
import kotlinx.android.synthetic.main.activityt_report_kulakan.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class KulakanActivity : BaseActivity<KulakanPresenter, KulakanContract.View>(),
    KulakanContract.View, BottomDialog.Listener {

    private val openFilter = 1100
    val adapter = KulakanAdapter()
    //private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun createPresenter(): KulakanPresenter {
        return KulakanPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activityt_report_kulakan
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
        renderView()
        sw_refresh.isRefreshing = true
        getPresenter()?.onViewCreated(intent)
    }

    private fun renderView() {

        sw_refresh.setOnRefreshListener {
            reloadData()
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter

        adapter.callback = object : KulakanAdapter.ItemClickCallback {
            override fun onClick(data: Transaction) {
                openDetail(data.no_invoice!!)
            }
        }

        btn_date.setOnClickListener {
            openFilter(getPresenter()?.getFilterDateSelected())
        }

        btn_sort.setOnClickListener {
            openFilterByStatusDialog(
                "Filter berdasarkan status", getPresenter()?.getFilters()!!, getPresenter()?.getFilterSelected(),
                AppConstant.Code.CODE_FILTER_DATE_HISTORY
            )
        }

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                adapter.clearAdapter()
                sw_refresh.isRefreshing = true
                getPresenter()?.onSearch(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item!!)
    }


    private fun setupToolbar() {
        supportActionBar?.apply {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            title = "Laporan"

            val backArrow = ContextCompat.getDrawable(this@KulakanActivity,R.drawable.ic_back_pos)
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
        EventBus.getDefault().unregister(this)
    }

    override fun reloadData() {
        sw_refresh.isRefreshing = true
        adapter.clearAdapter()
        getPresenter()?.loadTransaction()
    }

    override fun setList(list: List<Transaction>) {
        sw_refresh.isRefreshing = false
        rv_list.visibility = View.VISIBLE
        tv_error.visibility = View.GONE
        adapter.setItems(list)
    }

    override fun showErrorMessage(code: Int, msg: String) {
        sw_refresh.isRefreshing = false
        if (code == RestException.CODE_USER_NOT_FOUND) {
            restartLoginActivity()
        } else {
            rv_list.visibility = View.GONE
            tv_error.visibility = View.VISIBLE
            tv_error.text = msg
        }
    }

    override fun openDetail(id: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(AppConstant.CODE, AppConstant.Code.CODE_TRANSACTION_SUPPLIER)
        intent.putExtra(AppConstant.DATA, id)
        startActivity(intent)
    }

    override fun openFilterByStatusDialog(title: String, list: List<DialogModel>, selected: DialogModel?, type: Int) {
        val dialog = BottomDialog.newInstance()
        dialog.setData(title,type,list,selected)
        dialog.show(this.supportFragmentManager, BottomDialog.TAG)
    }

    override fun onItemClicked(data: DialogModel, type: Int) {
        adapter.clearAdapter()
        sw_refresh.isRefreshing = true
        getPresenter()?.onChangeStatus(data)
    }

    override fun onBackPressed() {
        if(getPresenter()?.getCode() == -1){
            finish()
        }
        else{
            restartMainActivity(R.id.nav_report)
        }
    }

    @Subscribe
    fun onEvent(event: onReloadTransaction){
        if(event.isReload){
            reloadData()
        }
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


}
