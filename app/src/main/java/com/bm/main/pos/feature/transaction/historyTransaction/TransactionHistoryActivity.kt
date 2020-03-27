package com.bm.main.pos.feature.transaction.historyTransaction

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.events.onHistoryChangedDate
import com.bm.main.pos.feature.transaction.detail.DetailSuccessActivity
import com.bm.main.pos.feature.transaction.historyTransaction.TransactionAdapter.*
import com.bm.main.pos.models.FilterDialogDate
import com.bm.main.pos.models.transaction.Transaction
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.EndlessRecyclerViewScrollListener
import com.bm.main.pos.utils.AppConstant
import kotlinx.android.synthetic.main.activity_history_transaction_new.*
import org.greenrobot.eventbus.Subscribe
import timber.log.Timber

class TransactionHistoryActivity : BaseActivity<TransactionPresenter, TransactionContract.View>(), TransactionContract.View {

    val adapter: TransactionAdapter = TransactionAdapter()
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun createPresenter(): TransactionPresenter {
        return TransactionPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_history_transaction_new
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Aktivitas"

            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            setHomeAsUpIndicator(backArrow)
        }

    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun renderView() {
        sw_refresh.isRefreshing = false
//        sw_refresh.setOnRefreshListener {
//            scrollListener.resetState()
//            reloadData()
//        }
        sw_refresh.setOnRefreshListener {
            reloadData()
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter
        adapter.callback = object : ItemClickCallback {
            override fun onClick(data: Transaction) {
                openDetail(data.no_invoice!!)
            }
//            override fun onItemClick(transaction: Transaction) {
//            }
        }

//        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
//            override fun onFirstItemVisible(isFirstItem: Boolean) {
//                sw_refresh.isEnabled = isFirstItem && adapter!!.itemCount > 0
//            }
//            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {}
//        }
//
//        rv_list.addOnScrollListener(scrollListener)
//        _view.btn_date.setOnClickListener {
//            openFilter(getPresenter()?.getFilterDateSelected())
//            //listener?.openFilterDateDialog(null,getPresenter()?.getTodayDate()?.date,getPresenter()?.getFirstDate(),getPresenter()?.getLastDate(),AppConstant.Code.CODE_FILTER_DATE_HISTORY)
//        }

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

    override fun reloadData() {
        sw_refresh.isRefreshing = true
        adapter.clearAdapter()
        getPresenter()?.loadTransaction()
    }

    override fun setList(list: List<Transaction>) {
        sw_refresh.isRefreshing = false
        rv_list.visibility = View.VISIBLE
        tv_error.visibility = View.GONE
//        val mList = arrayListOf<Transaction>()
//        mList.addAll(list)
//        adapter.setList(mList)
//        Timber.d("Adapter Size: ${adapter.itemSize}
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
        val intent = Intent(this, DetailSuccessActivity::class.java)
        intent.putExtra(AppConstant.DATA, id)
        startActivity(intent)
    }
//
//    @Subscribe
//    fun onEvent(event: onHistoryChangedDate) {
//        adapter?.clearAdapter()
//        sw_refresh.isRefreshing = true
//        getPresenter()?.onChangeDate(event.firstDate, event.lastDate)
//
//    }

    override fun openFilter(data: FilterDialogDate?) {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.putExtra(AppConstant.DATA, data)
//        startActivityForResult(intent, openFilter)
    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK && requestCode == openFilter) {
//            val filter = data?.getParcelableExtra(AppConstant.DATA) as FilterDialogDate
//            adapter?.clearAdapter()
//            sw_refresh.isRefreshing = true
//            getPresenter()?.setFilterDateSelected(filter)
//        }
//    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }
}
